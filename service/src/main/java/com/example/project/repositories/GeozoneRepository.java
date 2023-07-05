package com.example.project.repositories;

import com.example.project.models.entities.GeoZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeozoneRepository extends JpaRepository<GeoZone, Long> {

    @Query("SELECT g FROM geozone g WHERE g.isActive = TRUE")
    List<GeoZone> findAllActive();
}
