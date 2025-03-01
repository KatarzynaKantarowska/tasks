package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrelloMapperTest {
    private TrelloMapper trelloMapper;
    @BeforeEach
    void setUp() {
        trelloMapper = new TrelloMapper();
    }

    @Test
    void mapToBoards() {
        //Given
        List<TrelloBoardDto> boardDtos = List.of(
                new TrelloBoardDto("1","Board 1",List.of(new TrelloListDto("10","List 1", false)))
        );

        //When
        List<TrelloBoard> result = trelloMapper.mapToBoards(boardDtos);

        //Then
        assertNotNull(result);
        assertEquals(result.size(), boardDtos.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Board 1", result.get(0).getName());

    }

    @Test
    void mapToBoardsDto() {

        //Given
        List<TrelloBoard> boards = List.of(
                new TrelloBoard("1","Board 1",List.of(new TrelloList("10","List 1", false))));
        //When
        List<TrelloBoardDto> result = trelloMapper.mapToBoardsDto(boards);

        //Then
        assertNotNull(result);
        assertEquals(result.size(), boards.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Board 1", result.get(0).getName());
    }

    @Test
    void mapToList() {

        //Given
        List<TrelloList> list = List.of(new TrelloList("10","List 1", true));


        //When
        List<TrelloListDto> result = trelloMapper.mapToListDto(list);

        //Then
        assertNotNull(result);
        assertEquals(result.size(), list.size());
        assertEquals("10", result.get(0).getId());
        assertEquals("List 1", result.get(0).getName());

    }

    @Test
    void mapToListDto() {
        //Given
        List<TrelloListDto> list = List.of(new TrelloListDto("10","List 1", true));

        //When
        List<TrelloList> result = trelloMapper.mapToList(list);

        //Then
        assertNotNull(result);
        assertEquals(result.size(), list.size());
        assertEquals("10", result.get(0).getId());
        assertEquals("List 1", result.get(0).getName());
    }

    @Test
    void mapToCardDto() {
        //Given
        TrelloCard card = new TrelloCard("Card 1", "Description", "top", "1");

        //When
        TrelloCardDto result = trelloMapper.mapToCardDto(card);

        //Then
        assertNotNull(result);
        assertEquals("Card 1", result.getName());
        assertEquals("Description", result.getDescription());
        assertEquals("top", result.getPos());
        assertEquals("1", result.getListId());
    }

    @Test
    void mapToNewCard() {
        //Given
        CreatedTrelloCardDto newCardDto = new CreatedTrelloCardDto("1","New card 1","www.card.com");

        //When
        CreatedTrelloCardDto result = trelloMapper.mapToNewCardDto(newCardDto);

        //Then
        assertNotNull(result);
        assertEquals("New card 1", result.getId());
        assertEquals("www.card.com", result.getShortUrl());
        assertEquals("1", result.getName());
    }

    @Test
    void mapToCard() {
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("Card 1", "Description", "top", "1");

        //When
        TrelloCard result = trelloMapper.mapToCard(cardDto);

        //Then
        assertNotNull(result);
        assertEquals("Card 1", result.getName());
        assertEquals("Description", result.getDescription());
        assertEquals("top", result.getPos());
        assertEquals("1", result.getListId());
    }
}