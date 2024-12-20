package co.edu.unicauca.sgph.reporte.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteEspacioFisicoDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteFranjasLibresDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteSimcaDTO;

public interface GestionarReporteGatewayIntPort {
	ReporteSimcaDTO generarReporteSimca(ReporteSimcaDTO reporteSimcaDTO);

	ReporteDocenteDTO obtenerReporteLaborDocente(ReporteDocenteDTO filtro);
	
	ReporteEspacioFisicoDTO obtenerReporteHorarioEspaciosFisicos(ReporteEspacioFisicoDTO filtro);
	
	ReporteFranjasLibresDTO obtenerReporteHorarioFranjasLibres(List<ReporteFranjasLibresDTO> filtro);
}