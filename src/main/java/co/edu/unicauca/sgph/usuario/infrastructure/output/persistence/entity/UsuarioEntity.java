package co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import co.edu.unicauca.sgph.persona.infrastructure.output.persistence.entity.PersonaEntity;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity.ProgramaEntity;

@Entity
@Table(name = "USUARIO", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"ID_PERSONA"})
})
public class UsuarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO", nullable = false)
	private Long idUsuario;
	
	@NotNull
	@Column(name = "NOMBRE_USUARIO", unique = true)
	private String nombreUsuario;

	@NotNull
	@Column(name = "PASSWORD")
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO")
	private EstadoUsuarioEnum estado;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USUARIO_ROL", joinColumns = @JoinColumn(name = "ID_USUARIO"), inverseJoinColumns = @JoinColumn(name = "ID_ROL"))
	private Set<RolEntity> roles;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USUARIO_PROGRAMA", joinColumns = @JoinColumn(name = "ID_USUARIO"), inverseJoinColumns = @JoinColumn(name = "ID_PROGRAMA"))
	private List<ProgramaEntity> programas;
		
	@OneToOne
    @JoinColumn(name = "ID_PERSONA", nullable = false)
    private PersonaEntity persona;
	
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

	public EstadoUsuarioEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoUsuarioEnum estado) {
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public PersonaEntity getPersona() {
		return persona;
	}

	public void setPersona(PersonaEntity persona) {
		this.persona = persona;
	}
}