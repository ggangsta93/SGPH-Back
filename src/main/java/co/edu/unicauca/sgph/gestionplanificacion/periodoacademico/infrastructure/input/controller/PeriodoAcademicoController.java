package co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.input.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.aplication.input.GestionarPeriodoAcademicoCUIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.input.DTORequest.PeriodoAcademicoInDTO;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.input.DTOResponse.PeriodoAcademicoOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.output.persistence.mapper.PeriodoAcademicoRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarPeriodoAcademico")
public class PeriodoAcademicoController {

	private GestionarPeriodoAcademicoCUIntPort gestionarPeriodoAcademicoCUIntPort;
	private PeriodoAcademicoRestMapper periodoAcademicoRestMapper;

	public PeriodoAcademicoController(GestionarPeriodoAcademicoCUIntPort gestionarPeriodoAcademicoCUIntPort,
			PeriodoAcademicoRestMapper periodoAcademicoRestMapper) {
		this.gestionarPeriodoAcademicoCUIntPort = gestionarPeriodoAcademicoCUIntPort;
		this.periodoAcademicoRestMapper = periodoAcademicoRestMapper;
	}
	
	
	/**
	 * Método encargado de guardar o actualizar un periodo academico </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param periodoAcademicoInDTO
	 * @return
	 */
	@PostMapping("/guardarPeriodoAcademico")
	public PeriodoAcademicoOutDTO guardarPeriodoAcademico(@RequestBody PeriodoAcademicoInDTO periodoAcademicoInDTO) {
		return this.periodoAcademicoRestMapper.toPeriodoAcademicoOutDTO(this.gestionarPeriodoAcademicoCUIntPort
				.guardarPeriodoAcademico(this.periodoAcademicoRestMapper.toPeriodoAcademico(periodoAcademicoInDTO)));
	}
}