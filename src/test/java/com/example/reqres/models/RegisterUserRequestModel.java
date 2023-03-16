package com.example.reqres.models;

import lombok.Data;

@Data
public class RegisterUserRequestModel {

    private String email;
    private String password;
}
