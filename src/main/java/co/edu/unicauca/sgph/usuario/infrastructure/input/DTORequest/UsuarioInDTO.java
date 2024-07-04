package co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import co.edu.unicauca.sgph.common.dto.PersonaInDTOAbstract;
import co.edu.unicauca.sgph.usuario.infrastructure.input.validation.ExistsAtLeastOneProgramForPlanificadorRole;
import co.edu.unicauca.sgph.usuario.infrastructure.input.validation.ExistsByNombreUsuario;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.EstadoUsuarioEnum;

@ExistsByNombreUsuario
@ExistsAtLeastOneProgramForPlanificadorRole
public class UsuarioInDTO extends PersonaInDTOAbstract{
		
	@NotEmpty
	private String nombreUsuario;
	
	@NotEmpty
	private String password;
	
	@NotNull
	private EstadoUsuarioEnum estado;
	
	private List<Long> lstIdRol;
	
	private List<Long> lstIdPrograma;

	/**
	 * Atributo que determina si la invocación es para validar la información o
	 * persistirla
	 */
	private Boolean esValidar;
	
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

	public List<Long> getLstIdRol() {
		return lstIdRol;
	}

	public void setLstIdRol(List<Long> lstIdRol) {
		this.lstIdRol = lstIdRol;
	}

	public List<Long> getLstIdPrograma() {
		return lstIdPrograma;
	}

	public void setLstIdPrograma(List<Long> lstIdPrograma) {
		this.lstIdPrograma = lstIdPrograma;
	}

	public Boolean getEsValidar() {
		return esValidar;
	}

	public void setEsValidar(Boolean esValidar) {
		this.esValidar = esValidar;
	}
}