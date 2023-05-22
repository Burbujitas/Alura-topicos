package com.alura.foro.domain.topicos;

import com.alura.foro.domain.cursos.Curso;
import com.alura.foro.domain.respuestas.Respuesta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	private LocalDateTime fechaCreacion;
	@Enumerated(EnumType.STRING)
	private StatusTopico status;
    private Long cursoId;
	private Long usuarioId;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "topicoId", referencedColumnName = "id")
	private List<Respuesta> respuestas = new ArrayList<>();


}
