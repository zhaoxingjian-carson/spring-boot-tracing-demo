package com.example;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    private String userName;

    private Integer age;

    private Long id;



    public User(Long id) {
        this.id = id;
    }
}
