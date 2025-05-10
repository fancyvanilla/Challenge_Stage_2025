package tn.mariemby.jee.challenge_stage.users.services;

import tn.mariemby.jee.challenge_stage.users.entities.User;

public interface UserService {
    User createOrUpdateUser(User user);
    User getUserByEmail(String email);
    void deleteUserById(Long id);
}
