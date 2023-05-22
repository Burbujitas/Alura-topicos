package com.alura.foro.domain.respuestas;

import com.alura.foro.domain.topicos.Topico;
import com.alura.foro.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuesta_topicos")
@Entity(name = "Respuesta")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Respuesta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mensaje;
	private LocalDateTime fechaCreacion;
	private Boolean solucion;
	private Long usuarioId;
	private Long topicoId;


}
