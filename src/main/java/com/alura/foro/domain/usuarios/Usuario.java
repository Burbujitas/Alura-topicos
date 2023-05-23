package com.alura.foro.domain.usuarios;

import com.alura.foro.domain.respuestas.Respuesta;
import com.alura.foro.domain.topicos.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String email;
	private String contraseña;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuarioId",referencedColumnName = "id")
	private List<Topico> topicos = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuarioId",referencedColumnName = "id")
	private List<Respuesta> respuestas = new ArrayList<>();

	public Usuario(DatosRegistroUsuario datosRegistroUsuario){
		this.nombre = datosRegistroUsuario.nombre();
		this.email = datosRegistroUsuario.email();
		this.contraseña = datosRegistroUsuario.contraseña();
	}

	public void actualizarUsuarios(DatosActualizarUsuario datosActualizarUsuario){
		if(datosActualizarUsuario.nombre()!=null)
			this.nombre = datosActualizarUsuario.nombre();
		if(datosActualizarUsuario.email() != null)
			this.email = datosActualizarUsuario.email();
		if(datosActualizarUsuario.contraseña()!=contraseña)
			this.contraseña=datosActualizarUsuario.contraseña();
	}

}
