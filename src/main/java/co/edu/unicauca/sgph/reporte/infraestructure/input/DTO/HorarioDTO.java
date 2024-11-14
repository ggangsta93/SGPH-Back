package co.edu.unicauca.sgph.reporte.infraestructure.input.DTO;

import java.time.LocalTime;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class HorarioDTO {
    private DiaSemanaEnum dia;
    private LocalTime horarioInicio;
    private LocalTime horaFin;
    private String salon;

    public DiaSemanaEnum getDia() {
        return dia;
    }

    public void setDia(DiaSemanaEnum dia) {
        this.dia = dia;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
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
}
