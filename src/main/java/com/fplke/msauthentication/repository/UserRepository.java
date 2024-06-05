package com.fplke.msauthentication.repository;

import com.fplke.msauthentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByTeamId(Long teamId);
}
