/*
 * Copyright 2023 WeGotYou!
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.project.controllers;

import com.example.project.models.dtos.CheckPointResponse;
import com.example.project.models.dtos.CoordinateDTO;
import com.example.project.models.dtos.CreateGeozoneDTO;
import com.example.project.models.dtos.MessageResponse;
import com.example.project.models.entities.GeoZone;
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

    private GeozoneService geozoneService;

    @GetMapping("")
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    public ResponseEntity<?> getAllGeoZones() {
        try {
            List<GeoZone> lista = geozoneService.getAllActive();
            if(lista != null){
                return ResponseEntity.ok(new MessageResponse(true, 1, lista, "Lista de geozonas obtenida existosamente"));
            }else{
                return ResponseEntity.ok(new MessageResponse(false, 1, lista, "No hay geozonas en la db"));
            }

        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "Hubo algun problema al obtener la lista de geozonas"));
        }
    }

    @PostMapping("/checkpoint")
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    public ResponseEntity<?> checkPointInGeozone(@RequestBody CoordinateDTO coordinateDTO) {
        try {
            CheckPointResponse response = new CheckPointResponse();
            List<GeoZone> lista = geozoneService.getAllActive();
            if(lista == null){
                return ResponseEntity.ok(new MessageResponse(true, 1, response, "No se encontro ninguna geozona activa en db"));
            }
            Double latConverted = Double.valueOf(coordinateDTO.getLatitude());
            Double lonConverted = Double.valueOf(coordinateDTO.getLongitude());
            for(GeoZone geozona : lista){
                response = pointInPolygon.checkPointInGeozonePolygon(geozona.getIdGeozone(), latConverted, lonConverted);
                if(response.getResponse()){
                    String name = geozoneService.getOneById(response.getIdGeozone()).getNombre();
                    return ResponseEntity.ok(new MessageResponse(true, 1, response, "Actualmente se encuentra en una zona de riesgo\n" +
                            "Nombre de la geozona: " + name));
                }
            }
            response.setResponse(false);
            response.setIdGeozone(-1L);
            return ResponseEntity.ok(new MessageResponse(true, 7, response, "No se encuentra en ninguna zona de riesgo"));
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

    @GetMapping("/activate/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> activateGeozone(@PathVariable(value = "id") Long idGeo) {
        try {
            GeoZone nuevaGeo = geozoneService.activateGeozone(idGeo);
            return ResponseEntity.ok(new MessageResponse(true, 1, nuevaGeo, "Geozona activada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "Hubo algun problema al activar la geozona"));
        }
    }

    @GetMapping("/deactivate/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deactivateGeozone(@PathVariable(value = "id") Long idGeo) {
        try {
            GeoZone nuevaGeo = geozoneService.deactivateGeozone(idGeo);
            return ResponseEntity.ok(new MessageResponse(true, 1, nuevaGeo, "Geozona desactivada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "Hubo algun problema al desactivar la geozona"));
        }
    }
}
