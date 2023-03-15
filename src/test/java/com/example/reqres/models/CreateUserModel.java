package com.example.reqres.models;

import lombok.Data;

@Data
public class CreateUserModel {

    private String name;
    private String job;
    private Integer id;
    private String createdAt;
}
