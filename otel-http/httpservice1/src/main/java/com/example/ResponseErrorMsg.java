package com.example;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorMsg {


    private String en;
    private String zhHK;
    private String zhCN;


    public ResponseErrorMsg(String en) {
        this.en = en;
    }

}
