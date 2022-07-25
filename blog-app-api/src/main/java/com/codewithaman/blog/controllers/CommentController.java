package com.codewithaman.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithaman.blog.payloads.ApiResponse;
import com.codewithaman.blog.payloads.CommentDto;
import com.codewithaman.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {


    @Autowired
    CommentService commentService ;

    // Create Comment 
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<ApiResponse> createComment(
        @RequestBody CommentDto  commentDto, 
        @PathVariable("postId") Long postId){


          CommentDto commentDto2 =   this.commentService.createComment(commentDto, postId);



            return new ResponseEntity<>(new ApiResponse(commentDto2,"success","1"),HttpStatus.CREATED);

        }



        // Delete Comment

        @DeleteMapping("/comment/{commentId}")
        public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Long commentId){

            this.commentService.deleteComment(commentId);

            return new ResponseEntity<>(new ApiResponse(null,"success","1"),HttpStatus.OK);
        }
    
}
