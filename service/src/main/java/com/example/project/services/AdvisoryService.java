package com.example.project.services;

import com.example.project.models.entities.AdvisorType;
import com.example.project.models.entities.Advisory;

import java.util.List;

public interface AdvisoryService {
    List<Advisory> getAdvisoriesByAdvisorType(AdvisorType advisorType);
    Void save(Advisory advisory);
    Advisory findById(Long id);
    Void delete(Advisory advisory);
}
