package com.codewithaman.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithaman.blog.entities.Comment;
import com.codewithaman.blog.entities.Post;
import com.codewithaman.blog.exceptions.ResourceNotFoundException;
import com.codewithaman.blog.payloads.CommentDto;
import com.codewithaman.blog.repositories.CommentRepo;
import com.codewithaman.blog.repositories.PostRepo;
import com.codewithaman.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
      
       Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));

       Comment comment = this.modelMapper.map(commentDto, Comment.class);

       comment.setPost(post);

       Comment saveComment =  this.commentRepo.save(comment);
        return this.modelMapper.map(saveComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        
      Comment comment =   this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));
        
      this.commentRepo.delete(comment);
    }
    
}
