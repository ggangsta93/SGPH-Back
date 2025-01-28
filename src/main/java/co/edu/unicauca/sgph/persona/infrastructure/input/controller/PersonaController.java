package co.edu.unicauca.sgph.persona.infrastructure.input.controller;

import org.springframework.data.domain.Pageable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.espaciofisico.infrastructura.input.validation.ValidationGroups;
import co.edu.unicauca.sgph.persona.aplication.input.GestionarPersonaCUIntPort;
import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.infrastructure.input.DTORequest.PersonaInDTO;
import co.edu.unicauca.sgph.persona.infrastructure.input.DTOResponse.PersonaOutDTO;
import co.edu.unicauca.sgph.persona.infrastructure.input.mapper.PersonaRestMapper;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.TipoIdentificacionOutDTO;

@RestController
@RequestMapping("/AdministrarPersona")
public class PersonaController extends CommonEJB {

	@Autowired
	private Validator validator;
	
	private GestionarPersonaCUIntPort gestionarPersonaCUIntPort;
	private PersonaRestMapper personaRestMapper;

	public PersonaController(GestionarPersonaCUIntPort gestionarPersonaCUIntPort, PersonaRestMapper personaRestMapper) {
		this.gestionarPersonaCUIntPort = gestionarPersonaCUIntPort;
		this.personaRestMapper = personaRestMapper;
	}

	@PostMapping("/guardarPersona")
	public ResponseEntity<?> guardarPersona(@RequestBody PersonaInDTO personaInDTO, BindingResult result) {
		Class<?> validationGroup = (personaInDTO.getIdPersona() == null) 
                ? ValidationGroups.OnCreate.class 
                : ValidationGroups.OnUpdate.class;

		// Realizar la validación con el grupo correspondiente
		Set<ConstraintViolation<PersonaInDTO>> violations = validator.validate(personaInDTO, validationGroup);
		
		// Convertir las violaciones en errores del BindingResult
		for (ConstraintViolation<PersonaInDTO> violation : violations) {
			String fieldName = violation.getPropertyPath().toString();
			String errorMessage = violation.getMessage();
			result.addError(new FieldError("personaInDTO", fieldName, errorMessage));
		}
		
		// Si hay errores, retornar los mensajes de error
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}

	    
	    if (Boolean.FALSE.equals(personaInDTO.getEsValidar())) {
	    	PersonaOutDTO personaOutDTO = this.personaRestMapper.toPersonaOutDTO(
	        this.gestionarPersonaCUIntPort.guardarPersona(this.personaRestMapper.toPersona(personaInDTO)));
	    		return new ResponseEntity<>(personaOutDTO, HttpStatus.OK);
	        
	    } else {
	        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	    }
	}


	/**
	 * Método encargado de consultar una persona con el tipo y número de identificación<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	@GetMapping("/consultarPersonaPorIdentificacion")
	public PersonaOutDTO consultarPersonaPorIdentificacion(@RequestParam Long idTipoIdentificacion, @RequestParam String numeroIdentificacion) {		
		Persona persona = this.gestionarPersonaCUIntPort.consultarPersonaPorIdentificacion(idTipoIdentificacion, numeroIdentificacion);		
		return this.personaRestMapper.toPersonaOutDTO(persona);
	}
	
	/**
	 * Método encargado de consultar todos los tipos de identificación de
	 * persona<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	@GetMapping("/consultarTiposIdentificacion")
	public List<TipoIdentificacionOutDTO> consultarTiposIdentificacion() {
		return this.personaRestMapper
				.toLstTipoIdentificacionOutDTO(this.gestionarPersonaCUIntPort.consultarTiposIdentificacion());
	}
	/**
	 * Método encargado de consultar una persona con el correo<br>
	 *
	 * @author apedro
	 *
	 * @return
	 */
	@GetMapping("/consultarPersonaPorEmail")
	public PersonaOutDTO consultarPersonaPorEmail(@RequestParam String email) {
		Persona persona = this.gestionarPersonaCUIntPort.consultarPersonaPorEmail(email);
		return this.personaRestMapper.toPersonaOutDTO(persona);
	}
	
	@GetMapping("/consultarPersonasPaginadas")
	public ResponseEntity<Page<Persona>> consultarPersonasPaginadas(
	    @RequestParam int page,
	    @RequestParam int size,
	    @RequestParam String sortBy,
	    @RequestParam String sortOrder
	) {
	    Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
	    Page<Persona> personas = gestionarPersonaCUIntPort.consultarPersonasPaginadas(pageable);
	    return ResponseEntity.ok(personas);
	}

	 @GetMapping("/obtenerPersona/{idPersona}")
	 public ResponseEntity<Persona> obtenerPersonaPorId(@PathVariable Long idPersona) {
		 Persona persona = gestionarPersonaCUIntPort.obtenerPersonaPorId(idPersona);
	     return ResponseEntity.ok(persona);
	 }
	 
	 @DeleteMapping("/eliminarPersona/{idPersona}")
	 public ResponseEntity<?> eliminarPersona(@PathVariable Long idPersona) {
	     try {
	         this.gestionarPersonaCUIntPort.eliminarPersona(idPersona);
	         return ResponseEntity.noContent().build(); // 204 No Content
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la persona");
	     }
	 }
	
}