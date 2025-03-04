package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.exception.TaskNotFoundException;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void getAllTasks() {
        //Given
        List<Task> tasks = List.of(new Task(1L, "Test Task", "Test Content"));
        when(taskRepository.findAll()).thenReturn(tasks);

        //When
        List<Task> result = dbService.getAllTasks();

        //Then
        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
        verify(taskRepository).findAll();
    }

    @Test
    void getTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L, "Test Task", "Test Content");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //When
        Task result = dbService.getTask(1L);

        //Then
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository).findById(1L);

    }

    @Test
    void saveTask() {
        //Given
        Task task = new Task(1L, "Test Task", "Test Content");
        when(taskRepository.save(task)).thenReturn(task);

        //When
        Task result = dbService.saveTask(task);

        //Then
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void deleteTask() throws TaskNotFoundException {
        //Given
        Long taskId = 1L;
        doNothing().when(taskRepository).deleteById(taskId);

        //When
        dbService.deleteTask(1L);

        //Then
        verify(taskRepository).deleteById(taskId);
    }
}