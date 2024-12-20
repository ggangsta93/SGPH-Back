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

@Entity
@Table(name = "NOTIFICACIONES_RESERVA")
public class NotificacionesReservaEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTIFICACION", nullable = false)
    private Long idNotificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESERVA", nullable = false)
    private ReservaTemporalEntity reserva;

    @Column(name = "TIPO", nullable = false)
    private String tipo;

    @Column(name = "MENSAJE", nullable = false)
    private String mensaje;

    @Column(name = "FECHA_ENVIO", nullable = false)
    private LocalDateTime fechaEnvio;

	public Long getIdNotificacion() {
		return idNotificacion;
	}

	public void setIdNotificacion(Long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public ReservaTemporalEntity getReserva() {
		return reserva;
	}

	public void setReserva(ReservaTemporalEntity reserva) {
		this.reserva = reserva;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
    
}
