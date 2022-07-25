package com.codewithaman.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithaman.blog.entities.Category;
import com.codewithaman.blog.entities.Post;
import com.codewithaman.blog.entities.User;

public interface PostRepo extends JpaRepository<Post,Long>{
    

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);

}
