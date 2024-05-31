package com.fancode.Fancode_Automation.service;

import com.fancode.Fancode_Automation.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckUserCompletion() throws ExecutionException, InterruptedException {
        Todo todo1 = new Todo();
        todo1.setUserId(1);
        todo1.setId(1);
        todo1.setTitle("Task1");
        todo1.setCompleted(true);

        Todo todo2 = new Todo();
        todo2.setUserId(1);
        todo2.setId(2);
        todo2.setTitle("Task2");
        todo2.setCompleted(false);

        Todo todo3 = new Todo();
        todo3.setUserId(1);
        todo3.setId(3);
        todo3.setTitle("Task3");
        todo3.setCompleted(true);

        List<Todo> todos = Arrays.asList(todo1, todo2, todo3);

        when(restTemplate.getForObject(anyString(), eq(Todo[].class))).thenReturn(todos.toArray(new Todo[0]));

        CompletableFuture<Boolean> isCompletedFuture = todoService.checkUserCompletion(1);
        boolean isCompleted = isCompletedFuture.get();

        assertTrue(isCompleted);
    }
}

