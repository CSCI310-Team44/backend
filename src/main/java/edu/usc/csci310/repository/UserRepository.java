package edu.usc.csci310.repository;

import edu.usc.csci310.model.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    User findByUserId(Long UserId);
    User findByUsername(String username);
}
