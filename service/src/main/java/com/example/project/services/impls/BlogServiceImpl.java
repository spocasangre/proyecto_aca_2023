package com.example.project.services.impls;

import com.example.project.models.entities.Blog;
import com.example.project.repositories.BlogRepository;
import com.example.project.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Void save(Blog blog) {
        blogRepository.save(blog);
        return null;
    }

    @Override
    public Blog findById(Long id) {
        return blogRepository.findById(id).get();
    }
}
