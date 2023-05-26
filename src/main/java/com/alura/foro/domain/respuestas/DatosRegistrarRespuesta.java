package com.alura.foro.domain.respuestas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistrarRespuesta(
        @NotBlank
        String mensaje,
        @NotNull
        Long usuarioId,
        @NotNull
        Long topicoId

) {
}
