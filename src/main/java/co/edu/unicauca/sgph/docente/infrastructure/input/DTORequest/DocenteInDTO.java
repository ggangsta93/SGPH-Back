package co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import co.edu.unicauca.sgph.docente.infrastructure.input.validation.ExisteCodigoDocente;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.EstadoDocenteEnum;
import co.edu.unicauca.sgph.persona.infrastructure.input.validation.ExistePersonaPorIdPersona;
import co.edu.unicauca.sgph.docente.infrastructure.input.validation.ExisteIdPersonaDocente;

@ExisteCodigoDocente
@ExisteIdPersonaDocente
public class DocenteInDTO {

	private Long idDocente;
	
	@NotEmpty
	private String codigo;

	@NotNull
	private EstadoDocenteEnum estado;

	@NotNull
	private Long idDepartamento;

	@NotNull
	@ExistePersonaPorIdPersona
	private Long idPersona;

	/**
	 * Atributo que determina si la invocación es para validar la información o
	 * persistirla
	 */
	private Boolean esValidar;

	public Long getIdDocente() {
		return idDocente;
	}

	public void setIdDocente(Long idDocente) {
		this.idDocente = idDocente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public EstadoDocenteEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoDocenteEnum estado) {
		this.estado = estado;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
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