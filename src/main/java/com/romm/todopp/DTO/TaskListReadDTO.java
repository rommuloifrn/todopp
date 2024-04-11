package com.romm.todopp.DTO;

import java.time.Instant;
import java.util.Date;

public record TaskListReadDTO(
    Long id,
    String title, 
    String description, 
    String ownerUsername, 
    boolean isPublic,
    Date deadline,
    Instant createdAt,
    String progress
    ) {
    
}
