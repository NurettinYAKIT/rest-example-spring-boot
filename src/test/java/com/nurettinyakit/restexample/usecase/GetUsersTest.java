package com.nurettinyakit.restexample.usecase;

import com.nurettinyakit.restexample.gateway.userservice.UserServiceGateway;
import com.nurettinyakit.restexample.gateway.userservice.dto.UsersResponse;
import com.nurettinyakit.restexample.testutils.TestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUsersTest {

    @Mock
    private UserServiceGateway gateway;

    @InjectMocks
    private GetUsers getUsers;

    @Test
    void shouldGetUsers() {
        //GIVEN
        when(gateway.getUsers()).thenReturn(Optional.of(TestObjectFactory.createUsersResponse()));

        //WHEN
        final Optional<UsersResponse> response = getUsers.execute(null);

        //THEN
        assertThat(response).isPresent();
        assertThat(response.get().getTotalPages()).isEqualTo(3);
        assertThat(response.get().getTotal()).isEqualTo(6);
        assertThat(response.get().getPage()).isEqualTo(1);
        assertThat(response.get().getUsers().get(0).getId()).isEqualTo(1);

        verify(gateway, atLeastOnce()).getUsers();
    }

}
