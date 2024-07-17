package co.edu.unicauca.sgph.reporte.aplication.output;

import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteSimcaDTO;

public interface GestionarReporteGatewayIntPort {
	ReporteSimcaDTO generarReporteSimca(ReporteSimcaDTO reporteSimcaDTO);

	ReporteDocenteDTO obtenerReporteLaborDocente(ReporteDocenteDTO filtro);
}