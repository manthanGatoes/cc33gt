package com.org.ticketing.support.service.impl;

import com.org.ticketing.support.model.TicketStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.org.ticketing.support.dto.request.CreateTicketRequest;
import com.org.ticketing.support.dto.response.TicketResponse;
import com.org.ticketing.support.mapper.TicketMapper;
import com.org.ticketing.support.model.Ticket;
import com.org.ticketing.support.model.User;
import com.org.ticketing.support.repository.TicketRepository;
import com.org.ticketing.support.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public TicketResponse createTicket(CreateTicketRequest request, User customer) {
        Ticket ticket = new Ticket();
        ticket.setSubject(request.getSubject());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setCustomer(customer);

        ticket = ticketRepository.save(ticket);
        TicketResponse response = ticketMapper.toDto(ticket);
        messagingTemplate.convertAndSend("/topic/tickets", response);
        return response;
    }

    @Override
    public List<TicketResponse> getTicketsForCustomer(User customer) {
        return ticketRepository.findByCustomer(customer)
                .stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    @Override
    public List<TicketResponse> getTicketsForAgent(User agent) {
        return ticketRepository.findByAgent(agent)
                .stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    @Override
    public TicketResponse getTicketById(Long id) {
        return ticketMapper.toDto(
                ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"))
        );
    }

    @Override
    public TicketResponse updateTicketStatusAndAssignee(Long ticketId, String status, Long assigneeId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (status != null) {
            ticket.setStatus(TicketStatus.valueOf(status));
        }

        if (assigneeId != null) {
            User assignee = new User();
            assignee.setId(assigneeId); // In production, fetch from DB
            assignee.setName("dummy-assignee");
            ticket.setAgent(assignee);
        }

        ticket = ticketRepository.save(ticket);
        TicketResponse response = ticketMapper.toDto(ticket);

        // Notify clients
        messagingTemplate.convertAndSend("/topic/tickets/" + ticket.getId() + "/status", response);

        return response;
    }

}
