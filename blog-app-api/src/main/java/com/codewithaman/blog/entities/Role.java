package com.codewithaman.blog.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {


    @Id
    private Long roleId;

    private String name;


    
}
