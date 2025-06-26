package com.org.ticketing.support.mapper;

import com.org.ticketing.support.dto.response.UserResponse;
import com.org.ticketing.support.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDto(User user);
}
