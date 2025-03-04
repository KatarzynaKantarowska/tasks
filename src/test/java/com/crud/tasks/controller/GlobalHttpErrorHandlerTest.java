package com.crud.tasks.controller;

import com.crud.tasks.exception.TaskNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class GlobalHttpErrorHandlerTest {

    private final GlobalHttpErrorHandler globalHttpErrorHandler = new GlobalHttpErrorHandler();

    @Test
    void handleTaskNotFoundEx() {

        //Given
        TaskNotFoundException exception = new TaskNotFoundException("Task with given id doesn't exist");

        //When
        ResponseEntity<Object> responseEntity = globalHttpErrorHandler.handleTaskNotFoundEx(exception);

        //Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exception.getMessage(), responseEntity.getBody());


    }
}