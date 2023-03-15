package com.example.reqres.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserInfoResponseModel {

    @JsonProperty("data")
    private UserInfoResponseModel data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserInfoData {

        private Integer id;
        private String email;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        private String avatar;
    }

    @JsonProperty("support")
    private UserInfoResponseModel support;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserInfoSupport {

        private String url;
        private String text;
    }
}