package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class CreateLocationDTO {

    @NotNull
    private Long id_user;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    public CreateLocationDTO(Long id_user, Double latitude, Double longitude) {
        super();
        this.id_user = id_user;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
