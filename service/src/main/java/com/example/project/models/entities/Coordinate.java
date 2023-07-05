/*
 * Copyright 2023 WeGotYou!
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    public Coordinate(Double latitud, Double longitud, GeoZone geozona) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.geozona = geozona;
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
