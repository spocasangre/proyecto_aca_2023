package com.example.project.services.impls;

import com.example.project.models.entities.AdvisorType;
import com.example.project.repositories.AdvisorTypeRepository;
import com.example.project.services.AdvisoryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvisoryTypeImpl implements AdvisoryTypeService {

    @Autowired
    AdvisorTypeRepository advisorTypeRepository;

    @Override
    public AdvisorType findById(Long id) {
        AdvisorType advisorType = advisorTypeRepository.findById(id).get();
        return advisorType;
    }
}
