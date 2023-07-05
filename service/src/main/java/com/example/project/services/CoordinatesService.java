package com.example.project.services;

import com.example.project.models.dtos.CoordinateDTO;
import com.example.project.models.entities.Coordinate;

import java.util.List;

public interface CoordinatesService {
    List<Coordinate> createCoordinatesInGeozone(Long id, List<CoordinateDTO> listOfCoordinatesDTO) throws RuntimeException;
}
