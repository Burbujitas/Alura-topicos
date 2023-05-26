package com.alura.foro.domain.respuestas;

import com.alura.foro.domain.usuarios.DatosRespuestaUsuario;
import com.alura.foro.domain.usuarios.Usuario;

import java.time.LocalDateTime;

public record DatosRespuestaRespuesta(
 Long id,
 String mensaje,
 LocalDateTime fechaCreacion,
 Boolean solucion,
 DatosRespuestaUsuario usuario,
 Long topicoId

) {
    public DatosRespuestaRespuesta(Respuesta respuesta){
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaCreacion(),respuesta.getSolucion(),new DatosRespuestaUsuario(respuesta.getUsuario()), respuesta.getTopicoId());
    }


}
