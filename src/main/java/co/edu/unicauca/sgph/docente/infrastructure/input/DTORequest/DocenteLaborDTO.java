package co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest;

import co.edu.unicauca.sgph.docente.infrastructure.input.validation.ExisteCodigoDocente;
import co.edu.unicauca.sgph.docente.infrastructure.input.validation.ExisteIdPersonaDocente;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.EstadoDocenteEnum;
import co.edu.unicauca.sgph.persona.infrastructure.input.validation.ExistePersonaPorIdPersona;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ExisteCodigoDocente
@ExisteIdPersonaDocente
public class DocenteLaborDTO {
	private int oidPeriodo;
	private String periodo;
	private String identificacion;
	private String primerApellido;
	private String segundoApellido;
	private String primerNombre;
	private String segundoNombre;
	private String correo;
	private String nombreMateria;
	private String nombrePrograma;
	private String oid;
	private String codigo;
	private String tipoMateria;
	private String grupo;
	private String horasTeoricas;

	public int getOidPeriodo() {
		return oidPeriodo;
	}

	public void setOidPeriodo(int oidPeriodo) {
		this.oidPeriodo = oidPeriodo;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipoMateria() {
		return tipoMateria;
	}

	public void setTipoMateria(String tipoMateria) {
		this.tipoMateria = tipoMateria;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getHorasTeoricas() {
		return horasTeoricas;
	}

	public void setHorasTeoricas(String horasTeoricas) {
		this.horasTeoricas = horasTeoricas;
	}
}
