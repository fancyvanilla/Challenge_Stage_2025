package tn.mariemby.jee.challenge_stage.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tn.mariemby.jee.challenge_stage.auth.dtos.CreateUserDto;
import tn.mariemby.jee.challenge_stage.auth.dtos.LoginUserDto;
import tn.mariemby.jee.challenge_stage.mappers.UserMapper;
import tn.mariemby.jee.challenge_stage.users.dtos.UserDto;
import tn.mariemby.jee.challenge_stage.users.entities.User;
import tn.mariemby.jee.challenge_stage.users.services.UserService;

@Service
public class AuthService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private final JwtService jwtService;

    @Autowired
    public AuthService(UserService userService, UserMapper userMapper, JwtService jwtService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    public UserDto signup(CreateUserDto createUserDto) {
        User user = userService.getUserByEmail(createUserDto.getEmail());
        if (user != null) {
            return null;
        }
        User userToCreate = userMapper.toUser(createUserDto);
        userToCreate.setMot_de_passe(encoder.encode(userToCreate.getMot_de_passe()));
        return userMapper.toUserDto(userService.createOrUpdateUser(userToCreate));

    }

    public String login(LoginUserDto loginUserDto) {
        User user = userService.getUserByEmail(loginUserDto.getEmail());
        if (user == null || !encoder.matches(loginUserDto.getMot_de_passe(), user.getMot_de_passe())) {
            return null;
        }
        return jwtService.createJwtToken(userMapper.toUserDto(user));
    }
}
