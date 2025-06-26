package com.org.ticketing.support.repository;

import com.org.ticketing.support.model.Ticket;
import com.org.ticketing.support.model.TicketStatus;
import com.org.ticketing.support.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCustomer(User customer);
    List<Ticket> findByAgent(User agent);
    List<Ticket> findByStatus(TicketStatus status);
}
