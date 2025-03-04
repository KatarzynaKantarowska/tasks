package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrelloServiceTest {
    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TrelloMapper trelloMapper;

    @InjectMocks
    private TrelloService trelloService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchTrelloBoards() {
        //Given
        TrelloBoardDto boardDto = new TrelloBoardDto("1", "Test Board", List.of());
        when(trelloClient.getTrelloBoards()).thenReturn(Collections.singletonList(boardDto));

        //When
        List<TrelloBoardDto> boards = trelloService.fetchTrelloBoards();

        //Then
        assertNotNull(boards);
        assertEquals(1, boards.size());
        assertEquals("Test Board", boards.get(0).getName());
    }

    @Test
    void createTrelloCardDto() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test Card", "Description", "top", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Test Card", "http://trello.com");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("admin@test.com");
        when(trelloMapper.mapToNewCardDto(any(CreatedTrelloCardDto.class))).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto result = trelloService.createTrelloCardDto(trelloCardDto);

        //Then
        verify(emailService, times(1)).send(any(Mail.class));
        assertNotNull(result);
        assertEquals("Test Card", result.getName());
        assertEquals("http://trello.com", result.getShortUrl());
    }
}