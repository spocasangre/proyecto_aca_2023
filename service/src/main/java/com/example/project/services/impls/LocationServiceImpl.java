package com.example.project.services.impls;

import com.example.project.models.entities.Location;
import com.example.project.repositories.LocationRepository;
import com.example.project.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public Location getLocationDetail(String id) {
        return locationRepository.getLocationDetail(id);
    }

    @Override
    public Void saveLocation(Location location) {
        locationRepository.save(location);
        return null;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location findById(Long id) {
        Location location = locationRepository.findById(id).get();
        return location;
    }
}
