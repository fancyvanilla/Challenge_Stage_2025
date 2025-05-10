package tn.mariemby.jee.challenge_stage.auth.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.mariemby.jee.challenge_stage.auth.dtos.CreateUserDto;
import tn.mariemby.jee.challenge_stage.auth.dtos.LoginUserDto;
import tn.mariemby.jee.challenge_stage.auth.services.AuthService;
import tn.mariemby.jee.challenge_stage.users.dtos.UserDto;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Inscription d'un utilisateur",
            description = "Cette endpoint permet à un utilisateur de s'inscrire en fournissant ses informations personnelles. L'utilisateur doit fournir un nom, un prénom, une adresse e-mail et un mot de passe."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur inscrit avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'inscription invalides"),
            @ApiResponse(responseCode = "409", description = "L'utilisateur existe déjà"),
    })

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid CreateUserDto createUserDto){
        UserDto userDto = authService.signup(createUserDto);
        if (userDto == null) {
            return ResponseEntity.status(409).body("L'utilisateur existe déjà");
        }
        return ResponseEntity.ok(userDto);

    }
    @Operation(
            summary = "Connexion d'un utilisateur",
            description = "Cette endpoint permet à un utilisateur de se connecter en fournissant son adresse e-mail et son mot de passe. Si les informations d'identification sont valides, un jeton JWT est renvoyé."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie"),
            @ApiResponse(responseCode = "400", description = "Informations d'identification invalides"),
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginUserDto loginUserDto){
       String token=authService.login(loginUserDto);
       if (token==null){
           return ResponseEntity.status(400).body("Informations d'identification invalides");
       }
          return ResponseEntity.ok(token);
    }
}
