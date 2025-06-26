package com.org.ticketing.support.repository;

import com.org.ticketing.support.model.Message;
import com.org.ticketing.support.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByTicketOrderBySentAtAsc(Ticket ticket);
}
