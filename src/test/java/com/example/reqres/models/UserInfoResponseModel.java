package com.example.reqres.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserInfoResponseModel {

    private UserInfoData data;

    public @Data static class UserInfoData {

        private Integer id;
        private String email;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        private String avatar;
    }

    private UserInfoSupport support;

    public @Data static class UserInfoSupport {

        private String url;
        private String text;
    }
}