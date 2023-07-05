package com.example.project.models.dtos;

import java.io.Serializable;
import java.util.List;


public class CreateCoordinatesDTO implements Serializable {
    private Long idGeo;
    private List<CoordinateDTO> coordinates;

    public CreateCoordinatesDTO(Long idGeo,List<CoordinateDTO> coordinates) {
        this.idGeo = idGeo;
        this.coordinates = coordinates;
    }

    public List<CoordinateDTO> getCoordenadas() {
        return coordinates;
    }

    public Long getIdGeo() {
        return idGeo;
    }
}
