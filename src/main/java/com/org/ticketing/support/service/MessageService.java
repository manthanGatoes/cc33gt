package com.org.ticketing.support.service;

import com.org.ticketing.support.dto.request.PostMessageRequest;
import com.org.ticketing.support.dto.response.MessageResponse;
import com.org.ticketing.support.model.User;

import java.util.List;

public interface MessageService {
    MessageResponse postMessage(PostMessageRequest request, User sender);
    List<MessageResponse> getMessagesForTicket(Long ticketId);
}
