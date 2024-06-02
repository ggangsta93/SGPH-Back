package co.edu.unicauca.sgph.reporte.aplication.output;

import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteSimcaDTO;

import java.util.List;

public interface GestionarReporteGatewayIntPort {
	ReporteSimcaDTO generarReporteSimca(ReporteSimcaDTO reporteSimcaDTO);
}