package com.example.project.services;

import com.example.project.models.dtos.CreateGeozoneDTO;
import com.example.project.models.entities.GeoZone;

import java.util.List;

public interface GeozoneService {
    List<GeoZone> getAll();

    GeoZone createGeozone(CreateGeozoneDTO createGeozoneDTO);


}
