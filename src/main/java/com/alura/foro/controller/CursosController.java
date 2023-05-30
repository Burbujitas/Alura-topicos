package com.alura.foro.controller;


import com.alura.foro.domain.cursos.*;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursosController {

    private CursoRepository cursoRepository;
    @Autowired
    public CursosController(CursoRepository cursoRepository)
    {
        this.cursoRepository = cursoRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaCurso> registrarCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso, UriComponentsBuilder uriComponentsBuilder)
    {
        Curso curso = cursoRepository.save(new Curso(datosRegistroCurso));
        DatosRespuestaCurso datosRespuestaCurso = new DatosRespuestaCurso(curso);
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaCurso);

    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso)
    {
        Curso curso = cursoRepository.getReferenceById(datosActualizarCurso.id());
        curso.actualizarCurso(datosActualizarCurso);

        return ResponseEntity.ok(new DatosRespuestaCurso(curso.getId(),curso.getNombre(),curso.getCategoria()));

    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaCurso>> listarCursos(Pageable paginacion)
    {
        return ResponseEntity.ok(cursoRepository.findAll(paginacion).map(DatosRespuestaCurso::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaCurso> listarCurso(@PathVariable Long id)
    {
        Curso curso = cursoRepository.getReferenceById(id);
        DatosRespuestaCurso datosRespuestaCurso = new DatosRespuestaCurso(curso);
        return ResponseEntity.ok(datosRespuestaCurso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarCurso(@PathVariable Long id){
        Curso curso = cursoRepository.getReferenceById(id);
        cursoRepository.delete(curso);
        return ResponseEntity.noContent().build();
    }
}
