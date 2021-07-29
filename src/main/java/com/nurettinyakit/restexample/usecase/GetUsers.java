package com.nurettinyakit.restexample.usecase;

import com.nurettinyakit.restexample.gateway.userservice.UserServiceGateway;
import com.nurettinyakit.restexample.gateway.userservice.dto.UsersResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetUsers implements UseCase<Void, Optional<UsersResponse>> {

    private final UserServiceGateway userServiceGateway;

    @Override
    public Optional<UsersResponse> execute(final Void request) {
        return userServiceGateway.getUsers();
    }

}
