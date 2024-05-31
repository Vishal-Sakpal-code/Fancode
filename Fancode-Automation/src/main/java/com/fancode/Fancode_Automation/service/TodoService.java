package com.fancode.Fancode_Automation.service;

import com.fancode.Fancode_Automation.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service class for managing todos.
 */
@Service
public class TodoService {

    private final RestTemplate restTemplate;

    /**
     * Constructor to initialize TodoService with a RestTemplate.
     *
     * @param restTemplate the RestTemplate to be used for API calls
     */
    @Autowired
    public TodoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches all todos for a specific user from the external API.
     *
     * @param userId the ID of the user
     * @return a list of Todo objects
     * @throws RuntimeException if the API call fails
     */
    public List<Todo> fetchTodos(int userId) {
        try {
            Todo[] todos = restTemplate.getForObject("http://jsonplaceholder.typicode.com/todos?userId=" + userId, Todo[].class);
            return Arrays.asList(todos);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch todos for user: " + userId, e);
        }
    }

    /**
     * Calculates the completion percentage of a list of todos.
     *
     * @param todos the list of Todo objects
     * @return the completion percentage
     */
    public double calculateCompletionPercentage(List<Todo> todos) {
        long total = todos.size();
        long completed = todos.stream().filter(Todo::isCompleted).count();
        return total > 0 ? (double) completed / total * 100 : 0;
    }

    /**
     * Asynchronously checks if a user has more than 50% of their todos completed.
     *
     * @param userId the ID of the user
     * @return a CompletableFuture containing a boolean indicating if the user's completed task percentage is greater than 50%
     */
    @Async
    public CompletableFuture<Boolean> checkUserCompletion(int userId) {
        List<Todo> todos = fetchTodos(userId);
        boolean isCompleted = calculateCompletionPercentage(todos) > 50;
        return CompletableFuture.completedFuture(isCompleted);
    }
}
