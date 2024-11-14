package co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.input.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.aplication.input.GestionarLaborDocenciaCUIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.input.mapper.LaborDocenciaRestMapper;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;

@RestController
@RequestMapping("/AdministrarLaborDocente")
@Service
public class LaborDocenciaController extends CommonEJB{

	private GestionarLaborDocenciaCUIntPort gestionarLaborDocenciaCUIntPort;
	private LaborDocenciaRestMapper laborDocenciaRestMapper;

	@PersistenceContext
	private EntityManager entityManager;

	public LaborDocenciaController(GestionarLaborDocenciaCUIntPort gestionarLaborDocenciaCUIntPort,
			LaborDocenciaRestMapper laborDocenciaRestMapper) {
		this.gestionarLaborDocenciaCUIntPort = gestionarLaborDocenciaCUIntPort;
		this.laborDocenciaRestMapper = laborDocenciaRestMapper;
	}

	@PostMapping("/cargarLaborDocente")
	public MensajeOutDTO cargarLaborDocente(@RequestBody ReporteDocenteDTO reporteDocenteDTO) {
		return this.gestionarLaborDocenciaCUIntPort.cargarLaborDocente(reporteDocenteDTO);
	}
	@PostMapping("/consultaLaborDocente")
	public ReporteDocenteDTO consultaLaborDocente(@RequestBody ReporteDocenteDTO reporteDocenteDTO) {
		return this.gestionarLaborDocenciaCUIntPort.consultaLaborDocente(reporteDocenteDTO);
	}
}