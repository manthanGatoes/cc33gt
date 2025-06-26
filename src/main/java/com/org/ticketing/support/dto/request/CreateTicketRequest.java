package com.org.ticketing.support.dto.request;

import com.org.ticketing.support.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTicketRequest {
    @NotBlank
    private String subject;

    @NotBlank
    private String description;

    @NotNull
    private Priority priority;
}
