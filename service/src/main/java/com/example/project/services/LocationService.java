package com.example.project.services;

import com.example.project.models.entities.Location;

import java.util.List;

public interface LocationService {
    Location getLocationDetail(String id);
    Void saveLocation(Location location);
    List<Location> findAll();
    Location findById(Long id);
}
