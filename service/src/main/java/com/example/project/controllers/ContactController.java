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
package com.example.project.controllers;

import com.example.project.models.dtos.CreateContactDTO;
import com.example.project.models.dtos.CreateLocationDTO;
import com.example.project.models.dtos.MessageResponse;
import com.example.project.models.entities.Contact;
import com.example.project.models.entities.Usuario;
import com.example.project.repositories.UserRepository;
import com.example.project.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("")
    @PreAuthorize("hasRole('usuario')")
    public ResponseEntity<?> createContact(@RequestBody CreateContactDTO createContactDTO) {
        try {
            Contact contact = new Contact();
            contact.setNombre(createContactDTO.getName());
            contact.setTelefono(createContactDTO.getNumber());
            Usuario usuario = userRepository.findById(createContactDTO.getId_user()).get();
            contact.setUser(usuario);
            contact.setEmail(createContactDTO.getEmail());
            contactService.save(contact);
            return ResponseEntity.ok(new MessageResponse(true, 1, contact, "Contacto creado exitosamente!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('usuario')")
    public ResponseEntity<?> getAllUserContacts(@PathVariable("id") Long id) {
        try {
            List<Contact> contactList = contactService.getUserContactList(id);
            return ResponseEntity.ok(new MessageResponse(true, 1, contactList, "Lista obtenida exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "No se pudo obtener la lista de contactos"));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('usuario')")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id) {
        try {
            Contact contact = contactService.findById(id);
            contactService.delete(contact);
            return ResponseEntity.ok(new MessageResponse(true, 1, null, "Contacto eliminado!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "No se pudo eliminar el contacto"));
        }
    }

}
