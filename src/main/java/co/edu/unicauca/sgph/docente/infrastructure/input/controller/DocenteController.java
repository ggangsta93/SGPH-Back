package co.edu.unicauca.sgph.docente.infrastructure.input.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteInDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteLaborDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.mapper.DocenteRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
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
			return validarCampos(result, validaciones);
		}
		
		if (Boolean.FALSE.equals(docenteInDTO.getEsValidar())) {
			DocenteOutDTO docenteOutDTO =  this.docenteRestMapper.toDocenteOutDTO(
					this.gestionarDocenteCUIntPort.guardarDocente(this.docenteRestMapper.toDocente(docenteInDTO)));

			if (Objects.equals(docenteOutDTO.getIdPersona(), docenteOutDTO.getIdPersona())) {
				return new ResponseEntity<DocenteOutDTO>(docenteOutDTO, HttpStatus.OK);
			} else {
				return new ResponseEntity<DocenteOutDTO>(docenteOutDTO, HttpStatus.OK);
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

	@Autowired
	private Validator validator;
	
	@PostMapping("/importar/{idFacultad}")
	public ResponseEntity<?> importarLaborDocente(@RequestBody List<DocenteLaborDTO> docenteLaborDTOList, @PathVariable Long idFacultad, BindingResult result) {
	    // Verificar si hay errores de validación en la lista de DocenteLaborDTO
		List<String> erroresConContexto = new ArrayList<>();

	    // Validar cada objeto DocenteLaborDTO en la lista
	    for (int i = 0; i < docenteLaborDTOList.size(); i++) {
	        DocenteLaborDTO dto = docenteLaborDTOList.get(i);
	        Set<ConstraintViolation<DocenteLaborDTO>> violaciones = validator.validate(dto);

	        if (!violaciones.isEmpty()) {
	            for (ConstraintViolation<DocenteLaborDTO> violacion : violaciones) {
	                String mensajeError = String.format(
	                    "Error en el registro %d (Asignatura: %s, Grupo: %s, Identificación: %s): %s - %s",
	                    i + 1,
	                    dto.getNombreMateria() != null ? dto.getNombreMateria() : "N/A",
	                    dto.getGrupo() != null ? dto.getGrupo() : "N/A",
	                    dto.getIdentificacion() != null ? dto.getIdentificacion() : "N/A",
	                    violacion.getPropertyPath(),
	                    violacion.getMessage()
	                );
	                erroresConContexto.add(mensajeError);
	            }
	        }
	    }

	    // Si hay errores de validación, devolver la respuesta con los errores
	    if (!erroresConContexto.isEmpty()) {
	        return ResponseEntity.badRequest().body(erroresConContexto);
	    }

	    try {
	        // Procesar la lista validada y llamar al servicio
	        List<String> mensajes = this.gestionarDocenteCUIntPort.procesarLaborDocenteDesdeJson(docenteLaborDTOList, idFacultad);
	        return ResponseEntity.ok(mensajes);

	    } catch (ConstraintViolationException ex) {
	        StringBuilder errorMessage = new StringBuilder("Errores de validación: ");
	        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	            errorMessage.append(String.format("%s: %s; ", violation.getPropertyPath(), violation.getMessage()));
	        }
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage.toString());
	    } catch (DataIntegrityViolationException ex) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Error de integridad de datos: " + ex.getMostSpecificCause().getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar los datos: " + e.getMessage());
	    }
	}
}