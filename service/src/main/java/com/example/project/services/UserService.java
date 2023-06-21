package com.example.project.services;

import com.example.project.models.entities.Usuario;

public interface UserService {
    Boolean existByCorreo(String correo);
    Void save(Usuario usuario);
    Usuario findByCorreo(String correo);
    Usuario findById(Long id);
}
