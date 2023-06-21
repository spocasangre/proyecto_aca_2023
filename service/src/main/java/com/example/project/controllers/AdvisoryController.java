package com.example.project.controllers;

import com.example.project.models.dtos.CreateAdvisoryDTO;
import com.example.project.models.dtos.DeleteAdvisorDTO;
import com.example.project.models.dtos.MessageResponse;
import com.example.project.models.entities.AdvisorType;
import com.example.project.models.entities.Advisory;
import com.example.project.services.AdvisoryService;
import com.example.project.services.AdvisoryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advisory")
public class AdvisoryController {

    @Autowired
    AdvisoryService advisoryService;

    @Autowired
    AdvisoryTypeService advisoryTypeService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    public ResponseEntity<?> getByCategory(@PathVariable("id") Long id) {
        try {
            AdvisorType advisorType = advisoryTypeService.findById(id);
            List<Advisory> advisoryList = advisoryService.getAdvisoriesByAdvisorType(advisorType);
            if (advisoryList.isEmpty()) {
                return ResponseEntity.ok(new MessageResponse(true, 1, advisoryList, "Lista vacia"));
            } else {
                return ResponseEntity.ok(new MessageResponse(true, 1, advisoryList, "Lista obtenida correctamente"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "No se pudo obtener la lista de asesores"));
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createAdvisory(@RequestBody CreateAdvisoryDTO createAdvisoryDTO) {
        try {
            Advisory advisory = new Advisory();
            AdvisorType advisorType = advisoryTypeService.findById(createAdvisoryDTO.getType());
            advisory.setAdvisorType(advisorType);
            advisory.setNombre(createAdvisoryDTO.getFullname());
            advisory.setTelefono(createAdvisoryDTO.getNumber());
            advisoryService.save(advisory);
            return ResponseEntity.ok(new MessageResponse(true, 1, advisory, "Asesor creado!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, e.getMessage()));
        }
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    public ResponseEntity<?> getAdvisoryDetail(@PathVariable("id") Long id) {
        try {
            Advisory advisory = advisoryService.findById(id);
            return ResponseEntity.ok(new MessageResponse(true, 1, advisory, "Asesor obtenido!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "Nose pudo obtener el detalle del asesor"));
        }
    }

    @DeleteMapping("/delete_advisor/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteAdvisory(@PathVariable("id") Long id) {
        try {
            Advisory advisory = advisoryService.findById(id);
            advisoryService.delete(advisory);
            return ResponseEntity.ok(new MessageResponse(true, 1, null, "Asesor eliminado"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "No se pudo eliminar el asesor"));
        }
    }

}
