package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEntity;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.UsuarioEntity;

@Entity
@Table(name = "RESERVA_TEMPORAL")
public class ReservaTemporalEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA", nullable = false)
    private Long idReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESPACIO_FISICO", nullable = false)
    private EspacioFisicoEntity espacioFisico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_HORARIO", nullable = false)
    private HorarioEntity horario;

    @Column(name = "FECHA_RESERVA", nullable = false)
    private LocalDate fechaReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", nullable = false)
    private EstadoReservaEntity estado;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

	public Long getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Long idReserva) {
		this.idReserva = idReserva;
	}

	public EspacioFisicoEntity getEspacioFisico() {
		return espacioFisico;
	}

	public void setEspacioFisico(EspacioFisicoEntity espacioFisico) {
		this.espacioFisico = espacioFisico;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	public HorarioEntity getHorario() {
		return horario;
	}

	public void setHorario(HorarioEntity horario) {
		this.horario = horario;
	}

	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDate fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public EstadoReservaEntity getEstado() {
		return estado;
	}

	public void setEstado(EstadoReservaEntity estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
