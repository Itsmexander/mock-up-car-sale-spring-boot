package com.example.test.repository;

import com.example.test.domain.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("jpa")
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
