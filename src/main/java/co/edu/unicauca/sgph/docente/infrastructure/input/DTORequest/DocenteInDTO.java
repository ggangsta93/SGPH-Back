package co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest;

import co.edu.unicauca.sgph.common.dto.PersonaInDTOAbstract;
import co.edu.unicauca.sgph.docente.infrastructure.input.validation.ExistsByCodigo;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.EstadoDocenteEnum;

@ExistsByCodigo
public class DocenteInDTO extends PersonaInDTOAbstract{

	private String codigo;

	private EstadoDocenteEnum estado;

	private Long idDepartamento;

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
}