package com.alura.foro.domain.topicos;


import com.alura.foro.domain.cursos.DatosRespuestaCurso;
import com.alura.foro.domain.usuarios.DatosRespuestaUsuario;


import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico estado,
        DatosRespuestaUsuario usuario,
        DatosRespuestaCurso curso


) {
    public DatosRespuestaTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(),topico.getFechaCreacion(), topico.getEstado(),new DatosRespuestaUsuario(topico.getUsuario()),new DatosRespuestaCurso(topico.getCurso()));
    }

}
