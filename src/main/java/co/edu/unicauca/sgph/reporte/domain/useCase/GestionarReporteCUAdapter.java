package co.edu.unicauca.sgph.reporte.domain.useCase;

import co.edu.unicauca.sgph.reporte.aplication.input.GestionarReporteIntPort;
import co.edu.unicauca.sgph.reporte.aplication.output.GestionarReporteGatewayIntPort;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteSimcaDTO;
import org.springframework.stereotype.Service;

@Service
public class GestionarReporteCUAdapter implements GestionarReporteIntPort {

	private final GestionarReporteGatewayIntPort gestionarReporteGatewayIntPort;

	public GestionarReporteCUAdapter(GestionarReporteGatewayIntPort gestionarReporteGatewayIntPort) {
		this.gestionarReporteGatewayIntPort = gestionarReporteGatewayIntPort;
	}

	@Override
	public ReporteSimcaDTO generarReporteSimca(ReporteSimcaDTO reporte) {
		return this.gestionarReporteGatewayIntPort.generarReporteSimca(reporte);
	}

	@Override
	public ReporteDocenteDTO obtenerReporteLaborDocente(ReporteDocenteDTO filtro) {
		return this.gestionarReporteGatewayIntPort.obtenerReporteLaborDocente(filtro);
	}
}