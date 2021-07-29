package com.nurettinyakit.restexample.gateway.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nurettinyakit.restexample.domain.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersResponse {

    private Integer page;
    private Integer perPage;
    private Integer total;
    private Integer totalPages;
    @JsonProperty("data")
    private List<User> data = new ArrayList<>();
    private Support support;
}
