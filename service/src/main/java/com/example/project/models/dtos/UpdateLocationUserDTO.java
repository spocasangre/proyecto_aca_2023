package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class UpdateLocationUserDTO {

    @NotNull
    private Long idLocation;

    @NotNull
    private String title;

    @NotNull
    private String description;

    public UpdateLocationUserDTO(Long idLocation, String title, String description) {
        super();
        this.idLocation = idLocation;
        this.title = title;
        this.description = description;
    }

    public Long getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Long idLocation) {
        this.idLocation = idLocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
