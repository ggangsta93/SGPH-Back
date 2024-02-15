package co.edu.unicauca.sgph.seguridad.usuario.infrastructure.output.persistence.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import co.edu.unicauca.sgph.common.infrastructure.output.persistence.entities.PersonaEntity;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity.ProgramaEntity;

@Entity
@Table(name = "USUARIO")
@PrimaryKeyJoinColumn(name = "ID_PERSONA")
public class UsuarioEntity extends PersonaEntity {

	@NotNull
	@Column(name = "NOMBRE_USUARIO", unique = true)
	private String nombreUsuario;

	@NotNull
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "ESTADO")
	private Boolean estado;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USUARIO_ROL", joinColumns = @JoinColumn(name = "ID_PERSONA"), inverseJoinColumns = @JoinColumn(name = "ID_ROL"))
	private Set<RolEntity> roles;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USUARIO_PROGRAMA", joinColumns = @JoinColumn(name = "ID_PERSONA"), inverseJoinColumns = @JoinColumn(name = "ID_PROGRAMA"))
	private List<ProgramaEntity> programas;
		
	public UsuarioEntity() {
		this.roles = new HashSet<>();
		this.programas=new ArrayList<>();
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Set<RolEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolEntity> roles) {
		this.roles = roles;
	}

	public List<ProgramaEntity> getProgramas() {
		return programas;
	}

	public void setProgramas(List<ProgramaEntity> programas) {
		this.programas = programas;
	}
}