package co.edu.unicauca.sgph.asignatura.domain.model;

import java.util.ArrayList;
import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.programa.domain.model.Programa;
import lombok.Data;

@Data
public class Asignatura {

	private Long idAsignatura;

	private String nombre;

	private String codigoAsignatura;

	private String OID;

	private Integer semestre;

	private String pensum;

	private Integer horasSemana;

	private Programa programa;

	private List<AgrupadorEspacioFisico> agrupadores;

	public Asignatura() {
		agrupadores = new ArrayList<>();
	}

	public Asignatura(Long idAsignatura) {
		this.idAsignatura = idAsignatura;
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

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public List<AgrupadorEspacioFisico> getAgrupadores() {
		return agrupadores;
	}

	public void setAgrupadores(List<AgrupadorEspacioFisico> agrupadores) {
		this.agrupadores = agrupadores;
	}
}