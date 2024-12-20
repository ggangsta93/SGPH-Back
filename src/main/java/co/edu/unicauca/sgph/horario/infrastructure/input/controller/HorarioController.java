package co.edu.unicauca.sgph.horario.infrastructure.input.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.horario.aplication.input.GestionarHorarioCUIntPort;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.FranjaLibreOutDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.mapper.HorarioRestMapper;
import org.springframework.data.domain.Page;

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
	
	@GetMapping("/consultarFranjasLibres")
	public ResponseEntity<Page<FranjaLibreOutDTO>> consultarFranjasLibres(
	    @RequestParam(required = false) Long idEspacioFisico,
	    @RequestParam(required = false) DiaSemanaEnum diaSemana,
	    @RequestParam(required = false) String horaInicio,
	    @RequestParam(required = false) String horaFin,
	    @RequestParam(required = false) String salon,
	    @RequestParam(required = false) List<Long> ubicacion,
	    @RequestParam(defaultValue = "0") Integer pagina,
	    @RequestParam(defaultValue = "10") Integer registrosPorPagina
	) {
	    // Limpiar valores recibidos
	    String horaInicioLimpia = horaInicio != null ? horaInicio.trim() : null;
	    String horaFinLimpia = horaFin != null ? horaFin.trim() : null;

	    // Crear el filtro
	    FiltroFranjaHorariaDisponibleCursoDTO filtro = new FiltroFranjaHorariaDisponibleCursoDTO();
	    if (idEspacioFisico != null) {
	        filtro.setListaIdUbicacion(List.of(idEspacioFisico));
	    }
	    if (diaSemana != null) {
	        filtro.setListaDiaSemanaEnum(List.of(diaSemana));
	    }
	    filtro.setHoraInicio(horaInicioLimpia);
	    filtro.setHoraFin(horaFinLimpia);
	    filtro.setSalon(salon);
	    filtro.setListaIdUbicacion(ubicacion);
	    filtro.setPagina(pagina);
	    filtro.setRegistrosPorPagina(registrosPorPagina);

	    // Llama al caso de uso para obtener las franjas libres paginadas
	    Page<FranjaLibreOutDTO> franjasLibres = gestionarHorarioCUIntPort.consultarFranjasLibres(filtro);

	    // Retorna la respuesta con el objeto paginado
	    return ResponseEntity.ok(franjasLibres);
	}
}
