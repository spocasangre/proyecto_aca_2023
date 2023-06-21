package com.example.project.models.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateLocationDTO {

    @NotNull
    private Long idLocation;

    @NotNull
    private String mapId;

    public UpdateLocationDTO(Long idLocation, String mapId) {
        super();
        this.idLocation = idLocation;
        this.mapId = mapId;
    }

    public Long getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Long idLocation) {
        this.idLocation = idLocation;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }
}
