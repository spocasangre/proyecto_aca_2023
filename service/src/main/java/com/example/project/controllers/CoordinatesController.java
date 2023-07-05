package com.example.project.controllers;

import com.example.project.models.dtos.CreateCoordinatesDTO;
import com.example.project.models.dtos.MessageResponse;
import com.example.project.models.entities.Coordinate;
import com.example.project.services.CoordinatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordinates")
public class CoordinatesController {

    @Autowired
    private CoordinatesService coordinatesService;

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createCoordinatesInGeozone(@RequestBody CreateCoordinatesDTO createCoordinatesDTO) {
        try {
            List<Coordinate> respuesta = coordinatesService.createCoordinatesInGeozone(createCoordinatesDTO.getIdGeo(),
                    createCoordinatesDTO.getCoordenadas());
            return ResponseEntity.ok(new MessageResponse(true, 1, respuesta, "Coordenadas de geozona con id "+ createCoordinatesDTO.getIdGeo() + " guardadas exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, e.getMessage()));
        }
    }
}
