package com.org.ticketing.support.service;

import com.org.ticketing.support.dto.request.CreateTicketRequest;
import com.org.ticketing.support.dto.response.TicketResponse;
import com.org.ticketing.support.model.User;

import java.util.List;

public interface TicketService {
    TicketResponse createTicket(CreateTicketRequest request, User customer);
    List<TicketResponse> getTicketsForCustomer(User customer);
    List<TicketResponse> getTicketsForAgent(User agent);
    TicketResponse getTicketById(Long id);
}
