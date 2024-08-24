package co.edu.unicauca.sgph.seguridad.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.usuario.domain.model.Usuario;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.EstadoUsuarioEnum;

public class UsuarioPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 2840076810500648125L;
	
	private String nombreUsuario;
	private String email;
	private String password;
	private EstadoUsuarioEnum estado;
	private List<Programa> programas;
	private Collection<? extends GrantedAuthority> authorities;

	public UsuarioPrincipal(String nombreUsuario, String email, String password, EstadoUsuarioEnum estado,
			List<Programa> programas, Collection<? extends GrantedAuthority> authorities) {
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.estado=estado;
		this.programas=programas;
	}

	public static UsuarioPrincipal build(Usuario usuario) {
		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getRolUsuario().name())).collect(Collectors.toList());
		return new UsuarioPrincipal(usuario.getNombreUsuario(), usuario.getPersona().getEmail(), usuario.getPassword(),
				usuario.getEstado(), usuario.getProgramas(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return nombreUsuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		// Cambiado a true
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// Cambiado a true
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// Cambiado a true
		return true;
	}

	@Override
	public boolean isEnabled() {
		// Cambiado a true
		return true;
	}

	public String getEmail() {
		return email;
	}

	public EstadoUsuarioEnum getEstado() {
		return estado;
	}

	public List<Programa> getProgramas() {
		return programas;
	}
}