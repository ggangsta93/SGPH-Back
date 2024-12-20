package co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class FranjaLibreOutDTO implements Serializable {
	private static final long serialVersionUID = 1L;

    private Long idEspacioFisico; // ID del espacio físico asociado a la franja libre
    private DiaSemanaEnum dia; // Día de la semana
    private LocalTime horaInicio; // Hora de inicio de la franja
    private LocalTime horaFin; // Hora de fin de la franja    
    private String salon;
    private Long capacidad;
    private String tipo;
    private String nombre;
    private List<Long> idUbicaciones;

    public FranjaLibreOutDTO() {
        // Constructor vacío para uso genérico
    }

    public FranjaLibreOutDTO(Long idEspacioFisico, DiaSemanaEnum dia, LocalTime horaInicio, LocalTime horaFin, String salon, Long capacidad,
            String tipo, String nombre, List<Long> idUbicaciones) {
		this.idEspacioFisico = idEspacioFisico;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.salon = salon;
		this.capacidad = capacidad;
		this.tipo = tipo;
		this.nombre = nombre;
		this.idUbicaciones = idUbicaciones;
	}

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
    
}
