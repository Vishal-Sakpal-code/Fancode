package com.fancode.Fancode_Automation.controller;

import com.fancode.Fancode_Automation.model.User;
import com.fancode.Fancode_Automation.service.TodoService;
import com.fancode.Fancode_Automation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * REST controller for checking users' todo completion status.
 */
@RestController
public class FancodeController {

    private final UserService userService;
    private final TodoService todoService;

    /**
     * Constructor to initialize FancodeController with UserService and TodoService.
     *
     * @param userService the UserService to be used
     * @param todoService the TodoService to be used
     */
    @Autowired
    public FancodeController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    /**
     * Endpoint to check if all users in the city FanCode have more than 50% of their todos completed.
     *
     * @return a map with user IDs as keys and booleans indicating if the user's completed task percentage is greater than 50% as values
     * @throws ExecutionException if the computation threw an exception
     * @throws InterruptedException if the current thread was interrupted while waiting
     */
    @GetMapping("/checkUsersCompletion")
    public Map<Integer, Boolean> checkUsersCompletion() throws ExecutionException, InterruptedException {
        CompletableFuture<List<User>> fancodeUsersFuture = userService.getFancodeUsers();
        List<User> fancodeUsers = fancodeUsersFuture.get();

        List<CompletableFuture<Boolean>> completionFutures = fancodeUsers.stream()
                .map(user -> todoService.checkUserCompletion(user.getId()))
                .collect(Collectors.toList());

        CompletableFuture.allOf(completionFutures.toArray(new CompletableFuture[0])).join();

        return fancodeUsers.stream()
                .collect(Collectors.toMap(
                        User::getId,
                        user -> {
                            try {
                                return completionFutures.get(fancodeUsers.indexOf(user)).get();
                            } catch (InterruptedException | ExecutionException e) {
                                throw new RuntimeException("Error checking completion status for user: " + user.getId(), e);
                            }
                        }
                ));
    }
}

