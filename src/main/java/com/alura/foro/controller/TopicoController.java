package com.alura.foro.controller;

import com.alura.foro.domain.cursos.Curso;
import com.alura.foro.domain.cursos.CursoRepository;
import com.alura.foro.domain.cursos.DatosRespuestaCurso;
import com.alura.foro.domain.topicos.*;
import com.alura.foro.domain.usuarios.DatosRespuestaUsuario;
import com.alura.foro.domain.usuarios.Usuario;
import com.alura.foro.domain.usuarios.UsuarioRepository;
import jakarta.persistence.Enumerated;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private TopicoRepository topicoRepository;
    private UsuarioRepository usuarioRepository;
    private CursoRepository cursoRepository;
    @Autowired
    public TopicoController(TopicoRepository topicoRepository,UsuarioRepository usuarioRepository, CursoRepository cursoRepository)
    {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> añadirTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder)
    {
        Usuario usuario = usuarioRepository.getReferenceById(datosRegistroTopico.usuarioId());
        Curso curso = cursoRepository.getReferenceById(datosRegistroTopico.cursoId());

        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico,curso,usuario));

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico,new DatosRespuestaUsuario(usuario),new DatosRespuestaCurso(curso));

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id,@RequestBody @Valid DatosActualizarTopico datosActualizarTopico)
    {
        Topico topico = topicoRepository.getReferenceById(id);

        topico.actualizarTopico(datosActualizarTopico);
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico,new DatosRespuestaUsuario(topico.getUsuario()),new DatosRespuestaCurso(topico.getCurso()));

        return ResponseEntity.ok(datosRespuestaTopico);
    }

    @GetMapping
    public ResponseEntity<Page<Topico>> mostrarTopicos(@PageableDefault(size = 10)Pageable paginacion)
    {
        List<StatusTopico> lista = Arrays.asList(StatusTopico.CERRADO);
       // Page<Topico> topico = topicoRepository.findAll(paginacion);

        return ResponseEntity.ok(topicoRepository.findByEstadoNotIn(paginacion,lista));
    }

    @GetMapping("{id}")
    public ResponseEntity<DatosRespuestaTopico> mostrarTopico(@PathVariable Long id)
    {
        Topico topico = topicoRepository.getReferenceById(id);

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico,new DatosRespuestaUsuario(topico.getUsuario()),new DatosRespuestaCurso(topico.getCurso()));

        return ResponseEntity.ok(datosRespuestaTopico);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id)
    {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.eliminarTopico();
        return ResponseEntity.noContent().build();
    }
}
