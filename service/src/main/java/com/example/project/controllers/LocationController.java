package com.example.project.controllers;

import com.example.project.models.dtos.*;
import com.example.project.models.entities.Contact;
import com.example.project.models.entities.Location;
import com.example.project.models.entities.Usuario;
import com.example.project.repositories.UserRepository;
import com.example.project.services.ContactService;
import com.example.project.services.LocationService;
import com.example.project.services.firebase.PushNotificationService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private PushNotificationService pushNotificationService;

    @Value("${app.firebase.google.maps.key}")
    private String googleMapsApiKEY;

    @PostMapping("")
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    public ResponseEntity<?> createLocation(@RequestBody CreateLocationDTO createLocationDTO) {
        try {
            if(createLocationDTO.getType() < 1 || createLocationDTO.getType() > 2){
                return ResponseEntity.ok(new MessageResponse(false, 7, null,
                        "Type is Integer and it must be 1 or 2"));
            }
            Location location = new Location();
            Double la=createLocationDTO.getLatitude();
            Double lo=createLocationDTO.getLongitude();
            location.setLatitud(la);
            location.setLongitud(lo);
            Usuario usuario = userRepository.findById(createLocationDTO.getId_user()).get();
            location.setUser(usuario);

            List<Contact> contactList = contactService.getUserContactList(createLocationDTO.getId_user());
            String nombreCreador = usuario.getNombre();
            char inicial = nombreCreador.toUpperCase().charAt(0);
            String imgURL = "https://maps.googleapis.com/maps/api/staticmap?center="+la+","+lo+"&zoom=16&size=600x200&scale=1&markers=color:red%" +
                    "7Clabel:"+inicial+"%7C"+la+","+lo+"&key="+googleMapsApiKEY;
            String message = nombreCreador +" necesita tu ayuda urgentemente!";
            if(createLocationDTO.getType() == 1){
                location.setTitulo(message);
                location.setDescripcion("Tu familiar/amigo se encuentra pasando un mal momento\n " +
                        "Contáctalo lo antes posible para verificar que todo este bien!");
            }
            location.setLog_url(imgURL);
            locationService.saveLocation(location);
            Long locId = location.getId();
            System.out.println("Id of this location: " +locId);
            for (Contact contact : contactList) {
                Integer numero = contact.getTelefono();
                if (numero == null) {
                    continue;
                }
                if (userRepository.findByTelefono(numero).isEmpty()) {
                    continue;
                }
                Usuario user = userRepository.findByTelefono(numero).get();
                String token = user.getFtoken();
                System.out.println("Token of " + user.getNombre() + " : " + token);
                if (token != null) {
                    Map<String, String> object = new HashMap<>();
                    object.put("id", locId.toString());
                    object.put("titulo", "Alerta recibida");
                    object.put("message", message);
                    object.put("imagenURL", imgURL);
                    pushNotificationService.sendPushNotificationToToken(new PushNotificationRequest(
                            "Alert", token), object);
                }
            }
            return ResponseEntity.ok(new MessageResponse(true, 1, location, "Ubicacion creada con exito"));
        } catch (Exception e) {
            //System.out.println(Arrays.toString(e.getStackTrace()));
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
