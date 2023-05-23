package com.alura.foro.domain.usuarios;

import jakarta.validation.constraints.Email;

public record DatosActualizarUsuario(
        Long id,
        String nombre,
        @Email
        String email,
        String contraseña) {
}
