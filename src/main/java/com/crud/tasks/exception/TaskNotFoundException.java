package com.crud.tasks.exception;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String msg) {
        super(msg);
    }
}
