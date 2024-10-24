package co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import co.edu.unicauca.sgph.docente.infrastructure.input.validation.ExisteCodigoDocente;
import co.edu.unicauca.sgph.docente.infrastructure.input.validation.ExisteIdPersonaDocente;
@ExisteCodigoDocente
@ExisteIdPersonaDocente
public class DocenteLaborDTO {
	@JsonProperty("OIDPERIODO")
    private int oidPeriodo;

    @JsonProperty("PERIODO")
    private String periodo;

    @JsonProperty("OIDTIPOIDENTIFICACION")
    private Long oidTipoIdentificacion;

    @JsonProperty("TIPOIDENTIFICACION")
    private String tipoIdentificacion;

    @JsonProperty("IDENTIFICACION")
    private String identificacion;

    @JsonProperty("PRIMERAPELLIDO")
    private String primerApellido;

    @JsonProperty("SEGUNDOAPELLIDO")
    private String segundoApellido;

    @JsonProperty("PRIMERNOMBRE")
    private String primerNombre;

    @JsonProperty("SEGUNDONOMBRE")
    private String segundoNombre;

    @JsonProperty("CORREO")
    private String correo;

    @JsonProperty("NOMBREMATERIA")
    private String nombreMateria;

    @JsonProperty("NOMBREPROGRAMA")
    private String nombrePrograma;

    @JsonProperty("OID")
    private String oid;

    @JsonProperty("CODIGOMATERIA")
    private String codigo;

    @JsonProperty("TIPOASIGNATURA")
    private String tipoMateria;

    @JsonProperty("GRUPO")
    private String grupo;

    @JsonProperty("HORASTEORICAS")
    private String horasTeoricas;

    @JsonProperty("SEMESTRE")
    private Integer semestre;

    @JsonProperty("OIDPENSUM")
    private Long oidPensum;

    @JsonProperty("OIDDEPARTAMENTO")
    private Long oidDepartamento;

    @JsonProperty("DEPARTAMENTO")
    private String departamento;

    @JsonProperty("PROGRAMAASIGNATURA")
    private Long programaAsignatura;

    @JsonProperty("HORASSEMANALES")
    private Integer horasSemanales;

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

	public Long getOidTipoIdentificacion() {
		return oidTipoIdentificacion;
	}

	public void setOidTipoIdentificacion(Long oidTipoIdentificacion) {
		this.oidTipoIdentificacion = oidTipoIdentificacion;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
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

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public Long getOidPensum() {
		return oidPensum;
	}

	public void setOidPensum(Long oidPensum) {
		this.oidPensum = oidPensum;
	}

	public Long getOidDepartamento() {
		return oidDepartamento;
	}

	public void setOidDepartamento(Long oidDepartamento) {
		this.oidDepartamento = oidDepartamento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public Long getProgramaAsignatura() {
		return programaAsignatura;
	}

	public void setProgramaAsignatura(Long programaAsignatura) {
		this.programaAsignatura = programaAsignatura;
	}

	public Integer getHorasSemanales() {
		return horasSemanales;
	}

	public void setHorasSemanales(Integer horasSemanales) {
		this.horasSemanales = horasSemanales;
	}
	
}
