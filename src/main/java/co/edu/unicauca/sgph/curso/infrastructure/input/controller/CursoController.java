package co.edu.unicauca.sgph.curso.infrastructure.input.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.curso.infrastructure.input.DTORequest.CursoInDTO;
import co.edu.unicauca.sgph.curso.infrastructure.input.DTOResponse.CursoOutDTO;
import co.edu.unicauca.sgph.curso.infrastructure.input.mapper.CursoRestMapper;
import co.edu.unicauca.sgph.espaciofisico.infrastructura.input.validation.ValidationGroups;
import co.edu.unicauca.sgph.periodoacademico.aplication.input.GestionarPeriodoAcademicoCUIntPort;

@RestController
@RequestMapping("/AdministrarCurso")
public class CursoController extends CommonEJB {

	@Autowired
	 private Validator validator;
	
	private GestionarCursoCUIntPort gestionarCursoCUIntPort;
	@Autowired
	private GestionarPeriodoAcademicoCUIntPort gestionarPeriodoAcademicoCUIntPort;
	private CursoRestMapper cursoRestMapper;
	
	@Autowired
    private MessageSource messageSource;

	public CursoController(GestionarCursoCUIntPort gestionarCursoCUIntPort, CursoRestMapper cursoRestMapper) {
		this.gestionarCursoCUIntPort = gestionarCursoCUIntPort;
		this.cursoRestMapper = cursoRestMapper;
	}

	/**
	 * Método encargado de guardar o actualizar un curso <br>
	 * 
	 * @author jhurtado
	 * 
	 * @param cursoInDTO
	 * @return
	 */
	@PostMapping("/guardarCurso")
	public ResponseEntity<?> guardarCurso(@RequestBody CursoInDTO cursoInDTO, BindingResult result) {
		Class<?> validationGroup = (cursoInDTO.getIdCurso() == null) 
                ? ValidationGroups.OnCreate.class 
                : ValidationGroups.OnUpdate.class;

		// Realizar la validación con el grupo correspondiente
		Set<ConstraintViolation<CursoInDTO>> violations = validator.validate(cursoInDTO, validationGroup);
		
		// Convertir las violaciones en errores del BindingResult
		for (ConstraintViolation<CursoInDTO> violation : violations) {
			String fieldName = violation.getPropertyPath().toString();
			String errorMessage = violation.getMessage();
			result.addError(new FieldError("cursoInDTO", fieldName, errorMessage));
		}
		
		// Si hay errores, retornar los mensajes de error
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		
		if (Boolean.FALSE.equals(cursoInDTO.getEsValidar())) {
			CursoOutDTO cursoOutDTO =  this.cursoRestMapper.toCursoOutDTO(
					this.gestionarCursoCUIntPort.guardarCurso(this.cursoRestMapper.toCurso(cursoInDTO)));
			cursoOutDTO.setIdPeriodoAcademico(this.gestionarPeriodoAcademicoCUIntPort.consultarPeriodoAcademicoVigente().getIdPeriodoAcademico());
			return new ResponseEntity<>(cursoOutDTO, HttpStatus.OK);
	        
	    } else {
	        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	    }
	}
	@GetMapping("/obtenerCurso/{id}")
	public CursoOutDTO obtenerCurso(@PathVariable Long id) {
		return this.cursoRestMapper
				.toCursoOutDTO(this.gestionarCursoCUIntPort.obtenerCurso(id));
	}
	
	/**
	 * Método encargado de consultar los agrupadores de espacios físicos asociados
	 * al curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param cursoInDTO
	 * @return Lista de idAgrupadorEspacioFisico
	 */
	@GetMapping("/consultarAgrupadoresEspaciosFisicosPorCurso")
	public List<Long> consultarAgrupadoresEspaciosFisicosPorCurso(@RequestParam Long idCurso) {
		return this.gestionarCursoCUIntPort.consultarAgrupadoresEspaciosFisicosPorCurso(idCurso);
	}

	/**
	 * Método encargado de consultar un curso por grupo y asignatura <br>
	 * 
	 * @author apedro
	 * 
	 * @param grupo
	 * @param idAsignatura
	 * @return
	 */
	@Deprecated
	public CursoOutDTO consultarCursoPorGrupoYAsignatura(String grupo, Long idAsignatura) {
		return this.cursoRestMapper
				.toCursoOutDTO(this.gestionarCursoCUIntPort.consultarCursoPorGrupoYAsignatura(grupo, idAsignatura));
	}
	@DeleteMapping("/eliminarCurso/{id}")
	public ResponseEntity<?> eliminarCurso(@PathVariable Long id, Locale locale) {
		Set<String> errores = new HashSet<>();

	   Curso curso = this.gestionarCursoCUIntPort.consultarCursoPorIdCurso(id);
	    if (curso.getDocentes().size() > 0) {
	    	String mensajeDocentes = messageSource.getMessage("curso.exists.by.docentes", null, locale);
	        errores.add(mensajeDocentes);
	    }
	    if (curso.getHorarios().size() > 0) {
	    	String mensajeHorarios = messageSource.getMessage("curso.exists.by.horarios", null, locale);
	        errores.add(mensajeHorarios);
	    }
	    if (!errores.isEmpty()) {
	        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
	    }

	    this.gestionarCursoCUIntPort.eliminarCurso(id);
	    return ResponseEntity.ok().build();
	}
}