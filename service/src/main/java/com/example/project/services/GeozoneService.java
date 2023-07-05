package com.example.project.services;

import com.example.project.models.dtos.CreateGeozoneDTO;
import com.example.project.models.entities.GeoZone;

import java.util.List;

public interface GeozoneService {
    List<GeoZone> getAll();

    List<GeoZone> getAllActive();

    GeoZone getOneById(Long id);

    GeoZone createGeozone(CreateGeozoneDTO createGeozoneDTO);

    GeoZone activateGeozone(Long id);

    GeoZone deactivateGeozone(Long id);
}
