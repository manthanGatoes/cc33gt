package com.org.ticketing.support.controller;

import com.org.ticketing.support.dto.request.AdminTicketUpdateRequest;
import com.org.ticketing.support.dto.request.AssignTicketRequest;
import com.org.ticketing.support.dto.request.CreateTicketRequest;
import com.org.ticketing.support.dto.request.StatusUpdateRequest;
import com.org.ticketing.support.dto.response.TicketResponse;
import com.org.ticketing.support.service.TicketService;
import com.org.ticketing.support.util.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final AuthUtil authUtil;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public TicketResponse createTicket(@Valid @RequestBody CreateTicketRequest request) {
        return ticketService.createTicket(request, authUtil.getCurrentUser());
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my")
    public List<TicketResponse> getMyTickets() {
        return ticketService.getTicketsForCustomer(authUtil.getCurrentUser());
    }
    @PreAuthorize("hasRole('AGENT')")
    @PutMapping("/{ticketId}/status")
    public TicketResponse updateStatusOrAssignee(
            @PathVariable Long ticketId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long assigneeId
    ) {
        return ticketService.updateTicketStatusAndAssignee(ticketId, status, assigneeId);
    }

    @PreAuthorize("hasRole('AGENT')")
    @GetMapping("/assigned")
    public ResponseEntity<List<TicketResponse>> getAssignedTickets() {
        return ResponseEntity.ok(ticketService.getAssignedTickets());
    }

    // ADMIN: View all tickets
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    // AGENT: Update assigned ticket's status
    @PreAuthorize("hasRole('AGENT')")
    @PutMapping("/{id}/status")
    public ResponseEntity<TicketResponse> updateTicketStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(ticketService.updateAssignedTicketStatus(id, request));
    }

    // ADMIN: Update ticket assignee/status
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/admin-update")
    public ResponseEntity<TicketResponse> adminUpdateTicket(
            @PathVariable Long id,
            @RequestBody AdminTicketUpdateRequest request) {
        return ResponseEntity.ok(ticketService.adminUpdateTicket(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/assign")
    public ResponseEntity<TicketResponse> assignTicketToAgent(
            @PathVariable Long id,
            @RequestBody AssignTicketRequest request) {
        return ResponseEntity.ok(ticketService.assignTicket(id, request));
    }

}
