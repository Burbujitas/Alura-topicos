package com.alura.foro.controller;


import com.alura.foro.domain.usuarios.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository,BCryptPasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder){



        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario,passwordEncoder.encode(datosRegistroUsuario.contraseña())));
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario.getUsusarioId(),usuario.getNombre(),usuario.getEmail());

        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getUsusarioId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaUsuario);
    }

    @PutMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario){
        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());
        usuario.actualizarUsuarios(datosActualizarUsuario,passwordEncoder.encode(datosActualizarUsuario.contraseña()));
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario.getUsusarioId(),usuario.getNombre(),usuario.getEmail()));
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<DatosRespuestaUsuario>> mostrarUsuario(@PageableDefault(size = 10)Pageable paginacion){
        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(DatosRespuestaUsuario::new));
    }
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<DatosRespuestaUsuario> mostrarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        var datosUsuario =new DatosRespuestaUsuario(usuario.getUsusarioId(),usuario.getNombre(),usuario.getEmail());
        return ResponseEntity.ok(datosUsuario);
    }
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity eliminarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        System.out.println(usuario.getNombre());
        usuarioRepository.delete(usuario);
        return ResponseEntity.noContent().build();
    }
}
