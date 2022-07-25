package com.codewithaman.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithaman.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role,Long>  {
    
}
