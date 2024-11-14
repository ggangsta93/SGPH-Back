package co.edu.unicauca.sgph.gestionplanificacion.labordocencia.aplication.input;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;

public interface GestionarLaborDocenciaCUIntPort {

	public MensajeOutDTO cargarLaborDocente(ReporteDocenteDTO reporteDocenteDTO);

	public ReporteDocenteDTO consultaLaborDocente(ReporteDocenteDTO reporteDocenteDTO);
}