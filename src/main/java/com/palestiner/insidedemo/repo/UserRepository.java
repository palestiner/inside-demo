package com.palestiner.insidedemo.repo;

import com.palestiner.insidedemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
