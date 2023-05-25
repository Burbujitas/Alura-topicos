package com.alura.foro.domain.topicos;

import com.alura.foro.domain.cursos.Curso;
import com.alura.foro.domain.cursos.DatosActualizarCurso;
import com.alura.foro.domain.respuestas.Respuesta;
import com.alura.foro.domain.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Topico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensaje;
	@Column(name = "fechaCreacion")
	private LocalDateTime fechaCreacion;
	@Enumerated(EnumType.STRING)
	private StatusTopico estado;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "topicoId", referencedColumnName = "id")
	private List<Respuesta> respuestas = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "usuarioId")
	private Usuario usuario;
	@ManyToOne()
	@JoinColumn(name = "cursoId")
	private Curso curso;



	public Topico(DatosRegistroTopico datosRegistroTopico,Curso curso,Usuario usuario)
	{
		this.titulo = datosRegistroTopico.titulo();
		this.mensaje = datosRegistroTopico.mensaje();
		this.fechaCreacion = LocalDateTime.now();
		this.estado = StatusTopico.NO_RESPONDIDO;
		this.curso = curso;
		this.usuario= usuario;
	}
	public void actualizarTopico(DatosActualizarTopico datosActualizarTopico)
	{
		if(datosActualizarTopico.titulo()!=null)
			this.titulo = datosActualizarTopico.titulo();
		if(datosActualizarTopico.mensaje()!=null)
			this.mensaje = datosActualizarTopico.mensaje();
		if(datosActualizarTopico.estado() != null)
			this.estado = datosActualizarTopico.estado();
	}

	public void eliminarTopico() {
		this.estado=StatusTopico.CERRADO;
	}
}
