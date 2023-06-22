package com.example.project.controllers;

import com.example.project.models.dtos.CheckGeoZoneDTO;
import com.example.project.models.dtos.MessageResponse;
import com.example.project.models.entities.Contact;
import com.example.project.models.entities.GeoZone;
import com.example.project.repositories.GeozoneRepository;
import com.example.project.services.geofence.PointInPolygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geozone")
public class GeozoneController {

    @Autowired
    private PointInPolygon pointInPolygon;

    @Autowired
    private GeozoneRepository geozoneRepository;

    @GetMapping("/checkpoint")
    @PreAuthorize("hasRole('usuario')")
    public ResponseEntity<?> getAllUserContacts(@RequestBody CheckGeoZoneDTO checkGeoZoneDTO) {
        try {
            boolean resp = false;
            List<GeoZone> lista = geozoneRepository.findAll();
            Double latConverted = Double.valueOf(checkGeoZoneDTO.getLatitude());
            Double lonConverted = Double.valueOf(checkGeoZoneDTO.getLongitude());
            for(GeoZone geozona : lista){
                resp = resp || pointInPolygon.checkPointInGeozonePolygon(geozona.getIdGeozone(), latConverted, lonConverted);
            }
            if(resp){
                return ResponseEntity.ok(new MessageResponse(true, 1, resp, "Point inside geozone"));
            }else{
                return ResponseEntity.ok(new MessageResponse(true, 1, resp, "Point not inside of any geozone"));
            }

        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "Hubo algun problema al verificar la ubicación sobre la geozona"));
        }
    }
}
