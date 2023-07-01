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
        //System.out.println("Primera coordenada: " + firtsCoordinate.getLatitude() + firtsCoordinate.getLongitude());
        CoordinateDTO lastCoordinate = listOfCoordinatesDTO.get(listOfCoordinatesDTO.size()-1);
        //System.out.println("Ultima coordenada: " + lastCoordinate.getLatitude() + lastCoordinate.getLongitude());

        if( (!Objects.equals(firtsCoordinate.getLatitude(), lastCoordinate.getLatitude())) &&
                (!Objects.equals(firtsCoordinate.getLongitude(), lastCoordinate.getLongitude()))){
            throw new RuntimeException("Primera y ultima coordenada deben ser las mismas");
        }

        List<Coordinate> listExist = coordenadasRepository.getGeozoneCoordinates(id);

        if(listExist != null){
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
