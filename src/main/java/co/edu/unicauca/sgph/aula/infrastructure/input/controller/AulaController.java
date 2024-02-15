package co.edu.unicauca.sgph.aula.infrastructure.input.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.aula.aplication.input.GestionarAulaCUIntPort;
import co.edu.unicauca.sgph.aula.aplication.input.GestionarTipoAulaCUIntPort;
import co.edu.unicauca.sgph.aula.infrastructure.input.DTORequest.AulaInDTO;
import co.edu.unicauca.sgph.aula.infrastructure.input.DTORequest.FiltroAulaDTO;
import co.edu.unicauca.sgph.aula.infrastructure.input.DTOResponse.AulaDTO;
import co.edu.unicauca.sgph.aula.infrastructure.input.DTOResponse.AulaOutDTO;
import co.edu.unicauca.sgph.aula.infrastructure.input.DTOResponse.TipoAulaOutDTO;
import co.edu.unicauca.sgph.aula.infrastructure.input.mapper.AulaRestMapper;
import co.edu.unicauca.sgph.aula.infrastructure.input.mapper.TipoAulaRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarAula")
public class AulaController {

	private GestionarAulaCUIntPort gestionarAulaCUIntPort;
	private AulaRestMapper aulaRestMapper;
	private GestionarTipoAulaCUIntPort gestionarTipoAulaCUIntPort;
	private TipoAulaRestMapper tipoAulaRestMapper;

	public AulaController(GestionarAulaCUIntPort gestionarAulaCUIntPort, AulaRestMapper aulaRestMapper,
			GestionarTipoAulaCUIntPort gestionarTipoAulaCUIntPort, TipoAulaRestMapper tipoAulaRestMapper) {
		this.gestionarAulaCUIntPort = gestionarAulaCUIntPort;
		this.aulaRestMapper = aulaRestMapper;
		this.gestionarTipoAulaCUIntPort = gestionarTipoAulaCUIntPort;
		this.tipoAulaRestMapper = tipoAulaRestMapper;
	}

	@PostMapping("/guardarAula")
	public AulaOutDTO guardarAula(@RequestBody AulaInDTO aulaInDTO) {
		return this.aulaRestMapper
				.toCursoOutDTO(this.gestionarAulaCUIntPort.guardarAula(this.aulaRestMapper.toCurso(aulaInDTO)));
	}

	// Servicio utilizado en la pantalla de Gestionar Aulas
	@GetMapping("/consultarAulaPorIdAula")
	public AulaOutDTO consultarAulaPorIdAula(Long idAula) {
		return this.aulaRestMapper.toCursoOutDTO(this.gestionarAulaCUIntPort.consultarAulaPorIdAula(idAula));
	}

	/**
	 * Método encargado de consultar los tipos de aulas por facultad
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdFacultad
	 * @return
	 */
	@GetMapping("/consultarTipoAulasPorIdFacultad")
	public List<TipoAulaOutDTO> consultarTipoAulasPorIdFacultad(@RequestParam List<Long> lstIdFacultad) {
		return this.tipoAulaRestMapper
				.toLstTipoAulaOutDTO(this.gestionarTipoAulaCUIntPort.consultarTipoAulasPorIdFacultad(lstIdFacultad));
	}
	
	/**
	 * Método encargado de consultar las aulas por diferentes criterios de busqueda
	 * y retornarlos de manera paginada
	 * 
	 * 
	 * @param filtroAulaDTO DTO con los filtros de busqueda
	 * @return
	 */
	@PostMapping("/consultarAulas")
	public Page<AulaDTO> consultarAulas(@RequestBody FiltroAulaDTO filtroAulaDTO) {
		return this.gestionarAulaCUIntPort.consultarAulas(filtroAulaDTO);
	}

	/**
	 * Método encargado de consultar los tipo de aula asociados a una lista de
	 * edificios </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdEdificio
	 * @return
	 */
	@GetMapping("/consultarTipoAulasPorIdEdificio")
	public List<TipoAulaOutDTO> consultarTipoAulasPorIdEdificio(@RequestParam List<Long> lstIdEdificio) {
		return this.aulaRestMapper
				.toLstTipoAulaOutDTO(this.gestionarAulaCUIntPort.consultarTipoAulasPorIdEdificio(lstIdEdificio));
	}
	
}