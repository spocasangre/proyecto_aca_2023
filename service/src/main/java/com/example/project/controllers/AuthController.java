package com.example.project.controllers;

import com.example.project.models.dtos.*;
import com.example.project.models.entities.Rol;
import com.example.project.models.entities.Usuario;
import com.example.project.repositories.RolRepository;
import com.example.project.repositories.UserRepository;
import com.example.project.services.RolService;
import com.example.project.utils.JwtUtils;
import com.example.project.utils.UserDetailsImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    RolService rolService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JavaMailSender emailSender;

    @Qualifier("getFreeMarkerConfiguration")
    @Autowired
    private Configuration freemakerConfig;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody CreateAccountDTO createAccountDTO){
        try {
            if(userRepository.existsByCorreo(createAccountDTO.getCorreo())) {
                return ResponseEntity.ok(new MessageResponse(true, 2, null, "El correo se encuentra en uso"));
            } else {
                Usuario usuario = new Usuario();
                usuario.setNombre(createAccountDTO.getNombre());
                usuario.setTelefono(createAccountDTO.getTelefono());
                usuario.setCorreo(createAccountDTO.getCorreo());

                String passHash = encoder.encode(createAccountDTO.getPassword());
                usuario.setPassword(passHash);
                Rol rol = rolService.findById(createAccountDTO.getId_rol());
                usuario.setRol(rol);
                userRepository.save(usuario);
                return ResponseEntity.ok(new MessageResponse(true, 1, usuario, "Usuario creado con exito"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(false, 7, null, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail().trim(), userLoginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Boolean exist = userRepository.existsByCorreo(userLoginDTO.getEmail());
        if (exist) {

            Usuario encontrado = userRepository.findByCorreo(userLoginDTO.getEmail().trim()).get();
            String jwt = "";

            jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            List<Object> lista = new ArrayList<>();
            lista.add(new JwtResponse(jwt,
                    userDetails.getUsername(),
                    userDetails.getNombre(),
                    userDetails.getId(),
                    userDetails.getEmail(),
                    userDetails.getRol(),
                    roles));
            encontrado.setFtoken(userLoginDTO.getfToken());
            userRepository.save(encontrado);
            return ResponseEntity.ok(new MessageResponse(true, 1, lista, "Usuario loggeado existosamente."));
        } else {
            return ResponseEntity.ok().body(new MessageResponse(false, 5, null, "El correo no se encuentra registrado"));
        }

    }

    @PostMapping("/send_code")
    public ResponseEntity<?> changePassword(@RequestBody SendCodeDTO sendCodeDTO){
        try {
            Usuario user = userRepository.findByCorreo(sendCodeDTO.getEmail()).get();
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            String codigo = "";
            for (int i = 0; i < 5; i++) {
                codigo = codigo + number.nextInt(9);
            }
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helperMessage = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Template t = freemakerConfig.getTemplate("email.html");
            Map<String, Object> model = new HashMap<>();
            model.put("codigo", codigo);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helperMessage.setFrom("josenasser2009@gmail.com");
            helperMessage.setTo(user.getCorreo());

            helperMessage.setSubject("Codigo de verificacion");
            helperMessage.setText(html, true);
            user.setCodigo(codigo);
            userRepository.save(user);
            emailSender.send(message);
            return ResponseEntity.ok(new MessageResponse(true, 1, null, "Codigo enviado"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7 , null, e.getMessage()));
        }
    }

    @PostMapping("/verify_code")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeDTO verifyCodeDTO) {
        try {
            Boolean exist = userRepository.existsByCorreo(verifyCodeDTO.getEmail());
            if(exist) {
                Usuario usuario = userRepository.findByCorreo(verifyCodeDTO.getEmail()).get();
                if(usuario.getCodigo().equals(verifyCodeDTO.getCode())) {
                    return ResponseEntity.ok(new MessageResponse(true, 1, null, "Accion relizada exitosamente!"));
                } else {
                    return ResponseEntity.ok(new MessageResponse(true, 3, null, "El dogio proporcionado no coincide"));
                }
            } else {
                return ResponseEntity.ok(new MessageResponse(true, 2, null, "El correo no se encuentra registrado"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "No se pudo verificar el codigo"));
        }
    }

    @PostMapping("/change_password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            Usuario user = userRepository.findByCodigo(changePasswordDTO.getCodigo()).get();
            if(Objects.equals(user.getCodigo(), changePasswordDTO.getCodigo())) {
                String passHash = encoder.encode(changePasswordDTO.getPassword());
                user.setPassword(passHash);
                userRepository.save(user);
                return ResponseEntity.ok(new MessageResponse(true, 1, null, "Contrasena actualizada!"));
            } else {
                return ResponseEntity.ok(new MessageResponse(false, 5, null, "El codigo proporcionado no coincide"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "No se pudo cambiar la contrasna"));
        }
    }

}
