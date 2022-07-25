package com.codewithaman.blog.services;

import com.codewithaman.blog.payloads.CommentDto;

public interface CommentService {


    //Create Comment

    public CommentDto createComment(CommentDto commentDto, Long postId);

    //Delete Comment

    public void deleteComment(Long commentId);
    
}
