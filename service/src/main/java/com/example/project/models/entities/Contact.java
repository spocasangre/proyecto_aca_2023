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

@Entity(name = "contacto")
public class Contact {

    @Id
    @Column(name = "id_contacto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_contacto;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario user;

    public Contact(Long id_contacto, String nombre, Integer telefono, String email, Usuario user) {
        super();
        this.id_contacto = id_contacto;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.user = user;
    }

    public Contact() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId_contacto() {
        return id_contacto;
    }

    public void setId_contacto(Long id_contacto) {
        this.id_contacto = id_contacto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
