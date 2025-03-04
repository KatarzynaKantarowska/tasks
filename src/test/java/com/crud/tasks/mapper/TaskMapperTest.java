package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskMapperTest {
    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        taskMapper = new TaskMapper();
    }

    @Test
    void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Test Content");

        //When
        Task result = taskMapper.mapToTask(taskDto);

        //Then
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        assertEquals("Test Content", result.getContent());
        assertEquals(1L, result.getId());
    }

    @Test
    void mapToTaskDto() {
        //Given
        Task task = new Task(1L, "Test Task", "Test Content");

        //When
        TaskDto result = taskMapper.mapToTaskDto(task);

        //Then
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        assertEquals("Test Content", result.getContent());
        assertEquals(1L, result.getId());
    }

    @Test
    void mapToTaskDtoToList() {
        //Given
        List<Task> tasks = List.of(
                new Task(1L, "Test Task 1", "Test Content 1"),
                new Task(2L, "Test Task 2", "Test Content 2"));

        //When
        List<TaskDto> result = taskMapper.mapToTaskDtoToList(tasks);

        //Then
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Test Task 1", result.get(0).getTitle());
        assertEquals("Test Task 2", result.get(1).getTitle());
        assertEquals("Test Content 1", result.get(0).getContent());
        assertEquals("Test Content 2", result.get(1).getContent());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());

    }
}