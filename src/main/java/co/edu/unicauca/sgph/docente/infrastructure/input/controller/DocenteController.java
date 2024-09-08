package co.edu.unicauca.sgph.docente.infrastructure.input.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteInDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.mapper.DocenteRestMapper;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;

@RestController
@RequestMapping("/AdministrarDocente")
public class DocenteController extends CommonEJB{

	// private static final Logger log =
	// LoggerFactory.getLogger(DocenteController.class);

	private GestionarDocenteCUIntPort gestionarDocenteCUIntPort;
	private DocenteRestMapper docenteRestMapper;

	public DocenteController(GestionarDocenteCUIntPort gestionarDocenteCUIntPort, DocenteRestMapper docenteRestMapper) {
		this.gestionarDocenteCUIntPort = gestionarDocenteCUIntPort;
		this.docenteRestMapper = docenteRestMapper;
	}

	@PostMapping("/guardarDocente")
	public ResponseEntity<?> guardarDocente(@Valid @RequestBody DocenteInDTO docenteInDTO, BindingResult result) {
		Set<String> validaciones = new HashSet<String>();
		validaciones.add("ExisteCodigoDocente");
		validaciones.add("ExisteIdPersonaDocente");
		
		if (result.hasErrors()) {
			return validacion(result, validaciones);
		}
		
		if (Boolean.FALSE.equals(docenteInDTO.getEsValidar())) {
			DocenteOutDTO docenteOutDTO =  this.docenteRestMapper.toDocenteOutDTO(
					this.gestionarDocenteCUIntPort.guardarDocente(this.docenteRestMapper.toDocente(docenteInDTO)));

			if (Objects.equals(docenteOutDTO.getIdPersona(), docenteOutDTO.getIdPersona())) {
				return new ResponseEntity<DocenteOutDTO>(docenteOutDTO, HttpStatus.OK);
			} else {
				return new ResponseEntity<DocenteOutDTO>(docenteOutDTO, HttpStatus.CREATED);
			}
		} else {
			return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
		}
		
	}

	@GetMapping("/consultarDocentePorIdentificacion")
	public DocenteOutDTO consultarDocentePorIdentificacion(@RequestParam Long idTipoIdentificacion,
			@RequestParam String numeroIdentificacion) {
		return this.docenteRestMapper.toDocenteOutDTO(this.gestionarDocenteCUIntPort
				.consultarDocentePorIdentificacion(idTipoIdentificacion, numeroIdentificacion));
	}

	/**
	 * Método encargado de consultar los nombres de docentes por curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */
	@GetMapping("/consultarNombresDocentesPorIdCurso")
	public List<String> consultarNombresDocentesPorIdCurso(@RequestParam Long idCurso) {
		return this.gestionarDocenteCUIntPort.consultarNombresDocentesPorIdCurso(idCurso);
	}

	/**
	 * Método encargado de consultar los docentes por diferentes criterios de
	 * busqueda y retornarlos de manera paginada
	 * 
	 * @author apedro
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
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */
	@GetMapping("/consultarDocentePorIdCurso")
	public List<DocenteOutDTO> consultarDocentePorIdCurso(@RequestParam Long idCurso) {
		return this.docenteRestMapper
				.toLstDocenteOutDTO(this.gestionarDocenteCUIntPort.consultarDocentePorIdCurso(idCurso));
	}

	/**
	 * Método que se ejecuta para cargar labor docente
	 *
	 *
	 * @author
	 *
	 *
	 *
	 */
	@PostMapping("/cargarLaborDocente")
	public MensajeOutDTO cargarLaborDocente(@RequestBody ReporteDocenteDTO archivoDocente) {
		return this.gestionarDocenteCUIntPort.cargarLaborDocente(archivoDocente);
	}
	@PostMapping("/consultaLaborDocente")
	public ReporteDocenteDTO consultaLaborDocente(@RequestBody ReporteDocenteDTO filtro) {
		return this.gestionarDocenteCUIntPort.consultaLaborDocente(filtro);
	}

}