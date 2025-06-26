package com.org.ticketing.support.dto.response;

import com.org.ticketing.support.model.Priority;
import com.org.ticketing.support.model.TicketStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketResponse {
    private Long id;
    private String subject;
    private String description;
    private TicketStatus status;
    private Priority priority;
    private LocalDateTime createdAt;
    private UserResponse customer;
    private UserResponse agent;
}
