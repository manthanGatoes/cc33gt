package com.org.ticketing.support.service.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.org.ticketing.support.dto.request.PostMessageRequest;
import com.org.ticketing.support.dto.response.MessageResponse;
import com.org.ticketing.support.mapper.MessageMapper;
import com.org.ticketing.support.model.Message;
import com.org.ticketing.support.model.Ticket;
import com.org.ticketing.support.model.User;
import com.org.ticketing.support.repository.MessageRepository;
import com.org.ticketing.support.repository.TicketRepository;
import com.org.ticketing.support.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final TicketRepository ticketRepository;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;


    @Override
    public MessageResponse postMessage(PostMessageRequest request, User sender) {
        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Message message = new Message();
        message.setTicket(ticket);
        message.setSender(sender);
        message.setContent(request.getContent());

        message = messageRepository.save(message);
        MessageResponse response = messageMapper.toDto(message);
        messagingTemplate.convertAndSend(
                "/topic/tickets/" + ticket.getId(),
                response
        );
        return response;

    }

    @Override
    public List<MessageResponse> getMessagesForTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return messageRepository.findByTicketOrderBySentAtAsc(ticket)
                .stream()
                .map(messageMapper::toDto)
                .toList();
    }
}
