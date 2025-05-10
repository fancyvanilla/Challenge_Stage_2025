package tn.mariemby.jee.challenge_stage.auth.dtos;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String mot_de_passe;
}
