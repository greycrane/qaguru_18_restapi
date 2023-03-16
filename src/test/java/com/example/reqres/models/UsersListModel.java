package com.example.reqres.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class UsersListModel {

    private Integer page;
    @JsonProperty("per_page")
    private Integer perPage;
    private Integer total;
    @JsonProperty("total_pages")
    private Integer totalPages;

    private ArrayList<UsersListData> data;

    @Data
    public static class UsersListData {

        private Integer id;
        private String email;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        private String avatar;
    }

    private UsersListSupport support;

    @Data
    public static class UsersListSupport {

        private String url;
        private String text;
    }
}
