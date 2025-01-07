package co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
//@ExisteCodigoDocente
//@ExisteIdPersonaDocente
public class DocenteLaborDTO {
	@NotEmpty
    @JsonProperty("periodo")
    private String periodo;

    @NotNull
    @JsonProperty("oidTipoIdentificacion")
    private Long oidTipoIdentificacion;

    @NotEmpty
    @JsonProperty("tipoIdentificacion")
    private String tipoIdentificacion;

    @NotEmpty
    @JsonProperty("identificacion")
    private String identificacion;

    @NotEmpty
    @JsonProperty("primerApellido")
    private String primerApellido;

    @JsonProperty("segundoApellido")
    private String segundoApellido;

    @NotEmpty
    @JsonProperty("primerNombre")
    private String primerNombre;

    @JsonProperty("segundoNombre")
    private String segundoNombre;

    @NotEmpty
    @JsonProperty("correo")
    private String correo;

    @NotNull
    @JsonProperty("oidDepartamento")
    private Long oidDepartamento;

    @NotEmpty
    @JsonProperty("departamento")
    private String departamento;

    @NotNull
    @JsonProperty("programaAsignatura")
    private Long programaAsignatura;

    @NotEmpty
    @JsonProperty("nombrePrograma")
    private String nombrePrograma;

    // Campo que no tenías y que sí viene en el JSON
    @NotNull
    @JsonProperty("oidAsignatura")
    private Long oidAsignatura;

    @NotEmpty
    @JsonProperty("codigoMateria")
    private String codigoMateria;

    @NotEmpty
    @JsonProperty("nombreMateria")
    private String nombreMateria;

    // Viene como entero en el JSON
    @NotNull
    @JsonProperty("tipoAsignatura")
    private Integer tipoAsignatura;

    @NotEmpty
    @JsonProperty("grupo")
    private String grupo;

    // Viene como entero en el JSON
    @NotNull
    @JsonProperty("horasTeoricas")
    private Integer horasTeoricas;

    @NotNull
    @JsonProperty("oidPensum")
    private Long oidPensum;

    @NotNull
    @JsonProperty("horasSemanales")
    private Integer horasSemanales;

    @NotNull
    @JsonProperty("semestre")
    private Integer semestre;

    @NotNull
    @JsonProperty("cantidadEstudiantes")
    private Integer cantidadEstudiantes;

    @Override
    public String toString() {
        return "DocenteLaborDTO{" +
               "periodo='" + periodo + '\'' +
               ", oidTipoIdentificacion=" + oidTipoIdentificacion +
               ", tipoIdentificacion='" + tipoIdentificacion + '\'' +
               ", identificacion='" + identificacion + '\'' +
               ", primerApellido='" + primerApellido + '\'' +
               ", segundoApellido='" + segundoApellido + '\'' +
               ", primerNombre='" + primerNombre + '\'' +
               ", segundoNombre='" + segundoNombre + '\'' +
               ", correo='" + correo + '\'' +
               ", oidDepartamento=" + oidDepartamento +
               ", departamento='" + departamento + '\'' +
               ", programaAsignatura=" + programaAsignatura +
               ", nombrePrograma='" + nombrePrograma + '\'' +
               ", oidAsignatura=" + oidAsignatura +
               ", codigoMateria='" + codigoMateria + '\'' +
               ", nombreMateria='" + nombreMateria + '\'' +
               ", tipoAsignatura=" + tipoAsignatura +
               ", grupo='" + grupo + '\'' +
               ", horasTeoricas=" + horasTeoricas +
               ", oidPensum=" + oidPensum +
               ", horasSemanales=" + horasSemanales +
               ", semestre=" + semestre +
               ", cantidadEstudiantes=" + cantidadEstudiantes +
               '}';
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

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public Long getOidAsignatura() {
		return oidAsignatura;
	}

	public void setOidAsignatura(Long oidAsignatura) {
		this.oidAsignatura = oidAsignatura;
	}

	public String getCodigoMateria() {
		return codigoMateria;
	}

	public void setCodigoMateria(String codigoMateria) {
		this.codigoMateria = codigoMateria;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public Integer getTipoAsignatura() {
		return tipoAsignatura;
	}

	public void setTipoAsignatura(Integer tipoAsignatura) {
		this.tipoAsignatura = tipoAsignatura;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Integer getHorasTeoricas() {
		return horasTeoricas;
	}

	public void setHorasTeoricas(Integer horasTeoricas) {
		this.horasTeoricas = horasTeoricas;
	}

	public Long getOidPensum() {
		return oidPensum;
	}

	public void setOidPensum(Long oidPensum) {
		this.oidPensum = oidPensum;
	}

	public Integer getHorasSemanales() {
		return horasSemanales;
	}

	public void setHorasSemanales(Integer horasSemanales) {
		this.horasSemanales = horasSemanales;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public Integer getCantidadEstudiantes() {
		return cantidadEstudiantes;
	}

	public void setCantidadEstudiantes(Integer cantidadEstudiantes) {
		this.cantidadEstudiantes = cantidadEstudiantes;
	}
    
	
}
