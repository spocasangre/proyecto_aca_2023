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
package com.example.project.services.impls;

import com.example.project.models.dtos.CoordinateDTO;
import com.example.project.models.dtos.MessageResponse;
import com.example.project.models.entities.Coordinate;
import com.example.project.models.entities.GeoZone;
import com.example.project.repositories.CoordenadasRepository;
import com.example.project.repositories.GeozoneRepository;
import com.example.project.services.CoordinatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CoordinatesServiceImpl implements CoordinatesService {

    @Autowired
    private CoordenadasRepository coordenadasRepository;

    @Autowired
    private GeozoneRepository geozoneRepository;

    @Override
    @Transactional
    public List<Coordinate> createCoordinatesInGeozone(Long id, List<CoordinateDTO> listOfCoordinatesDTO) throws RuntimeException {
        if (geozoneRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Ninguna geozona encontrada con este id");
        }

        GeoZone geoEncontrada = geozoneRepository.findById(id).get();

        CoordinateDTO firtsCoordinate = listOfCoordinatesDTO.get(0);
        CoordinateDTO lastCoordinate = listOfCoordinatesDTO.get(listOfCoordinatesDTO.size()-1);

        if( (!Objects.equals(firtsCoordinate.getLatitude(), lastCoordinate.getLatitude())) &&
                (!Objects.equals(firtsCoordinate.getLongitude(), lastCoordinate.getLongitude()))){
            throw new RuntimeException("Primera y ultima coordenada deben ser las mismas");
        }

        List<Coordinate> listExist = coordenadasRepository.getGeozoneCoordinates(id);
        System.out.println("Id Geozona: " + id + listExist.size());

        if(listExist != null && listExist.size() == 1){
            throw new RuntimeException("Geozona ya posee coordenadas (solo 1)");
        }

        if(listExist != null && listExist.size() > 1){
            throw new RuntimeException("Geozona ya posee coordenadas");
        }

        for(CoordinateDTO x : listOfCoordinatesDTO){
            Coordinate nuevaCoordenada = new Coordinate(
                    Double.valueOf(x.getLatitude()),
                    Double.valueOf(x.getLongitude()),
                    geoEncontrada
            );

            coordenadasRepository.save(nuevaCoordenada);
        }

        return coordenadasRepository.getGeozoneCoordinates(id);
    }
}
