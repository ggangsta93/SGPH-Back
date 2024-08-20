package co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse;

import java.util.List;

import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.EstadoUsuarioEnum;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.RolUsuarioEnum;

public class UsuarioOutDTO {

	private Long idUsuario;
	
	private String nombreUsuario;
	
	private String password;
	
	private EstadoUsuarioEnum estado;
	
	private List<Long> lstIdRol;
	
	private List<Long> lstIdPrograma;
	
	private Long idPersona;

	private Long idTipoIdentificacion;

	private String numeroIdentificacion;

	private String codigoTipoIdentificacion;

	private String primerNombre;

	private String segundoNombre;

	private String primerApellido;

	private String segundoApellido;

	private String email;
	private List<RolUsuarioEnum> lstRol;

	public UsuarioOutDTO() {
		// Constructor sin parámetros
	}

	public UsuarioOutDTO(Long idUsuario, Long idPersona, Long idTipoIdentificacion, String numeroIdentificacion,
			String codigoTipoIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, String email, String nombreUsuario, String password, EstadoUsuarioEnum estado) {
		this.idUsuario=idUsuario;
		this.idPersona = idPersona;
		this.idTipoIdentificacion = idTipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;
		this.codigoTipoIdentificacion = codigoTipoIdentificacion;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.email = email;
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.estado = estado;
	}

	public List<RolUsuarioEnum> getLstRol() {
		return lstRol;
	}

	public void setLstRol(List<RolUsuarioEnum> lstRol) {
		this.lstRol = lstRol;
	}

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

	public String getCodigoTipoIdentificacion() {
		return codigoTipoIdentificacion;
	}

	public void setCodigoTipoIdentificacion(String codigoTipoIdentificacion) {
		this.codigoTipoIdentificacion = codigoTipoIdentificacion;
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
}