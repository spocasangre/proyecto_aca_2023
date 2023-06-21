package com.example.project.controllers;

import com.example.project.models.dtos.MessageResponse;
import com.example.project.models.entities.Usuario;
import com.example.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<Usuario> users = userRepository.findAllUsers();
            return ResponseEntity.ok(new MessageResponse(false, 1, users, "Lista obtenida correctamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "No se pudo obtener la lista de usuarios"));
        }
    }

    @DeleteMapping("/delete_user/{id}")
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        try {
            Usuario user = userRepository.findById(id).get();
            userRepository.delete(user);
            return ResponseEntity.ok().body(new MessageResponse(true, 1, null, "Usuario eliminado!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(false, 7, null, "Error al eliminar el usuario"));
        }
    }

}
