package com.example.project.services.impls;

import com.example.project.models.entities.AdvisorType;
import com.example.project.models.entities.Advisory;
import com.example.project.repositories.AdvisoryRepository;
import com.example.project.services.AdvisoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvisoryServiceImpl implements AdvisoryService {

    @Autowired
    AdvisoryRepository advisoryRepository;

    @Override
    public List<Advisory> getAdvisoriesByAdvisorType(AdvisorType advisorType) {
        List<Advisory> advisoryList = advisoryRepository.getAdvisoriesByAdvisorType(advisorType).get();
        return advisoryList;
    }

    @Override
    public Void save(Advisory advisory) {
        advisoryRepository.save(advisory);
        return null;
    }

    @Override
    public Advisory findById(Long id) {
        return advisoryRepository.findById(id).get();
    }

    @Override
    public Void delete(Advisory advisory) {
        advisoryRepository.delete(advisory);
        return null;
    }
}
