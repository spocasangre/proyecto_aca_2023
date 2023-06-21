package com.example.project.repositories;

import com.example.project.models.entities.AdvisorType;
import com.example.project.models.entities.Advisory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvisoryRepository extends JpaRepository<Advisory, Long> {

    Optional<List<Advisory>> getAdvisoriesByAdvisorType(AdvisorType advisorType);

}
