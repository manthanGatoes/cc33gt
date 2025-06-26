package com.org.ticketing.support.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostMessageRequest {
    @NotNull
    private Long ticketId;

    @NotBlank
    private String content;
}
