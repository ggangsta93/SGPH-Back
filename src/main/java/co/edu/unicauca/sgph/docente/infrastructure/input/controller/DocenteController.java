package co.edu.unicauca.sgph.docente.infrastructure.input.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
		validaciones.add("ExistsByCodigo");
		validaciones.add("ExistsByEmail");
		validaciones.add("ExistsByTipoAndNumeroIdentificacion");
		
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

	/**
	 * Método encargado de manejar la validación de errores en las peticiones.<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param result El resultado de la validación.
	 * @param codes  Codigos personalizados
	 * @return ResponseEntity con los errores de validación.
	 * 
	 */
	private ResponseEntity<?> validacion(BindingResult result, Set<String> codes) {
		Map<String, String> errores = new HashMap<>();
		// Se validan restricciones de campos
		result.getAllErrors().forEach(error -> {
			if (codes.contains(error.getCode())) {
				errores.put(error.getCode(), error.getDefaultMessage());
			}
		});
		// Se validan restricciones de campos
		result.getFieldErrors().forEach(error -> {
			errores.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
		});
		return ResponseEntity.accepted().body(errores);
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co> *
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