package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.UsuarioEntity;

@Entity
@Table(name = "log_reservas")
public class LogReservasEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOG", nullable = false)
    private Long idLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESERVA", nullable = false)
    private ReservaTemporalEntity reserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "FECHA_MODIFICACION", nullable = false)
    private LocalDateTime fechaModificacion;

    @Column(name = "ACCION", nullable = false)
    private String accion;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

	public Long getIdLog() {
		return idLog;
	}

	public void setIdLog(Long idLog) {
		this.idLog = idLog;
	}

	public ReservaTemporalEntity getReserva() {
		return reserva;
	}

	public void setReserva(ReservaTemporalEntity reserva) {
		this.reserva = reserva;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
    
}
