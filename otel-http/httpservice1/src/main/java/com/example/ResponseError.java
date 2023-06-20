package com.example;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {


    private String traceId;
    private String errorCode;
    private ResponseErrorMsg errorMessage;
}
