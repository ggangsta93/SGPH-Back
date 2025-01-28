package co.edu.unicauca.sgph.persona.infrastructure.input.DTORequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.edu.unicauca.sgph.espaciofisico.infrastructura.input.validation.ValidationGroups;
import co.edu.unicauca.sgph.persona.infrastructure.input.validation.ExisteEmail;
import co.edu.unicauca.sgph.persona.infrastructure.input.validation.ExistePersonaPorIdentificacion;

@ExisteEmail(groups = ValidationGroups.OnCreate.class)
@ExistePersonaPorIdentificacion(groups = ValidationGroups.OnCreate.class)
public class PersonaInDTO {

	private Long idPersona;
	@NotNull(groups = ValidationGroups.OnCreate.class)
	private Long idTipoIdentificacion;
	@NotEmpty(groups = ValidationGroups.OnCreate.class)  
	private String numeroIdentificacion;
	@NotEmpty
	private String primerNombre;

	private String segundoNombre;
	
	@NotEmpty
	private String primerApellido;

	private String segundoApellido;

	@NotEmpty(groups = ValidationGroups.OnCreate.class)
	private String email;

	/**
	 * Atributo que determina si la invocación es para validar la información o
	 * persistirla
	 */
	private Boolean esValidar;

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public Long getIdTipoIdentificacion() {
		return idTipoIdentificacion;
	}

	public void setIdTipoIdentificacion(Long idTipoIdentificacion) {
		this.idTipoIdentificacion = idTipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEsValidar() {
		return esValidar;
	}

	public void setEsValidar(Boolean esValidar) {
		this.esValidar = esValidar;
	}
}