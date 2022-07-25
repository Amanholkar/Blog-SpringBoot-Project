package com.codewithaman.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithaman.blog.config.AppConstants;
import com.codewithaman.blog.payloads.ApiResponse;
import com.codewithaman.blog.payloads.PostDto;
import com.codewithaman.blog.payloads.PostResponse;
import com.codewithaman.blog.services.FileService;
import com.codewithaman.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {


    @Autowired
    private PostService postService;

    // @Autowired
    // private UserService userService;

    // @Autowired
    // private CategoryService categoryService;


    // @Autowired
    // private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // create 

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<ApiResponse> createPost(
        @RequestBody PostDto postDto,
        @PathVariable Long userId,
        @PathVariable Long categoryId){


            PostDto post= this.postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<ApiResponse>(new ApiResponse(
                         post,
                "Post is added sussefulty",
                "1"),
                HttpStatus.CREATED);
    }


    // update post 


    @PutMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> updatePost(@RequestBody PostDto postDto,@PathVariable Long postId){

       PostDto postDto2 = this.postService.updatePost(postDto, postId);


       return new ResponseEntity<ApiResponse>(new ApiResponse(postDto2,"success","1"), HttpStatus.OK);
    }


    // delete post 

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId){

        this.postService.deletePost(postId);

        return new ResponseEntity<ApiResponse>(new ApiResponse(null,"Delete Success","1"), HttpStatus.OK);
    }



    // Get all Post 


    @GetMapping("/posts")
    public ResponseEntity<ApiResponse> getAllPosts(
        @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
        @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
        @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
        @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
    ){

       PostResponse postDtos =   this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);


       return new ResponseEntity<ApiResponse>( new ApiResponse(postDtos,"sucess","1"),HttpStatus.OK);
    }


    @GetMapping("post/{postId}")
    public ResponseEntity<ApiResponse> getSinglePost(@PathVariable Long postId){

        PostDto postDto = this.postService.getPostById(postId);

        return new ResponseEntity<ApiResponse>(new ApiResponse(postDto,"success","1"),HttpStatus.OK);
    }

    // Get Post by User 

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<ApiResponse> getPostsByUser(@PathVariable Long userId){


       List<PostDto> posts =  this.postService.getPostsByUser(userId);

        return new ResponseEntity<ApiResponse>(new ApiResponse(posts,"All Post list of user","1"),HttpStatus.OK);
    }


    // Get Post by Category

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<ApiResponse> getPostsByCategory(@PathVariable Long categoryId){

        List<PostDto> posts =  this.postService.getPostsByCategory(categoryId);

        return new ResponseEntity<ApiResponse>(new ApiResponse(posts,"All Post list of category","1"),HttpStatus.OK);


    }


    // Search by Title Contain
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<ApiResponse> searchPostByTitle(@PathVariable String keyword){

        List<PostDto> postDtos = this.postService.searchPosts(keyword);

        return new ResponseEntity<ApiResponse>(new ApiResponse(postDtos,"success","1"), HttpStatus.OK);

    }



    //post Image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<ApiResponse> uploadPostImage(
        @RequestParam("image") MultipartFile image,
        @PathVariable Long postId
    ) throws IOException{

        PostDto postDto =this.postService.getPostById(postId);
            String fileName =  this.fileService.uploadImage(path, image);
       
      

      

       postDto.setImageName(fileName);

       System.out.println(fileName);
       System.out.println(postDto.getImageName());


       PostDto updatePost =   this.postService.updatePost(postDto, postId);

       System.out.println(updatePost.getImageName());


       return new ResponseEntity<ApiResponse>(new ApiResponse(updatePost,"success","1"),HttpStatus.OK);

    }


    //method to serve files
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response
    ) throws IOException{

        InputStream resource = this.fileService.getResource(path, imageName);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
    // @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE )
    // public void downloadImage(
    //     @PathVariable("imageName") String imageName,
    //     HttpServletResponse response
    // ) throws IOException{

    //     InputStream resource = this.fileService.getResource(path, imageName);
    //     response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    //     StreamUtils.copy((InputStream) response, response.getOutputStream());
    //     // StreamUtils.copy(resource, ((ServletResponse) resource).getOutputStream());
    // }
    
}
