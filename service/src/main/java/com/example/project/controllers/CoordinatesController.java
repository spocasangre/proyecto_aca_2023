package com.example.project.controllers;

import com.example.project.models.dtos.CoordinateDTO;
import com.example.project.models.dtos.CreateGeozoneDTO;
import com.example.project.models.dtos.ListOfCoordinatesDTO;
import com.example.project.models.dtos.MessageResponse;
import com.example.project.models.entities.Coordinate;
import com.example.project.models.entities.GeoZone;
import com.example.project.repositories.CoordenadasRepository;
import com.example.project.repositories.GeozoneRepository;
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

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createCoordinatesInGeozone(@PathVariable("id") Long id, @RequestBody List<CoordinateDTO> listOfCoordinatesDTO) {
        try {
            List<Coordinate> respuesta = coordinatesService.createCoordinatesInGeozone(id, listOfCoordinatesDTO);
            return ResponseEntity.ok(new MessageResponse(true, 1, respuesta, "Coordenadas de Geozona guardadas exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, e.getMessage()));
        }
    }
}
