package com.nurettinyakit.restexample.gateway.controller;

import com.nurettinyakit.restexample.gateway.userservice.dto.UserResponse;
import com.nurettinyakit.restexample.gateway.userservice.dto.UsersResponse;
import com.nurettinyakit.restexample.usecase.UseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UseCase<Void, Optional<UsersResponse>> getUsers;
    private final UseCase<String, Optional<UserResponse>> getUser;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all users.")
    @ApiResponse(responseCode = "200", description = "OK",
        content = @Content(schema = @Schema(implementation = UsersResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found.", content = @Content)
    public ResponseEntity<UsersResponse> getUsers() {
        log.info("Getting all users");
        return getUsers.execute(null)
            .map(ResponseEntity::ok)
            .orElse(notFound().build());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve user information of the given id")
    @ApiResponse(responseCode = "200", description = "OK",
        content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") final String userId) {
        log.info("Retrieving user information of the user {}", userId);
        return getUser.execute(userId)
            .map(ResponseEntity::ok)
            .orElse(notFound().build());
    }

}
