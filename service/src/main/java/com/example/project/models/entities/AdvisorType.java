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

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "tipo_asesor")
public class AdvisorType {

    @Id
    @Column(name = "id_tipo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipo_asesor;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "advisorType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Advisory> advisories;

    public AdvisorType(Long id_tipo_asesor, String nombre, String descripcion) {
        super();
        this.id_tipo_asesor = id_tipo_asesor;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public AdvisorType() {
        super();
    }

    public Long getId_tipo_asesor() {
        return id_tipo_asesor;
    }

    public void setId_tipo_asesor(Long id_tipo_asesor) {
        this.id_tipo_asesor = id_tipo_asesor;
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

    public List<Advisory> getAdvisories() {
        return advisories;
    }

    public void setAdvisories(List<Advisory> advisories) {
        this.advisories = advisories;
    }
}
