package com.org.ticketing.support.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {
    private Long id;
    private String content;
    private LocalDateTime sentAt;
    private UserResponse sender;
}
