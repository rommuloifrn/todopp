package com.romm.todopp.DTO;

public record AccountDTO (
    String username,
    String email,
    Integer activeListsQtt, 
    Integer inactiveListsQtt,
    Integer listsQtt
    ) {
    
}
