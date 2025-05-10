package tn.mariemby.jee.challenge_stage.auth.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

@Data
public class CreateUserDto {
    private String nom;
    private String email;
    private String mot_de_passe;
    @JsonIgnore
    @AssertTrue(message = "Le nom doit etre non vide")
    public boolean isNameValid() {
        return nom != null && !nom.trim().isEmpty();
    }

    @JsonIgnore
    @AssertTrue(message = "L'email doit etre non vide")
    public boolean isEmailValid() {
        return email != null && !email.trim().isEmpty();
    }

    @JsonIgnore
    @AssertTrue(message = "Le mot de passe doit etre non vide et avoir au moins 8 caractères")
    public boolean isPasswordValid() {
        return mot_de_passe != null &&  !mot_de_passe.trim().isEmpty() && mot_de_passe.length() >= 8 ;
    }
}