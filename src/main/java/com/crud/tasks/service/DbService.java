package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.exception.TaskNotFoundException;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbService {

    private final TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTask(final Long id) throws TaskNotFoundException {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with given id doesn't exist"));
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    public void deleteTask(final Long id) throws TaskNotFoundException {
        repository.deleteById(id);
    }
}
