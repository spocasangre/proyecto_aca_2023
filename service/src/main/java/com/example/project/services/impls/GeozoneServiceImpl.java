package com.example.project.services.impls;

import com.example.project.models.dtos.CreateGeozoneDTO;
import com.example.project.models.entities.GeoZone;
import com.example.project.repositories.GeozoneRepository;
import com.example.project.services.GeozoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeozoneServiceImpl implements GeozoneService {

    @Autowired
    private GeozoneRepository geozoneRepository;
    @Override
    public List<GeoZone> getAll() {
        return geozoneRepository.findAll();
    }

    @Override
    public GeoZone createGeozone(CreateGeozoneDTO createGeozoneDTO) {
        GeoZone nuevaGeo = new GeoZone(createGeozoneDTO.getNombre(), createGeozoneDTO.getDesc());
        return geozoneRepository.save(nuevaGeo);
    }
}
