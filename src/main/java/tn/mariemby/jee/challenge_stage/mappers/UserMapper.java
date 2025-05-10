package tn.mariemby.jee.challenge_stage.mappers;

import org.mapstruct.Mapper;
import tn.mariemby.jee.challenge_stage.auth.dtos.CreateUserDto;
import tn.mariemby.jee.challenge_stage.users.dtos.UserDto;
import tn.mariemby.jee.challenge_stage.users.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);

    CreateUserDto toCreateUserDto(User user);

    User toUser(CreateUserDto createUserDto);
}