package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseCommon<T> {


    private boolean success;
    private Integer code;
    private T payload;

//    @JsonProperty("last Name English")
    @JsonProperty("error")
    private ResponseError responseError;

//    public void setPayload(Object payload) {
//        if(payload==null){
//            payload=new tr
//        }
//        this.payload = payload;
//    }



}
