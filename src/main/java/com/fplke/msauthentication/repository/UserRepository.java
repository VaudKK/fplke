package com.fplke.msauthentication.repository;

import com.fplke.msauthentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
