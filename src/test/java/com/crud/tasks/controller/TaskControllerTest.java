package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DbService dbService;

    @MockBean
    TaskMapper taskMapper;

    @Test
    void shouldFetchEmptyList() throws Exception {
        //Given
        when(dbService.getAllTasks()).thenReturn(List.of());
        when(taskMapper.mapToTaskDtoToList(anyList())).thenReturn(List.of());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchOneTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test task", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test task", "Test content");

        when(dbService.getTask(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test content")));

    }
    @Test
    void shouldFetchTaskList() throws Exception {
        // Given
        List<Task> tasks = List.of(new Task(1L, "Test Task", "Test Content"));
        List<TaskDto> taskDto = List.of(new TaskDto(1L, "Test Task", "Test Content"));

        when(dbService.getAllTasks()).thenReturn(List.of(new Task(1L, "Test Task", "Test Content")));
        when(taskMapper.mapToTaskDtoToList(anyList())).thenReturn(List.of(new TaskDto(1L, "Test Task", "Test Content")));


        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test Task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Test Content")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test Task", "Test Content");
        when(dbService.getTask(1L)).thenReturn(task);
        doNothing().when(dbService).deleteTask(1L);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void ShouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Updated Task", "Updated Content");
        Task task = new Task(1L, "Updated Task", "Updated Content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(any())).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(s))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Updated Task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Updated Content")));
    }

    @Test
    void ShouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "New Task", "New Content");
        Task task = new Task(1L, "New Task", "New Content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}