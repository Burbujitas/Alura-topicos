package com.alura.foro.controller;


import com.alura.foro.domain.usuarios.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder){
        System.out.println(datosRegistroUsuario);
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario.getUsusarioId(),usuario.getNombre(),usuario.getEmail());

        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getUsusarioId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaUsuario);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario){
        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());
        usuario.actualizarUsuarios(datosActualizarUsuario);
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario.getUsusarioId(),usuario.getNombre(),usuario.getEmail()));
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaUsuario>> mostrarUsuario(@PageableDefault(size = 2)Pageable paginacion){
        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(DatosRespuestaUsuario::new));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaUsuario> mostrarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        var datosUsuario =new DatosRespuestaUsuario(usuario.getUsusarioId(),usuario.getNombre(),usuario.getEmail());
        return ResponseEntity.ok(datosUsuario);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        System.out.println(usuario.getNombre());
        usuarioRepository.delete(usuario);
        return ResponseEntity.noContent().build();
    }
}
