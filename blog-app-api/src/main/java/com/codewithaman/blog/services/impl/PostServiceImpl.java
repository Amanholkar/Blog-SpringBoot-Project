package com.codewithaman.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithaman.blog.config.AppConstants;
import com.codewithaman.blog.entities.Category;
import com.codewithaman.blog.entities.Post;
import com.codewithaman.blog.entities.User;
import com.codewithaman.blog.exceptions.ResourceNotFoundException;
import com.codewithaman.blog.payloads.PostDto;
import com.codewithaman.blog.payloads.PostResponse;
import com.codewithaman.blog.repositories.CategoryRepo;
import com.codewithaman.blog.repositories.PostRepo;
import com.codewithaman.blog.repositories.UserRepo;
import com.codewithaman.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {


     @Autowired
     private PostRepo postRepo;

     @Autowired
     private UserRepo userRepo;

     @Autowired
     private CategoryRepo categoryRepo;


     @Autowired
     private ModelMapper modelMapper;


    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {


        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ", "User Id ", userId));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id ", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");

        post.setAddDate(new Date());

        post.setUser(user);

        post.setCategory(category);


        Post newPost = this.postRepo.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
     
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id ", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
       

        this.postRepo.save(post);
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(Long postId) {
        
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post ", "Post Id ", postId));

        this.postRepo.delete(post);
        
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber , Integer pageSize ,String sortBy ,String sortDir) {



        Sort  sort = null;

        if(sortDir.equalsIgnoreCase(AppConstants.SORT_DIR)){

            sort = Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }


        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);


        Page<Post> pagePost = this.postRepo.findAll(pageable);

        List<Post> allPosts = pagePost.getContent();


     

        List<PostDto> postDtos = allPosts.stream().map(post-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        // List<UserDto> userDtolist =userlist.stream().map(user->this.userToDto(user)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContant(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long postId) {
        
        Post post =   this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id ", postId));


        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        
       Category category =  this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "category Id ", categoryId));

      List<Post> posts = this.postRepo.findByCategory(category);

      List<PostDto> postDtos =  posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Long userId) {
      
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ", "User ID", userId));

        List<Post> posts= this.postRepo.findByUser(user);

        List<PostDto> postDtos =       posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
       
         List<Post> posts =   this.postRepo.findByTitleContaining(keyword);


         List<PostDto> postDto =    posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDto;
    }


    

  
    
}
