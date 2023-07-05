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
