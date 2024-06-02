package co.edu.unicauca.sgph.reporte.infraestructure.input.DTO;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.HorarioInDTO;

import java.time.LocalTime;
import java.util.List;

public class HorarioDTO {
    private DiaSemanaEnum dia;
    private LocalTime horarioInicio;
    private LocalTime horaFin;

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
}
