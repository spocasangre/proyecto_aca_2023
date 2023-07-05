package com.example.project.models.dtos;

public class CheckPointResponse {

    private Boolean response;

    private Long idGeozone;

    public CheckPointResponse(){
        super();
    }

    public CheckPointResponse(Boolean response, Long idGeozone) {
        this.response = response;
        this.idGeozone = idGeozone;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public Long getIdGeozone() {
        return idGeozone;
    }

    public void setIdGeozone(Long idGeozone) {
        this.idGeozone = idGeozone;
    }
}
