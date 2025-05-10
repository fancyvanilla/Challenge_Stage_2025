package tn.mariemby.jee.challenge_stage.tasks.dtos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import tn.mariemby.jee.challenge_stage.tasks.entities.Statut;

@Data
public class UpdateTaskDto {
    String status;
    @JsonIgnore
    @AssertTrue(message = "Le status doit etre valide soit 'A_FAIRE', 'EN_COURS' ou 'TERMINE'")
    public boolean isStatusValid() {
        return status != null && (status.equals(Statut.A_FAIRE.toString()) || status.equals(Statut.EN_COURS.toString()) || status.equals(Statut.TERMINE.toString()));
    }

}
