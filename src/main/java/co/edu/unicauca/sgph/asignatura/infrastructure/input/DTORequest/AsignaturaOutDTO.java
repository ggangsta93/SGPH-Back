package co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest;

import java.util.List;

public class AsignaturaOutDTO {

	private Long idAsignatura;

	private String nombre;

	private String codigoAsignatura;

	private String OID;

	private Integer semestre;

	private String pensum;

	private Integer horasSemana;

	private Long idPrograma;
	
	private String abreviaturaPrograma;
	
	private List<Long> lstIdTipoEspacioFisico;

	
	
	/**
	 * @param nombre
	 * @param codigoAsignatura
	 * @param oID
	 * @param semestre
	 * @param pensum
	 * @param horasSemana
	 * @param abreviaturaPrograma
	 */
	public AsignaturaOutDTO(String abreviaturaPrograma, Integer semestre, String nombre, String codigoAsignatura, String oID, Integer horasSemana, String pensum) {
		super();
		this.nombre = nombre;
		this.codigoAsignatura = codigoAsignatura;
		OID = oID;
		this.semestre = semestre;
		this.pensum = pensum;
		this.horasSemana = horasSemana;
		this.abreviaturaPrograma = abreviaturaPrograma;
	}

	public Long getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(Long idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoAsignatura() {
		return codigoAsignatura;
	}

	public void setCodigoAsignatura(String codigoAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
	}

	public String getOID() {
		return OID;
	}

	public void setOID(String oID) {
		OID = oID;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public String getPensum() {
		return pensum;
	}

	public void setPensum(String pensum) {
		this.pensum = pensum;
	}

	public Integer getHorasSemana() {
		return horasSemana;
	}

	public void setHorasSemana(Integer horasSemana) {
		this.horasSemana = horasSemana;
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public List<Long> getLstIdTipoEspacioFisico() {
		return lstIdTipoEspacioFisico;
	}

	public String getAbreviaturaPrograma() {
		return abreviaturaPrograma;
	}

	public void setAbreviaturaPrograma(String abreviaturaPrograma) {
		this.abreviaturaPrograma = abreviaturaPrograma;
	}

	public void setLstIdTipoEspacioFisico(List<Long> lstIdTipoEspacioFisico) {
		this.lstIdTipoEspacioFisico = lstIdTipoEspacioFisico;
	}
}