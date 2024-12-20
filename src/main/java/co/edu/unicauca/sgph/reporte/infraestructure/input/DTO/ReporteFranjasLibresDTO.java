package co.edu.unicauca.sgph.reporte.infraestructure.input.DTO;

import java.time.LocalTime;
import java.util.List;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class ReporteFranjasLibresDTO {
	private Long idEspacioFisico; // ID del espacio físico asociado a la franja libre
    private DiaSemanaEnum dia; // Día de la semana
    private LocalTime horaInicio; // Hora de inicio de la franja
    private LocalTime horaFin; // Hora de fin de la franja    
    private String salon;
    private Long capacidad;
    private String tipo;
    private String nombre;    
    private List<Long> idUbicaciones;
    private String archivoBase64;
    
	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}
	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}
	public DiaSemanaEnum getDia() {
		return dia;
	}
	public void setDia(DiaSemanaEnum dia) {
		this.dia = dia;
	}
	public LocalTime getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	public LocalTime getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}
	public String getSalon() {
		return salon;
	}
	public void setSalon(String salon) {
		this.salon = salon;
	}
	public Long getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Long> getIdUbicaciones() {
		return idUbicaciones;
	}
	public void setIdUbicaciones(List<Long> idUbicaciones) {
		this.idUbicaciones = idUbicaciones;
	}
	public String getArchivoBase64() {
		return archivoBase64;
	}
	public void setArchivoBase64(String archivoBase64) {
		this.archivoBase64 = archivoBase64;
	}
    
}
