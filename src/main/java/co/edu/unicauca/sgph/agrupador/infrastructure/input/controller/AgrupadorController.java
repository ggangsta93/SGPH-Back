package co.edu.unicauca.sgph.agrupador.infrastructure.input.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.agrupador.aplication.input.GestionarAgrupadorEspacioFisicoCUIntPort;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest.FiltroGrupoDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoOutDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.mapper.AgrupadorEspacioFisicoRestMapper;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.AsignacionEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;

@RestController
@RequestMapping("/AdministrarAgrupador")
@CrossOrigin(origins = "http://localhost:4200")
public class AgrupadorController {

	// Gestionadores
	private GestionarAgrupadorEspacioFisicoCUIntPort gestionarAgrupadorEspacioFisicoCUIntPort;
	// Mapers
	private AgrupadorEspacioFisicoRestMapper agrupadorEspacioFisicoRestMapper;

	public AgrupadorController(GestionarAgrupadorEspacioFisicoCUIntPort gestionarAgrupadorEspacioFisicoCUIntPort,
			AgrupadorEspacioFisicoRestMapper agrupadorEspacioFisicoRestMapper) {
		this.gestionarAgrupadorEspacioFisicoCUIntPort = gestionarAgrupadorEspacioFisicoCUIntPort;
		this.agrupadorEspacioFisicoRestMapper = agrupadorEspacioFisicoRestMapper;
	}

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos dado una
	 * lista de identificadores únicos<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idAgrupadorEspacioFisico
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	@GetMapping("/consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico")
	public List<AgrupadorEspacioFisicoOutDTO> consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(
			@RequestParam List<Long> idAgrupadorEspacioFisico) {
		return this.agrupadorEspacioFisicoRestMapper
				.toLstAgrupadorEspacioFisicoOutDTO(this.gestionarAgrupadorEspacioFisicoCUIntPort
						.consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(idAgrupadorEspacioFisico));
	}

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos dado una
	 * lista de identificadores únicos de facultades<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idFacultad
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	@GetMapping("/consultarAgrupadoresEspaciosFisicosPorIdFacultad")
	public List<AgrupadorEspacioFisicoOutDTO> consultarAgrupadoresEspaciosFisicosPorIdFacultad(
			@RequestParam List<Long> idFacultad) {
		return this.agrupadorEspacioFisicoRestMapper
				.toLstAgrupadorEspacioFisicoOutDTO(this.gestionarAgrupadorEspacioFisicoCUIntPort
						.consultarAgrupadoresEspaciosFisicosPorIdFacultad(idFacultad));
	}

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos asociados a
	 * un curso<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	@GetMapping("/consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso")
	public List<AgrupadorEspacioFisicoOutDTO> consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(
			@RequestParam Long idCurso) {
		return this.agrupadorEspacioFisicoRestMapper
				.toLstAgrupadorEspacioFisicoOutDTO(this.gestionarAgrupadorEspacioFisicoCUIntPort
						.consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(idCurso));
	}

	@PostMapping("/filtrarGrupos")
	public Page<AgrupadorEspacioFisicoOutDTO> filtrarGrupos(@RequestBody FiltroGrupoDTO filtro) {
		return this.gestionarAgrupadorEspacioFisicoCUIntPort.filtrarGrupos(filtro);
	}

	@PostMapping("/guardarGrupo")
	public AgrupadorEspacioFisicoOutDTO guardarGrupo(@RequestBody AgrupadorEspacioFisicoOutDTO agrupador) {
		return this.gestionarAgrupadorEspacioFisicoCUIntPort.guardarGrupo(agrupador);
	}
	
	@PostMapping("/guardarAsignacion")
	public MensajeOutDTO guardarAsignacion(@RequestBody AsignacionEspacioFisicoDTO asignacion) {
		return this.gestionarAgrupadorEspacioFisicoCUIntPort.guardarAsignacion(asignacion);
	}
}