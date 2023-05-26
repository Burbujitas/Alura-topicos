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

	@ManyToOne
	@JoinColumn(name = "usuarioId")
	private Usuario usuario;
	private Long topicoId;

	public Respuesta(DatosRegistrarRespuesta datosRegistrarRespuesta,Usuario usuario)
	{
		this.mensaje = datosRegistrarRespuesta.mensaje();
		this.fechaCreacion = LocalDateTime.now();
		this.solucion = false;
		this.usuario = usuario;
		this.topicoId = datosRegistrarRespuesta.topicoId();
	}
	public void actulizarRespuesta(DatosActualizarRespuesta datosActualizarRespuesta)
	{
		if(datosActualizarRespuesta.mensaje()!=null)
			this.mensaje = datosActualizarRespuesta.mensaje();
		if(datosActualizarRespuesta.solucion()!=null)
			this.solucion = datosActualizarRespuesta.solucion();
	}
}
