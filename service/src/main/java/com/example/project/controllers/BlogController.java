package com.example.project.controllers;

import com.example.project.image.Base64Converter;
import com.example.project.image.FilesStorageService;
import com.example.project.models.dtos.CreateBlogDTO;
import com.example.project.models.dtos.MessageResponse;
import com.example.project.models.entities.Blog;
import com.example.project.models.entities.Imagenes;
import com.example.project.models.entities.Usuario;
import com.example.project.repositories.ImagenesRepository;
import com.example.project.repositories.UserRepository;
import com.example.project.services.BlogService;
import com.example.project.utils.GeneradorNombre;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FilesStorageService storageService;

    @Autowired
    ImagenesRepository imagenesRepository;

    @Value("${ruta-imagenes}")
    private String path;

    GeneradorNombre gn = new GeneradorNombre();

    @GetMapping("")
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    public ResponseEntity<?> getAllBlogs(){
        try {
            List<Blog> lista = blogService.findAll();
            return ResponseEntity.ok(new MessageResponse(true, 1, lista, "Lista recuperada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "No se pudo obtener la lista de blogs"));
        }
    }

    @RequestMapping(value = "/imagenes", produces = MediaType.IMAGE_PNG_VALUE)
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    @ResponseBody
    public byte[] blogImagenes(@RequestBody Map<String, String> fileName) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(path + fileName.get("imagen"))) {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    public ResponseEntity<?> createBlog(@RequestBody CreateBlogDTO createBlogDTO){
        try {
            Blog blog = new Blog();
            blog.setTitulo(createBlogDTO.getTitle());
            blog.setSubtitulo(createBlogDTO.getSubtitle());
            blog.setDescripcion(createBlogDTO.getDescription());
            Usuario user = userRepository.findById(createBlogDTO.getId_user()).get();
            blog.setUser(user);

            List<Imagenes> guardarImg = new ArrayList<>();
            MultipartFile file = Base64Converter.converter(createBlogDTO.getImage());
            String name = UUID.randomUUID().toString().replace("-", "");
            storageService.save(file, name);
            Imagenes imagen = new Imagenes();
            imagen.setBlog(blog);
            imagen.setSrc(name + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
            guardarImg.add(imagen);
            imagenesRepository.save(imagen);

            blog.setImagenes(guardarImg);
            blogService.save(blog);

            return ResponseEntity.ok(new MessageResponse(true, 1, blog, "Blog creado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, e.getMessage()));
        }
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('admin') or hasRole('usuario')")
    public ResponseEntity<?> getBlogDetail(@PathVariable("id") Long id){
        try {
            Blog blog = blogService.findById(id);
            return ResponseEntity.ok(new MessageResponse(true, 1, blog, "Blog obtenido exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(false, 7, null, "No se pudo obtener el detalle del blog"));
        }
    }

}
