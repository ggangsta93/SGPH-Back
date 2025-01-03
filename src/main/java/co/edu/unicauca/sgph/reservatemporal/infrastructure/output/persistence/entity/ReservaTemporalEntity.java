package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @Column(name = "USUARIO", nullable = false)
    private String usuario;
    
    @Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacion;

    @Column(name = "NUMERO_IDENTIFICACION")
    private String numeroIdentificacion;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "TIPO_SOLICITANTE")
    private String tipoSolicitante;

    @Column(name = "HORA_INICIO", columnDefinition = "TIME")
	private LocalTime horaInicio;

	@Column(name = "HORA_FIN", columnDefinition = "TIME")
	private LocalTime horaFin;

    @Column(name = "FECHA_RESERVA", nullable = false)
    private LocalDate fechaReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", nullable = false)
    private EstadoReservaEntity estado;
    
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    
    @Column(name = "OBSERVACIONES_PRESTAMISTA")
    private String observacionesPrestamista;

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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getTipoSolicitante() {
		return tipoSolicitante;
	}

	public void setTipoSolicitante(String tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getObservacionesPrestamista() {
		return observacionesPrestamista;
	}

	public void setObservacionesPrestamista(String observacionesPrestamista) {
		this.observacionesPrestamista = observacionesPrestamista;
	}

}
