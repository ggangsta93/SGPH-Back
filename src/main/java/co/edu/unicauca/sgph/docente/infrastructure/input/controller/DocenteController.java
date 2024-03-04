package co.edu.unicauca.sgph.docente.infrastructure.input.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteInDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.mapper.DocenteRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarDocente")
public class DocenteController {

	//private static final Logger log = LoggerFactory.getLogger(DocenteController.class);

	private GestionarDocenteCUIntPort gestionarDocenteCUIntPort;
	private DocenteRestMapper docenteRestMapper;

	public DocenteController(GestionarDocenteCUIntPort gestionarDocenteCUIntPort, DocenteRestMapper docenteRestMapper) {
		this.gestionarDocenteCUIntPort = gestionarDocenteCUIntPort;
		this.docenteRestMapper = docenteRestMapper;
	}

	@PostMapping("/guardarDocente")
	public DocenteOutDTO guardarDocente(@RequestBody DocenteInDTO docenteInDTO) {
		return this.docenteRestMapper.toDocenteOutDTO(
				this.gestionarDocenteCUIntPort.guardarDocente(this.docenteRestMapper.toDocente(docenteInDTO)));
	}

	@GetMapping("/consultarDocentePorIdentificacion")
	public DocenteOutDTO consultarDocentePorIdentificacion(@RequestParam Long idTipoIdentificacion,
			@RequestParam String numeroIdentificacion) {
		return this.docenteRestMapper.toDocenteOutDTO(this.gestionarDocenteCUIntPort
				.consultarDocentePorIdentificacion(idTipoIdentificacion, numeroIdentificacion));
	}	

	@Deprecated
	@GetMapping("/consultarDocentePorIdPersona")
	public DocenteOutDTO consultarDocentePorIdPersona(@RequestParam Long idPersona) {
		return this.docenteRestMapper
				.toDocenteOutDTO(this.gestionarDocenteCUIntPort.consultarDocentePorIdPersona(idPersona));
	}
	
	@GetMapping("/consultarNombresDocentesPorIdCurso")
	public List<String> consultarNombresDocentesPorIdCurso(@RequestParam Long idCurso) {
		return this.gestionarDocenteCUIntPort.consultarNombresDocentesPorIdCurso(idCurso);
	}
	
	/**
	 * Método encargado de consultar los docentes por diferentes criterios de busqueda
	 * y retornarlos de manera paginada
	 * 
	 * 
	 * @param filtroDocenteDTO DTO con los filtros de busqueda
	 * @return
	 */
	@PostMapping("/consultarDocentes")
	public Page<DocenteOutDTO> consultarDocentes(@RequestBody FiltroDocenteDTO filtroDocenteDTO) {
		return this.gestionarDocenteCUIntPort.consultarDocentes(filtroDocenteDTO);
	}
	
	/**
	 * Método encargado de obtener los docentes asociadas a un curso.
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @return
	 */
	@GetMapping("/consultarDocentePorIdCurso")
	public List<DocenteOutDTO> consultarDocentePorIdCurso(@RequestParam Long idCurso) {
		return this.docenteRestMapper
				.toLstDocenteOutDTO(this.gestionarDocenteCUIntPort.consultarDocentePorIdCurso(idCurso));
	}
}