package com.org.ticketing.support.mapper;

import com.org.ticketing.support.dto.response.TicketResponse;
import com.org.ticketing.support.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TicketMapper {

    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "agent", target = "agent")
    TicketResponse toDto(Ticket ticket);
}
