package co.edu.unicauca.sgph.curso.infrastructure.input.DTOResponse;

public class CursoOutDTO {

	private Long idCurso;

	private String grupo;

	private Integer cupo;

	private Long idAsignatura;
	private String OIDAsignatura;
	private Long semestre;
	private Long horas;
	private Long idPrograma;
	private Long idFacultad;
	private Long idPeriodoAcademico;
	private String nombreFacultad;
	private String nombrePrograma;
	private String nombreCurso;
	private String periodoAcademicoAnio;

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Integer getCupo() {
		return cupo;
	}

	public void setCupo(Integer cupo) {
		this.cupo = cupo;
	}

	public Long getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(Long idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public Long getIdPeriodoAcademico() {
		return idPeriodoAcademico;
	}

	public void setIdPeriodoAcademico(Long idPeriodoAcademico) {
		this.idPeriodoAcademico = idPeriodoAcademico;
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Long getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(Long idFacultad) {
		this.idFacultad = idFacultad;
	}

	public String getNombreFacultad() {
		return nombreFacultad;
	}

	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public String getNombreCurso() {
		return nombreCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	public String getOIDAsignatura() {
		return OIDAsignatura;
	}

	public void setOIDAsignatura(String OIDAsignatura) {
		this.OIDAsignatura = OIDAsignatura;
	}

	public Long getSemestre() {
		return semestre;
	}

	public void setSemestre(Long semestre) {
		this.semestre = semestre;
	}

	public Long getHoras() {
		return horas;
	}

	public void setHoras(Long horas) {
		this.horas = horas;
	}

	public String getPeriodoAcademicoAnio() {
		return periodoAcademicoAnio;
	}

	public void setPeriodoAcademicoAnio(String periodoAcademicoAnio) {
		this.periodoAcademicoAnio = periodoAcademicoAnio;
	}
}