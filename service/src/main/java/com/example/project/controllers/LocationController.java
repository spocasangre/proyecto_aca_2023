package com.example.project.controllers;

import com.example.project.models.dtos.CreateLocationDTO;
import com.example.project.models.dtos.MessageResponse;
import com.example.project.models.dtos.UpdateLocationDTO;
import com.example.project.models.dtos.UpdateLocationUserDTO;
import com.example.project.models.entities.Contact;
import com.example.project.models.entities.Location;
import com.example.project.models.entities.Usuario;
import com.example.project.repositories.UserRepository;
import com.example.project.services.ContactService;
import com.example.project.services.LocationService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    LocationService locationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private ContactService contactService;

    @Qualifier("getFreeMarkerConfiguration")
    @Autowired
    private Configuration freemakerConfig;

    @PostMapping("")
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    public ResponseEntity<?> createLocation(@RequestBody CreateLocationDTO createLocationDTO) {
        try {
            Location location = new Location();
            location.setLatitud(createLocationDTO.getLatitude());
            location.setLongitud(createLocationDTO.getLongitude());
            Usuario usuario = userRepository.findById(createLocationDTO.getId_user()).get();
            location.setUser(usuario);
            locationService.saveLocation(location);

            List<Contact> contactList = contactService.getUserContactList(createLocationDTO.getId_user());

            for(int i = 0; i < contactList.size(); i++) {
                MimeMessage message2 = emailSender.createMimeMessage();
                MimeMessageHelper helperMessage2 = new MimeMessageHelper(message2, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

                Template t2 = freemakerConfig.getTemplate("mail_recive.html");
                Map<String, Object> model2 = new HashMap<>();
                model2.put("from", location.getUser().getNombre());
                if(location.getDescripcion() != null) {
                    model2.put("desc", location.getDescripcion());
                } else {
                    model2.put("desc", "El usuario no proporciono descripcion");
                }
                model2.put("longitud", location.getLongitud().toString());
                model2.put("latitud", location.getLatitud().toString());
                String html2 = FreeMarkerTemplateUtils.processTemplateIntoString(t2, model2);

                helperMessage2.setFrom("josenasser2009@gmail.com");
                helperMessage2.setTo(contactList.get(i).getEmail());

                helperMessage2.setSubject("Alerta enviada!");
                helperMessage2.setText(html2, true);
                emailSender.send(message2);
            }

            return ResponseEntity.ok(new MessageResponse(true, 1, location, "Ubicacion creada con exito"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, e.getMessage()));
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> getAllLocations() {
        try {
            List<Location> locationList = locationService.findAll();
            return ResponseEntity.ok(new MessageResponse(true, 1, locationList, "Lista obtenida exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "No se pudo obtener la lista de ubicaciones"));
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateLocation(@Valid @RequestBody List<UpdateLocationDTO> updateLocationDTO) {
        try {
            for (int i = 0; i <= updateLocationDTO.size() - 1; i++) {
                Location location = locationService.findById(updateLocationDTO.get(i).getIdLocation());
                location.setMaps_id(updateLocationDTO.get(i).getMapId());
                locationService.saveLocation(location);
            }
            return ResponseEntity.ok(new MessageResponse(true, 1, null, "Ubicacion actualizada!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, e.getMessage()));
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('usuario')")
    public ResponseEntity<?> updateLocationUser(@Valid @RequestBody UpdateLocationUserDTO updateLocationUserDTO) {
        try {
            Location location = locationService.findById(updateLocationUserDTO.getIdLocation());
            location.setTitulo(updateLocationUserDTO.getTitle());
            location.setDescripcion(updateLocationUserDTO.getDescription());
            locationService.saveLocation(location);
            return ResponseEntity.ok(new MessageResponse(true, 1, null, "Ubicacion actualizada!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, e.getMessage()));
        }
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> locationDetail(@PathVariable("id") String id) {
        try {
            Location location = locationService.getLocationDetail(id);
            return ResponseEntity.ok(new MessageResponse(true, 1, location, "Ubicacion recuperada exitosamente!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "Error obteniendo la ubicacion"));
        }
    }

}
