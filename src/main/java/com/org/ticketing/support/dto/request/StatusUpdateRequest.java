package com.org.ticketing.support.dto.request;

import lombok.Data;

@Data
public class StatusUpdateRequest {
    private String status; // "IN_PROGRESS", "RESOLVED", etc.
}
