package com.codewithaman.blog.payloads;

import lombok.Data;

@Data
public class PostResponse {


    private Object contant;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean lastPage;

    
}
