package com.org.ticketing.support.service.impl;

import com.org.ticketing.support.dto.request.AdminTicketUpdateRequest;
import com.org.ticketing.support.dto.request.AssignTicketRequest;
import com.org.ticketing.support.dto.request.StatusUpdateRequest;
import com.org.ticketing.support.model.TicketStatus;
import com.org.ticketing.support.repository.UserRepository;
import com.org.ticketing.support.util.AuthUtil;
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

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final AuthUtil authUtil;

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

    @Override
    public List<TicketResponse> getAssignedTickets() {
        User currentAgent = authUtil.getCurrentUser();
        List<Ticket> tickets = ticketRepository.findByAgent(currentAgent);
        return tickets.stream()
                .map(ticketMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponse> getAllTickets() { // add pagination here
        return ticketRepository.findAll().stream()
                .map(ticketMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponse updateAssignedTicketStatus(Long id, StatusUpdateRequest request) {
        User currentAgent = authUtil.getCurrentUser();
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getAgent().equals(currentAgent)) {
            throw new RuntimeException("You are not assigned to this ticket");
        }

        ticket.setStatus(TicketStatus.valueOf(request.getStatus()));
        Ticket updated = ticketRepository.save(ticket);

        // 🔴 WebSocket: notify ticket status update
        messagingTemplate.convertAndSend("/topic/ticket/status", ticketMapper.toDto(updated));

        return ticketMapper.toDto(updated);
    }

    @Override
    public TicketResponse adminUpdateTicket(Long id, AdminTicketUpdateRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (request.getStatus() != null) {
            ticket.setStatus(TicketStatus.valueOf(request.getStatus()));
        }

        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            ticket.setAgent(assignee);
        }

        Ticket updated = ticketRepository.save(ticket);

        // 🔴 WebSocket broadcast
        messagingTemplate.convertAndSend("/topic/ticket/status", ticketMapper.toDto(updated));

        return ticketMapper.toDto(updated);
    }

    @Override
    public TicketResponse assignTicket(Long ticketId, AssignTicketRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User agent = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        ticket.setAgent(agent);
        Ticket updated = ticketRepository.save(ticket);

        TicketResponse response = ticketMapper.toDto(updated);

        // 🔴 WebSocket broadcast to the assigned agent
        messagingTemplate.convertAndSendToUser(
                String.valueOf(agent.getId()),
                "/queue/ticket-assigned",
                response
        );

        return response;
    }





}
