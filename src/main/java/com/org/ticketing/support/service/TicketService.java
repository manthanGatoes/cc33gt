package com.org.ticketing.support.service;

import com.org.ticketing.support.dto.request.AdminTicketUpdateRequest;
import com.org.ticketing.support.dto.request.AssignTicketRequest;
import com.org.ticketing.support.dto.request.CreateTicketRequest;
import com.org.ticketing.support.dto.request.StatusUpdateRequest;
import com.org.ticketing.support.dto.response.TicketResponse;
import com.org.ticketing.support.model.User;

import java.util.List;

public interface TicketService {
    TicketResponse createTicket(CreateTicketRequest request, User customer);
    List<TicketResponse> getTicketsForCustomer(User customer);
    List<TicketResponse> getTicketsForAgent(User agent);
    TicketResponse getTicketById(Long id);
//    TicketResponse updateTicketStatusAndAssignee(Long ticketId, String status, Long assigneeId);
    List<TicketResponse> getAssignedTickets();

    List<TicketResponse> getAllTickets();

    TicketResponse updateAssignedTicketStatus(Long id, StatusUpdateRequest request);

    TicketResponse adminUpdateTicket(Long id, AdminTicketUpdateRequest request);
    TicketResponse assignTicket(Long ticketId, AssignTicketRequest request);

}
