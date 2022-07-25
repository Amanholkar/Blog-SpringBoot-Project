package com.codewithaman.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithaman.blog.entities.Category;
import com.codewithaman.blog.exceptions.ResourceNotFoundException;
import com.codewithaman.blog.payloads.CategoryDto;
import com.codewithaman.blog.repositories.CategoryRepo;
import com.codewithaman.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        
      Category category=  this.modelMapper.map(categoryDto, Category.class);

      Category addCategory =  this.categoryRepo.save(category);
        return this.modelMapper.map(addCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        
        Category category  = this.categoryRepo.findById(categoryId)
        .orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));


        category.setTitle(categoryDto.getTitle());
        category.setDescripton(categoryDto.getDescripton());

        Category updateCat = this.categoryRepo.save(category);

        return this.modelMapper.map(updateCat, CategoryDto.class);
    }

    @Override
    public Void deleteCategory(Long categoryId) {
        

        Category category = this.categoryRepo.findById(categoryId)
        .orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));

          this.categoryRepo.delete(category);
      
          return null;
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        
        Category category = this.categoryRepo.findById(categoryId)
        .orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
        
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategorys() {
       List<Category> categories = this.categoryRepo.findAll();


       List<CategoryDto> categoryDtos =  categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }
    
}
