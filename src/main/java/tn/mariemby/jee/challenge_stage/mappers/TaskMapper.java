package tn.mariemby.jee.challenge_stage.mappers;

import org.mapstruct.Mapper;
import tn.mariemby.jee.challenge_stage.auth.dtos.CreateUserDto;
import tn.mariemby.jee.challenge_stage.tasks.dtos.CreateTaskDto;
import tn.mariemby.jee.challenge_stage.tasks.entities.Task;
import tn.mariemby.jee.challenge_stage.users.dtos.UserDto;
import tn.mariemby.jee.challenge_stage.users.entities.User;

@Mapper(componentModel = "spring")
public interface TaskMapper {
 Task toTask(CreateTaskDto taskDto);
}