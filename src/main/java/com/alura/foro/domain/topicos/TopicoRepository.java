package com.alura.foro.domain.topicos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface TopicoRepository extends JpaRepository<Topico,Long> {

    public Page<Topico> findByEstadoNotIn(Pageable paginacion,List<StatusTopico> estados);

}
