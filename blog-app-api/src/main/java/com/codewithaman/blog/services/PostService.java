package com.codewithaman.blog.services;

import java.util.List;

import com.codewithaman.blog.payloads.PostDto;
import com.codewithaman.blog.payloads.PostResponse;

public interface PostService {


    //Create 

      public PostDto createPost(PostDto postDto,Long userId,Long  categoryId);

    //Update 

      public PostDto updatePost(PostDto postDto,Long postId);


    //Delete

    public void deletePost(Long postId);

    //Get All
    
    public PostResponse getAllPost(Integer pageNumber , Integer pageSize ,String sortBy ,String sortDir);

    // Get single Port

    public PostDto getPostById(Long postId);

    //get all posts by category

    public List<PostDto> getPostsByCategory(Long categoryId);

    //get all posts by user

    public List<PostDto> getPostsByUser(Long userId);

    // search Post

    List<PostDto> searchPosts(String keyword);
}
