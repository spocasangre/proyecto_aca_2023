package com.example.project.models.dtos;

import java.util.List;

public class ListOfCoordinatesDTO {
    private List<CoordinateDTO> coordenadas;

    public ListOfCoordinatesDTO(List<CoordinateDTO> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public List<CoordinateDTO> getCoordenadas() {
        return coordenadas;
    }
}
