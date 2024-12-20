package co.edu.unicauca.sgph.persona.infrastructure.input.controller;

import org.springframework.data.domain.Pageable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.persona.aplication.input.GestionarPersonaCUIntPort;
import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.infrastructure.input.DTORequest.PersonaInDTO;
import co.edu.unicauca.sgph.persona.infrastructure.input.DTOResponse.PersonaOutDTO;
import co.edu.unicauca.sgph.persona.infrastructure.input.mapper.PersonaRestMapper;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.TipoIdentificacionOutDTO;

@RestController
@RequestMapping("/AdministrarPersona")
public class PersonaController extends CommonEJB {

	private GestionarPersonaCUIntPort gestionarPersonaCUIntPort;
	private PersonaRestMapper personaRestMapper;

	public PersonaController(GestionarPersonaCUIntPort gestionarPersonaCUIntPort, PersonaRestMapper personaRestMapper) {
		this.gestionarPersonaCUIntPort = gestionarPersonaCUIntPort;
		this.personaRestMapper = personaRestMapper;
	}

	@PostMapping("/guardarPersona")
	public ResponseEntity<?> guardarPersona(@Valid @RequestBody PersonaInDTO personaInDTO, BindingResult result) {
	    Set<String> validaciones = new HashSet<>();
	    validaciones.add("ExisteEmail");
	    validaciones.add("ExistePersonaPorIdentificacion");

	    // Validaciones genéricas de campos
	    if (result.hasErrors()) {
	        return this.validarCampos(result, validaciones);
	    }

	    PersonaOutDTO personaOutDTO;

	    if (personaInDTO.getIdPersona() != null) {
	        // Lógica de edición
	        personaOutDTO = this.personaRestMapper.toPersonaOutDTO(
	                this.gestionarPersonaCUIntPort.editarPersona(this.personaRestMapper.toPersona(personaInDTO)));
	    } else {
	        // Lógica de creación
	        if (Boolean.FALSE.equals(personaInDTO.getEsValidar())) {
	            personaOutDTO = this.personaRestMapper.toPersonaOutDTO(
	                    this.gestionarPersonaCUIntPort.guardarPersona(this.personaRestMapper.toPersona(personaInDTO)));
	        } else {
	            // Retorna un valor indicando que no se ejecutará creación
	            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	        }
	    }

	    return new ResponseEntity<>(personaOutDTO, HttpStatus.OK);
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