package com.myproject.jobapp.repository;

import com.myproject.jobapp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query(value = "SELECT u FROM Users u WHERE u.email = :email")
    Optional<Users> findByEmail(String email);
}
