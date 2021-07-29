package com.nurettinyakit.restexample.usecase;

import com.nurettinyakit.restexample.gateway.userservice.dto.UserResponse;
import com.nurettinyakit.restexample.gateway.userservice.UserServiceGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetUser implements UseCase<String, Optional<UserResponse>> {

    private final UserServiceGateway userServiceGateway;

    @Override
    public Optional<UserResponse> execute(String id) {
        return userServiceGateway.getUser(id);
    }
}
