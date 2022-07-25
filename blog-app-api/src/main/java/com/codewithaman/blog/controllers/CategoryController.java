package com.codewithaman.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithaman.blog.payloads.ApiResponse;
import com.codewithaman.blog.payloads.CategoryDto;
import com.codewithaman.blog.services.CategoryService;



@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    //Create

    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){


       CategoryDto createCategory= this.categoryService.createCategory(categoryDto);

        return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
    }


    //Update

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Long categoryId){
        CategoryDto createCategory= this.categoryService.updateCategory(categoryDto,categoryId);

        return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.OK);

        
    }

    //Delete


    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){

        this.categoryService.deleteCategory(categoryId);

        return new ResponseEntity<ApiResponse>(new ApiResponse(null,"Category is deleted successfully","1"),HttpStatus.OK);
    }

    //Get

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getSingleCategory(@PathVariable Long categoryId){

        CategoryDto  categoryDto = this.categoryService.getCategory(categoryId);

        return new ResponseEntity<ApiResponse>(new ApiResponse(categoryDto,"sucess","1"),HttpStatus.OK);
    }



    //Get All


    @GetMapping("/category")
    public ResponseEntity<ApiResponse> getCategorys(){

        List<CategoryDto> categoryDto = this.categoryService.getCategorys();

        return new ResponseEntity<ApiResponse>(new ApiResponse(categoryDto,"sucess","1"),HttpStatus.OK);
    }

    
}
