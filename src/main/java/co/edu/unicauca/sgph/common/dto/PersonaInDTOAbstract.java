package co.edu.unicauca.sgph.common.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import co.edu.unicauca.sgph.common.infrastructure.input.validation.ExistsByEmail;
import co.edu.unicauca.sgph.common.infrastructure.input.validation.ExistsByTipoAndNumeroIdentificacion;

@ExistsByEmail
@ExistsByTipoAndNumeroIdentificacion
public class PersonaInDTOAbstract {

	private Long idPersona;
	@NotNull
	private Long  idTipoIdentificacion;
	@NotEmpty
	private String numeroIdentificacion;
	@NotEmpty
	private String primerNombre;

	private String segundoNombre;
	@NotEmpty
	private String primerApellido;

	private String segundoApellido;

	@NotEmpty
	private String email;
	
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
}