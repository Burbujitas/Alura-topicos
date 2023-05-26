package com.alura.foro.domain.respuestas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {

    public Page<Respuesta> findByTopicoId(Pageable paginacion, Long topicoId);
}
