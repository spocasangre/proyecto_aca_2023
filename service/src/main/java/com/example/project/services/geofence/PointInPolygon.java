package com.example.project.services.geofence;

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
    public boolean checkPointInGeozonePolygon(Long idGeozone, Double lat, Double lon){
        List<Coordinate> coordenadas = coordenadasRepository.getGeozoneCoordinates(idGeozone);

        List<org.locationtech.jts.geom.Coordinate> listOfCoordinates = new LinkedList<>();

        for(int i = 0; i < coordenadas.size(); i++){
            listOfCoordinates.add( new org.locationtech.jts.geom.Coordinate(coordenadas.get(i).getLatitud() , coordenadas.get(i).getLongitud() ));
        }

        org.locationtech.jts.geom.Coordinate[] coordinates =
                listOfCoordinates.toArray(new org.locationtech.jts.geom.Coordinate[0]);
        /*for (org.locationtech.jts.geom.Coordinate x : coordinates)
            System.out.print(x.toString() + " ");*/
        GeometryFactory geometryFactory = new GeometryFactory();
        LinearRing linearRing = geometryFactory.createLinearRing(coordinates);

        Polygon polygon = geometryFactory.createPolygon(linearRing, null);

        org.locationtech.jts.geom.Coordinate point = new org.locationtech.jts.geom.Coordinate(lat, lon); // Coordinate inside polygon
        return polygon.contains(geometryFactory.createPoint(point));
    }
}
