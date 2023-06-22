package com.example.project.repositories;

import com.example.project.models.entities.GeoZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeozoneRepository extends JpaRepository<GeoZone, Long> {

}
