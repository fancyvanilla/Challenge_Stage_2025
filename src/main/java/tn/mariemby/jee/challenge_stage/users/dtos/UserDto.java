package tn.mariemby.jee.challenge_stage.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import tn.mariemby.jee.challenge_stage.users.entities.Role;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String nom;
    private String email;
    private Role role;
}
