package co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTORequest;

import java.time.LocalDate;

public class ReservaTemporalInDTO {

	private Long idEspacioFisico;
    private Long idUsuario;
    private LocalDate fechaReserva;
    private String estado;
    private String observaciones;
	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}
	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public LocalDate getFechaReserva() {
		return fechaReserva;
	}
	public void setFechaReserva(LocalDate fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
}
