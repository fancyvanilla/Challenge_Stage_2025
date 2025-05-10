package tn.mariemby.jee.challenge_stage.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.mariemby.jee.challenge_stage.users.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
