package co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ROL")
public class RolEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRol;

	@NotNull
	@Column(name = "ROL")
	@Enumerated(EnumType.STRING)
	private RolUsuarioEnum rolUsuario;

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public RolUsuarioEnum getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(RolUsuarioEnum rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
}