package com.example.project.controllers;

import com.example.project.models.dtos.CoordinateDTO;
import com.example.project.models.dtos.CreateGeozoneDTO;
import com.example.project.models.dtos.MessageResponse;
import com.example.project.models.entities.GeoZone;
import com.example.project.repositories.GeozoneRepository;
import com.example.project.services.GeozoneService;
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
    private GeozoneService geozoneService;

    @GetMapping("")
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    public ResponseEntity<?> getAllGeoZones() {
        try {
            List<GeoZone> lista = geozoneService.getAll();
            if(lista != null){
                return ResponseEntity.ok(new MessageResponse(true, 1, lista, "Lista de geozonas obtenida existosamente"));
            }else{
                return ResponseEntity.ok(new MessageResponse(false, 1, lista, "No hay geozonas en la db"));
            }

        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "Hubo algun problema al obtener la lista de geozonas"));
        }
    }

    @GetMapping("/checkpoint")
    @PreAuthorize("hasRole('usuario')")
    public ResponseEntity<?> checkPointInGeozone(@RequestBody CoordinateDTO coordinateDTO) {
        try {
            boolean resp = false;
            List<GeoZone> lista = geozoneService.getAll();
            if(lista == null){
                return ResponseEntity.ok(new MessageResponse(false, 7, resp, "No se encontro ninguna geozona en db"));
            }

            Double latConverted = Double.valueOf(coordinateDTO.getLatitude());
            Double lonConverted = Double.valueOf(coordinateDTO.getLongitude());
            for(GeoZone geozona : lista){
                resp = resp || pointInPolygon.checkPointInGeozonePolygon(geozona.getIdGeozone(), latConverted, lonConverted);
            }
            if(resp){
                return ResponseEntity.ok(new MessageResponse(true, 1, resp, "Point inside geozone"));
            }else{
                return ResponseEntity.ok(new MessageResponse(false, 7, resp, "Point not inside of any geozone"));
            }

        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "Hubo algun problema al verificar la ubicación sobre la geozona"));
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createGeozone(@RequestBody CreateGeozoneDTO createGeozoneDTO) {
        try {
            GeoZone nuevaGeo = geozoneService.createGeozone(createGeozoneDTO);
            return ResponseEntity.ok(new MessageResponse(true, 1, nuevaGeo, "Geozona guardada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "Hubo algun problema al guardar la nueva geozona"));
        }
    }

}
