package com.example.project.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "coordenada")
public class Coordinate {

    @Id
    @Column(name = "id_coordenada")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_coordenada;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_geozona")
    private GeoZone geozona;

    public Coordinate(){
        super();
    }

    public GeoZone getGeozona() {
        return geozona;
    }

    public void setGeozona(GeoZone geozona) {
        this.geozona = geozona;
    }

    public Long getId_coordenada() {
        return id_coordenada;
    }

    public void setId_coordenada(Long id_coordenada) {
        this.id_coordenada = id_coordenada;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
