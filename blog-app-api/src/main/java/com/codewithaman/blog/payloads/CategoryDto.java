package com.codewithaman.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {
    
    private Long categoryId;
    @NotBlank
    @Size(min = 3,max = 30 ,message = "Min size of Title is 10")
    private String title;

    @NotBlank
    @Size(min = 10,max = 300,message = "Min size of description is 10")
    private String descripton;
}
