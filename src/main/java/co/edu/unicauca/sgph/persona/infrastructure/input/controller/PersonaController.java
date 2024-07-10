package co.edu.unicauca.sgph.persona.infrastructure.input.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;

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

import co.edu.unicauca.sgph.persona.aplication.input.GestionarPersonaCUIntPort;
import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.infrastructure.input.DTORequest.PersonaInDTO;
import co.edu.unicauca.sgph.persona.infrastructure.input.DTOResponse.PersonaOutDTO;
import co.edu.unicauca.sgph.persona.infrastructure.input.mapper.PersonaRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarPersona")
public class PersonaController {

	private GestionarPersonaCUIntPort gestionarPersonaCUIntPort;
	private PersonaRestMapper personaRestMapper;

	public PersonaController(GestionarPersonaCUIntPort gestionarPersonaCUIntPort, PersonaRestMapper personaRestMapper) {
		this.gestionarPersonaCUIntPort = gestionarPersonaCUIntPort;
		this.personaRestMapper = personaRestMapper;
	}

	@PostMapping("/guardarPersona")
	public ResponseEntity<?> guardarPersona(@Valid @RequestBody PersonaInDTO personaInDTO, BindingResult result) {
		Set<String> validaciones = new HashSet<String>();
		validaciones.add("ExisteEmail");
		validaciones.add("ExistePersonaPorIdentificacion");

		if (result.hasErrors()) {
			return validacion(result, validaciones);
		}

		if (Boolean.FALSE.equals(personaInDTO.getEsValidar())) {
			PersonaOutDTO personaOutDTO = this.personaRestMapper.toPersonaOutDTO(
					this.gestionarPersonaCUIntPort.guardarPersona(this.personaRestMapper.toPersona(personaInDTO)));

			if (Objects.equals(personaOutDTO.getIdPersona(), personaOutDTO.getIdPersona())) {
				return new ResponseEntity<PersonaOutDTO>(personaOutDTO, HttpStatus.OK);
			} else {
				return new ResponseEntity<PersonaOutDTO>(personaOutDTO, HttpStatus.CREATED);
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
	
	/**
	 * Método encargado de consultar una persona con el tipo y número de identificación<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	@GetMapping("/consultarPersonaPorIdentificacion")
	public PersonaOutDTO consultarPersonaPorIdentificacion(@RequestParam Long idTipoIdentificacion, @RequestParam String numeroIdentificacion) {		
		Persona persona = this.gestionarPersonaCUIntPort.consultarPersonaPorIdentificacion(idTipoIdentificacion, numeroIdentificacion);		
		return this.personaRestMapper.toPersonaOutDTO(persona);
	}
}