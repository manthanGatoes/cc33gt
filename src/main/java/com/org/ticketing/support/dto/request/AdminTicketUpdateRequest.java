package com.org.ticketing.support.dto.request;

import lombok.Data;

@Data
public class AdminTicketUpdateRequest {
    private String status;
    private Long assigneeId;
}
