package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity;

import java.time.LocalTime;
import java.util.List;

public class FranjaHorarioEntity {
	private LocalTime horaInicio;
    private LocalTime horaFin;

    public FranjaHorarioEntity(LocalTime horaInicio, LocalTime horaFin) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public static List<FranjaHorarioEntity> obtenerFranjas() {
        return List.of(
            new FranjaHorarioEntity(LocalTime.of(7, 0), LocalTime.of(9, 0)),
            new FranjaHorarioEntity(LocalTime.of(9, 0), LocalTime.of(11, 0)),
            new FranjaHorarioEntity(LocalTime.of(11, 0), LocalTime.of(13, 0)),
            new FranjaHorarioEntity(LocalTime.of(14, 0), LocalTime.of(16, 0)),
            new FranjaHorarioEntity(LocalTime.of(16, 0), LocalTime.of(18, 0)),
            new FranjaHorarioEntity(LocalTime.of(18, 0), LocalTime.of(20, 0)),
            new FranjaHorarioEntity(LocalTime.of(20, 0), LocalTime.of(22, 0))
        );
    }
}
