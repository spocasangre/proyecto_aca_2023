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

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "geozone")
public class GeoZone {

    @Id
    @Column(name = "id_geozona")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGeozone;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "is_active")
    private Boolean isActive;

    @JsonManagedReference
    @OneToMany(mappedBy = "geozona", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coordinate> coordenadas;

    public GeoZone() {
        super();
        this.createdAt = new Date();
    }

    public GeoZone(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.isActive = true;
        this.createdAt = new Date();
    }

    public List<Coordinate> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<Coordinate> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Long getIdGeozone() {
        return idGeozone;
    }

    public void setIdGeozone(Long idGeozone) {
        this.idGeozone = idGeozone;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
