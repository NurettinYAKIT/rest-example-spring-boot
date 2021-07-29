package com.nurettinyakit.restexample.testutils;

import com.nurettinyakit.restexample.domain.User;
import com.nurettinyakit.restexample.gateway.userservice.dto.Support;
import com.nurettinyakit.restexample.gateway.userservice.dto.UserResponse;
import com.nurettinyakit.restexample.gateway.userservice.dto.UsersResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestObjectFactory {

    public static UsersResponse createUsersResponse() {
        final UsersResponse response = new UsersResponse();

        response.setPage(1);
        response.setPerPage(2);
        response.setTotal(6);
        response.setTotalPages(3);
        response.setSupport(createSupport());
        response.setUsers(List.of(createUser()));

        return response;
    }

    private static Support createSupport() {
        final Support support = new Support();

        support.setText("text");
        support.setUrl("http://url.url.com");

        return support;
    }

    private static User createUser() {
        final User user = new User();

        user.setAvatar("avatar");
        user.setEmail("email");
        user.setId(1);
        user.setFirstName("first name");
        user.setLastName("Last name");

        return user;
    }

    public static UserResponse createUserResponse() {
        final UserResponse userResponse = new UserResponse();

        userResponse.setUser(createUser());
        userResponse.setSupport(createSupport());

        return userResponse;
    }

    public static HttpHeaders createHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
