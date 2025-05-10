package tn.mariemby.jee.challenge_stage.tasks.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

@Data
public class CreateTaskDto {
    String titre;
    String description;
    @JsonIgnore
    @AssertTrue(message = "Le titre doit etre non vide")
    public boolean isTitleValid() {
        return titre != null && !titre.trim().isEmpty();
    }
    @JsonIgnore
    @AssertTrue(message = "L'email doit etre non vide")
    public boolean isDescriptionValid() {
        return description != null && !description.trim().isEmpty();
    }
}
