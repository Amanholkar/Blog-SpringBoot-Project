package com.codewithaman.blog.services;

import java.util.List;

import com.codewithaman.blog.payloads.CategoryDto;

public interface CategoryService {

    //Create

     public CategoryDto createCategory(CategoryDto categoryDto);
    //Update

     public CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);

    //Delete

    public Void deleteCategory(Long categoryId);

    //Get

    public CategoryDto getCategory(Long categoryId);

    //Get All 

    public List<CategoryDto> getCategorys();
    
}
