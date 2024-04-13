package com.romm.todopp.DTO;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import com.romm.todopp.entity.Link;

public record TaskListReadDTO(
    Long id,
    String title, 
    String description, 
    String ownerUsername, 
    boolean isPublic,
    Date deadline,
    Instant createdAt,
    String progress,
    List<Link> links
    ) {
    
}
