package com.org.ticketing.support.controller;

import com.org.ticketing.support.dto.request.CreateTicketRequest;
import com.org.ticketing.support.dto.response.TicketResponse;
import com.org.ticketing.support.model.User;
import com.org.ticketing.support.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // TEMP: Simulate logged-in user
    private User getCurrentUser() {
        // Replace with actual logic after JWT auth
        User u = new User();
        u.setId(1L);
        u.setEmail("customer@example.com");
        u.setName("Customer One");
        return u;
    }

    @PostMapping
    public TicketResponse createTicket(@Valid @RequestBody CreateTicketRequest request) {
        return ticketService.createTicket(request, getCurrentUser());
    }

    @GetMapping("/my")
    public List<TicketResponse> getMyTickets() {
        return ticketService.getTicketsForCustomer(getCurrentUser());
    }

    @PutMapping("/{ticketId}/status")
    public TicketResponse updateStatusOrAssignee(
            @PathVariable Long ticketId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long assigneeId
    ) {
        return ticketService.updateTicketStatusAndAssignee(ticketId, status, assigneeId);
    }

}
