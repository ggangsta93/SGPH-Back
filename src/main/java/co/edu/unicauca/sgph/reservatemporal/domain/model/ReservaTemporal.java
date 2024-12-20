package co.edu.unicauca.sgph.reservatemporal.domain.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReservaTemporal {
	private Long idReserva;
    private Long idEspacioFisico;
    private Long idUsuario;
    private LocalDate fechaReserva;
    private String estado;
    private String observaciones;

    public ReservaTemporal() {
    }

    public ReservaTemporal(Long idReserva) {
        this.idReserva = idReserva;
    }

	public Long getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Long idReserva) {
		this.idReserva = idReserva;
	}

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
