package co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import co.edu.unicauca.sgph.persona.infrastructure.input.validation.ExistePersonaPorIdPersona;
import co.edu.unicauca.sgph.usuario.infrastructure.input.validation.ExisteAlMenosUnProgramaParaRolPlanificador;
import co.edu.unicauca.sgph.usuario.infrastructure.input.validation.ExisteNombreUsuario;
import co.edu.unicauca.sgph.usuario.infrastructure.input.validation.ExisteIdPersonaUsuario;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.EstadoUsuarioEnum;

@ExisteNombreUsuario
@ExisteAlMenosUnProgramaParaRolPlanificador
@ExisteIdPersonaUsuario
public class UsuarioInDTO{
		
	private Long idUsuario;
	
	@NotEmpty
	private String nombreUsuario;
	
	@NotEmpty
	private String password;
	
	@NotNull
	private EstadoUsuarioEnum estado;
	
	private List<Long> lstIdRol;
	
	private List<Long> lstIdPrograma;
	
	@NotNull
	@ExistePersonaPorIdPersona
	private Long idPersona;
	
	/**
	 * Atributo que determina si la invocación es para validar la información o
	 * persistirla
	 */
	private Boolean esValidar;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public Boolean getEsValidar() {
		return esValidar;
	}

	public void setEsValidar(Boolean esValidar) {
		this.esValidar = esValidar;
	}
}