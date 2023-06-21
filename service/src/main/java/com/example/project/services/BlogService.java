package com.example.project.services;

import com.example.project.models.entities.Blog;

import java.util.List;

public interface BlogService {
    List<Blog> findAll();
    Void save(Blog blog);
    Blog findById(Long id);
}
