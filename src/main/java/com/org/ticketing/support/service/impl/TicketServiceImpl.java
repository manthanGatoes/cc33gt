package com.org.ticketing.support.service.impl;

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

    @Override
    public TicketResponse createTicket(CreateTicketRequest request, User customer) {
        Ticket ticket = new Ticket();
        ticket.setSubject(request.getSubject());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setCustomer(customer);

        ticket = ticketRepository.save(ticket);
        return ticketMapper.toDto(ticket);
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
}
