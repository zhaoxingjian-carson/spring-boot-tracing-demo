package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class YMyRestfulHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (returnType.getExecutable().getName().equals("error")) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request1 = requestAttributes.getRequest();
            HttpServletResponse response1 = requestAttributes.getResponse();

            Throwable ex = (Throwable) request1.getAttribute(DefaultErrorAttributes.class.getName() + ".ERROR");
            ResponseCommon responseCommon = new ResponseCommon();

            ResponseError responseError = new ResponseError();


            responseCommon.setResponseError(responseError);
            return responseCommon;
        }
        System.out.println("test");
        return body;
    }

    //    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = {Exception.class})
    public ResponseCommon handleValidatedException(HttpServletRequest request, HttpServletResponse response, Exception e) {

        ResponseCommon responseCommon = new ResponseCommon();


        response.setStatus(HttpStatus.BAD_REQUEST.value());

        responseCommon.setSuccess(false);
        responseCommon.setCode(400);
        return responseCommon;
    }

}