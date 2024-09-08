package co.edu.unicauca.sgph.horario.infrastructure.input.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.horario.aplication.input.GestionarHorarioCUIntPort;
import co.edu.unicauca.sgph.horario.infrastructure.input.mapper.HorarioRestMapper;

@RestController
@RequestMapping("/AdministrarHorario")
public class HorarioController extends CommonEJB {

	private GestionarHorarioCUIntPort gestionarHorarioCUIntPort;
	private HorarioRestMapper horarioRestMapper;

	public HorarioController(GestionarHorarioCUIntPort gestionarHorarioCUIntPort,
			HorarioRestMapper horarioRestMapper) {
		this.gestionarHorarioCUIntPort = gestionarHorarioCUIntPort;
		this.horarioRestMapper = horarioRestMapper;
	}
	
}
