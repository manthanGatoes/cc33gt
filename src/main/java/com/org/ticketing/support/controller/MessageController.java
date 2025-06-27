package com.org.ticketing.support.controller;

import com.org.ticketing.support.dto.request.PostMessageRequest;
import com.org.ticketing.support.dto.response.MessageResponse;
import com.org.ticketing.support.model.User;
import com.org.ticketing.support.service.MessageService;
import com.org.ticketing.support.util.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    private final MessageService messageService;
    private final AuthUtil authUtil;

    @PostMapping
    public MessageResponse postMessage(@Valid @RequestBody PostMessageRequest request) {
        User user = authUtil.getCurrentUser();
        log.info("current user is: {}", user.toString());
        return messageService.postMessage(request, authUtil.getCurrentUser());
    }

    @GetMapping("/ticket/{ticketId}")
    public List<MessageResponse> getMessages(@PathVariable Long ticketId) {
        return messageService.getMessagesForTicket(ticketId);
    }
}
