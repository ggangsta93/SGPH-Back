package co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest;

import java.util.List;

public class AsignaturaInDTO {

	private Long idAsignatura;

	private String nombre;

	private String codigoAsignatura;

	private String OID;

	private Integer semestre;

	private String pensum;

	private Integer horasSemana;

	private Long idPrograma;
	private String base64;

	public AsignaturaInDTO(String nombre,
						   String codigoAsignatura,
						   String OID,
						   Integer semestre,
						   String pensum,
						   Integer horasSemana,
						   Long idPrograma) {
		this.nombre = nombre;
		this.codigoAsignatura = codigoAsignatura;
		this.OID = OID;
		this.semestre = semestre;
		this.pensum = pensum;
		this.horasSemana = horasSemana;
		this.idPrograma = idPrograma;
	}

	private List<Long> lstIdAgrupadorEspacioFisico;
	
	private Boolean aplicaEspacioSecundario;
	
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

	public List<Long> getLstIdAgrupadorEspacioFisico() {
		return lstIdAgrupadorEspacioFisico;
	}

	public void setLstIdAgrupadorEspacioFisico(List<Long> lstIdAgrupadorEspacioFisico) {
		this.lstIdAgrupadorEspacioFisico = lstIdAgrupadorEspacioFisico;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
	public Boolean getAplicaEspacioSecundario() {
		return aplicaEspacioSecundario;
	}

	public void setAplicaEspacioSecundario(Boolean aplicaEspacioSecundario) {
		this.aplicaEspacioSecundario = aplicaEspacioSecundario;
	}
}