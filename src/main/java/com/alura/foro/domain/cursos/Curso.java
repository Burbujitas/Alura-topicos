package com.alura.foro.domain.cursos;

import com.alura.foro.domain.topicos.Topico;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Curso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String categoria;
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "cursoId",referencedColumnName = "id")
//	private List<Topico> topicos = new ArrayList<>();

	public Curso(DatosRegistroCurso datosRegistroCurso){
		this.nombre = datosRegistroCurso.nombre();
		this.categoria = datosRegistroCurso.categoria();

	}

	public void actualizarCurso(DatosActualizarCurso datosActualizarCurso) {
		if(datosActualizarCurso.nombre()!=null)
			this.nombre=datosActualizarCurso.nombre();
		if(datosActualizarCurso.categoria()!=null)
			this.categoria=datosActualizarCurso.categoria();
	}
}
