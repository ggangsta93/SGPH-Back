package co.edu.unicauca.sgph.reporte.aplication.input;

import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteSimcaDTO;
public interface GestionarReporteIntPort {
    ReporteSimcaDTO generarReporteSimca(ReporteSimcaDTO reporte);
}
