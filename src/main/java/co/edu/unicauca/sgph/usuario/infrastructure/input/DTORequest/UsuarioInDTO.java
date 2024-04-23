package co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.EstadoUsuarioEnum;

public class UsuarioInDTO {
	
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

	@NotEmpty(message = "{usuario.email.not.null}")
	private String email;
	
	@NotEmpty
	private String nombreUsuario;
	
	@NotEmpty
	private String password;
	
	@NotNull
	private EstadoUsuarioEnum estado;
	
	private List<Long> lstIdRol;
	
	private List<Long> lstIdPrograma;
	
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
}