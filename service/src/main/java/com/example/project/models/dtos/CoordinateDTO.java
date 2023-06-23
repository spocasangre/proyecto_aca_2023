package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class CoordinateDTO {
    @NotNull
    private String latitude;

    @NotNull
    private String longitude;

    public CoordinateDTO(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
