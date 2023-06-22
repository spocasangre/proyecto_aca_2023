package com.example.project.repositories;

import com.example.project.models.entities.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoordenadasRepository extends JpaRepository<Coordinate, Long> {

    @Query("SELECT c from coordenada c WHERE c.geozona.idGeozone = ?1")
    List<Coordinate> getGeozoneCoordinates(Long id);
}
