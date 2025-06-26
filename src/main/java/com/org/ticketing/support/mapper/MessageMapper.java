package com.org.ticketing.support.mapper;

import com.org.ticketing.support.dto.response.MessageResponse;
import com.org.ticketing.support.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface MessageMapper {

    @Mapping(source = "sender", target = "sender")
    MessageResponse toDto(Message message);
}
