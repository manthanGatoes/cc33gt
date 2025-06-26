package com.org.ticketing.support.controller;

import com.org.ticketing.support.dto.request.PostMessageRequest;
import com.org.ticketing.support.dto.response.MessageResponse;
import com.org.ticketing.support.model.User;
import com.org.ticketing.support.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // TEMP: Simulate logged-in user
    private User getCurrentUser() {
        User u = new User();
        u.setId(1L);
        u.setEmail("customer@example.com");
        u.setName("Customer One");
        return u;
    }

    @PostMapping
    public MessageResponse postMessage(@Valid @RequestBody PostMessageRequest request) {
        return messageService.postMessage(request, getCurrentUser());
    }

    @GetMapping("/ticket/{ticketId}")
    public List<MessageResponse> getMessages(@PathVariable Long ticketId) {
        return messageService.getMessagesForTicket(ticketId);
    }
}
