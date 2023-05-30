package com.alura.foro.domain.usuarios;

import com.alura.foro.domain.respuestas.Respuesta;
import com.alura.foro.domain.topicos.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long ususarioId;
	private String nombre;
	private String email;
	private String contraseña;

	//@OneToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name = "usuarioId",referencedColumnName = "id")
	//private List<Topico> topicos = new ArrayList<>();
	//@OneToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name = "usuarioId",referencedColumnName = "id")
	//private List<Respuesta> respuestas = new ArrayList<>();

	public Usuario(DatosRegistroUsuario datosRegistroUsuario,String contraseña){
		this.nombre = datosRegistroUsuario.nombre();
		this.email = datosRegistroUsuario.email();
		this.contraseña = contraseña;
	}

	public void actualizarUsuarios(DatosActualizarUsuario datosActualizarUsuario,String contraseña){
		if(datosActualizarUsuario.nombre()!=null)
			this.nombre = datosActualizarUsuario.nombre();
		if(datosActualizarUsuario.email() != null)
			this.email = datosActualizarUsuario.email();
		if(datosActualizarUsuario.contraseña()!=null)
			this.contraseña=contraseña;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return contraseña;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
