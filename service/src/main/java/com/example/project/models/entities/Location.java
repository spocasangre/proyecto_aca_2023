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

import javax.persistence.*;

@Entity(name = "ubicacion")
public class Location {

    @Id
    @Column(name = "id_ubicacion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "maps_id")
    private String maps_id;

    @Column(name = "log_url")
    private String log_url;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario user;

    public Location(Long id, Double latitud, Double longitud, String maps_id, String titulo, String descripcion, Usuario user) {
        super();
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.maps_id = maps_id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.user = user;
    }

    public Location() {
        super();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMaps_id() {
        return maps_id;
    }

    public void setMaps_id(String maps_id) {
        this.maps_id = maps_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getLog_url() {
        return log_url;
    }

    public void setLog_url(String log_url) {
        this.log_url = log_url;
    }
}
