package com.fancode.Fancode_Automation.service;

import com.fancode.Fancode_Automation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    private final RestTemplate restTemplate;

    /**
     * Constructor to initialize UserService with a RestTemplate.
     *
     * @param restTemplate the RestTemplate to be used for API calls
     */
    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches all users from the external API.
     *
     * @return a list of User objects
     * @throws RuntimeException if the API call fails
     */
    public List<User> fetchUsers() {
        try {
            User[] users = restTemplate.getForObject("http://jsonplaceholder.typicode.com/users", User[].class);
            return Arrays.asList(users);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch users", e);
        }
    }

    /**
     * Checks if a user belongs to the city FanCode.
     *
     * @param user the User object to check
     * @return true if the user belongs to FanCode, false otherwise
     */
    public boolean isFancodeCity(User user) {
        double lat = Double.parseDouble(user.getAddress().getGeo().getLat());
        double lng = Double.parseDouble(user.getAddress().getGeo().getLng());
        return -40 < lat && lat < 5 && 5 < lng && lng < 100;
    }

    /**
     * Asynchronously fetches users belonging to the city FanCode.
     *
     * @return a CompletableFuture containing a list of User objects belonging to FanCode
     */
    @Async
    public CompletableFuture<List<User>> getFancodeUsers() {
        return CompletableFuture.completedFuture(
                fetchUsers().stream()
                        .filter(this::isFancodeCity)
                        .collect(Collectors.toList())
        );
    }
}
