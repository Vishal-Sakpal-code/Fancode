package com.fancode.Fancode_Automation.service;

import com.fancode.Fancode_Automation.model.Address;
import com.fancode.Fancode_Automation.model.Geo;
import com.fancode.Fancode_Automation.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFancodeUsers() throws ExecutionException, InterruptedException {
        User user1 = new User();
        user1.setId(1);
        user1.setName("User1");
        user1.setAddress(new Address());
        user1.getAddress().setGeo(new Geo());
        user1.getAddress().getGeo().setLat("1.0");
        user1.getAddress().getGeo().setLng("10.0");

        User user2 = new User();
        user2.setId(2);
        user2.setName("User2");
        user2.setAddress(new Address());
        user2.getAddress().setGeo(new Geo());
        user2.getAddress().getGeo().setLat("10.0");
        user2.getAddress().getGeo().setLng("100.0");

        User[] users = {user1, user2};

        when(restTemplate.getForObject(anyString(), eq(User[].class))).thenReturn(users);

        CompletableFuture<List<User>> fancodeUsers = userService.getFancodeUsers();

        assertEquals(1, fancodeUsers.get().get(0).getId());
    }
}
