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
package com.example.project.services.geofence;

import com.example.project.models.dtos.CheckPointResponse;
import com.example.project.models.entities.Coordinate;
import com.example.project.repositories.CoordenadasRepository;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;


import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PointInPolygon {

    @Autowired
    private CoordenadasRepository coordenadasRepository;

    public CheckPointResponse checkPointInGeozonePolygon(Long idGeozone, Double lat, Double lon){

        List<Coordinate> coordenadas = coordenadasRepository.getGeozoneCoordinates(idGeozone);

        List<org.locationtech.jts.geom.Coordinate> listOfCoordinates = new LinkedList<>();

        for(int i = 0; i < coordenadas.size(); i++){
            listOfCoordinates.add( new org.locationtech.jts.geom.Coordinate(coordenadas.get(i).getLatitud() , coordenadas.get(i).getLongitud() ));
        }

        org.locationtech.jts.geom.Coordinate[] coordinates =
                listOfCoordinates.toArray(new org.locationtech.jts.geom.Coordinate[0]);
      
        GeometryFactory geometryFactory = new GeometryFactory();
        LinearRing linearRing = geometryFactory.createLinearRing(coordinates);

        Polygon polygon = geometryFactory.createPolygon(linearRing, null);

        org.locationtech.jts.geom.Coordinate point = new org.locationtech.jts.geom.Coordinate(lat, lon); // Coordinate inside polygon
        return new CheckPointResponse(polygon.contains(geometryFactory.createPoint(point)), idGeozone);
    }
}
