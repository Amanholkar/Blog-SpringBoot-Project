package com.codewithaman.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithaman.blog.entities.User;

public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
