package com.alura.foro.controller;

import com.alura.foro.domain.respuestas.*;
import com.alura.foro.domain.topicos.DatosActualizarTopico;
import com.alura.foro.domain.topicos.DatosRespuestaTopico;
import com.alura.foro.domain.topicos.Topico;
import com.alura.foro.domain.usuarios.DatosRespuestaUsuario;
import com.alura.foro.domain.usuarios.Usuario;
import com.alura.foro.domain.usuarios.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    private RespuestaRepository respuestaRepository;
    private UsuarioRepository usuarioRepository;
    @Autowired
    public RespuestaController(RespuestaRepository respuestaRepository, UsuarioRepository usuarioRepository){
        this.respuestaRepository=respuestaRepository;
        this.usuarioRepository= usuarioRepository;

    }

    @PostMapping
    public ResponseEntity<DatosRespuestaRespuesta> agregarRespuesta(@RequestBody @Valid DatosRegistrarRespuesta datosRegistrarRespuesta, UriComponentsBuilder uriComponentsBuilder){
        Usuario usuario = usuarioRepository.getReferenceById(datosRegistrarRespuesta.usuarioId());
        Respuesta respuesta = respuestaRepository.save(new Respuesta(datosRegistrarRespuesta,usuario));

        DatosRespuestaRespuesta datosRespuestaRespuesta = new DatosRespuestaRespuesta(respuesta);
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaRespuesta);
    }
    @PutMapping
    @Transactional
    public ResponseEntity actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta)
    {
        Respuesta respuesta = respuestaRepository.getReferenceById(datosActualizarRespuesta.id());
        respuesta.actulizarRespuesta(datosActualizarRespuesta);

        DatosRespuestaRespuesta datosRespuestaRespuesta = new DatosRespuestaRespuesta(respuesta);
        return ResponseEntity.ok(datosRespuestaRespuesta);
    }
    @GetMapping
    public ResponseEntity<Page<DatosRespuestaRespuesta>> buscarRespuestas(Pageable paginacion)
    {
        Page<Respuesta> respuestas = respuestaRepository.findAll(paginacion);


        return ResponseEntity.ok(respuestaRepository.findAll(paginacion).map(DatosRespuestaRespuesta::new));
    }
    @GetMapping("{id}")
    public ResponseEntity<Page<DatosRespuestaRespuesta>> buscarRespuestaPorTopico(Pageable paginacion,@PathVariable Long id){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);

        return ResponseEntity.ok(respuestaRepository.findByTopicoId(paginacion,id).map(DatosRespuestaRespuesta::new));
    }

}
