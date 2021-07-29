package com.nurettinyakit.restexample.usecase;

import com.nurettinyakit.restexample.gateway.userservice.UserServiceGateway;
import com.nurettinyakit.restexample.gateway.userservice.dto.UserResponse;
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
class GetUserTest {

    @Mock
    private UserServiceGateway gateway;

    @InjectMocks
    private GetUser getUser;

    @Test
    void shouldGetUser() {
        //GIVEN
        when(gateway.getUser(anyString())).thenReturn(Optional.of(TestObjectFactory.createUserResponse()));

        //WHEN
        final Optional<UserResponse> response = getUser.execute("1");

        //THEN
        assertThat(response).isPresent();
        assertThat(response.get().getUser().getId()).isEqualTo(1);
        assertThat(response.get().getUser().getLastName()).isEqualTo("Last name");

        verify(gateway, atLeastOnce()).getUser("1");
    }

}
