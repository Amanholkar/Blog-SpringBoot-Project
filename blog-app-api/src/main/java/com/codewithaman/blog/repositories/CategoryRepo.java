package com.codewithaman.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithaman.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Long>{
    
}
