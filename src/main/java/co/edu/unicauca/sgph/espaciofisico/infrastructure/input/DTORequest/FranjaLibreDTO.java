package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest;

import java.time.LocalTime;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class FranjaLibreDTO {

    private Long idEspacioFisico;
    private String nombre;
    private String edificio;
    private DiaSemanaEnum dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    // Constructor con parámetros
    public FranjaLibreDTO(Long idEspacioFisico, String nombre, String edificio, DiaSemanaEnum dia, LocalTime horaInicio, LocalTime horaFin) {
        this.idEspacioFisico = idEspacioFisico;
        this.nombre = nombre;
        this.edificio = edificio;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y setters
    public Long getIdEspacioFisico() {
        return idEspacioFisico;
    }

    public void setIdEspacioFisico(Long idEspacioFisico) {
        this.idEspacioFisico = idEspacioFisico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
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
}
