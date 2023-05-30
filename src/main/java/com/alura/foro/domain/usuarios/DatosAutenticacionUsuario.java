package com.alura.foro.domain.usuarios;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(@NotBlank String email,@NotBlank String contraseña) {
}
