package co.edu.unicauca.sgph.persona.infrastructure.input.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;

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
		Set<String> validaciones = new HashSet<String>();
		validaciones.add("ExisteEmail");
		validaciones.add("ExistePersonaPorIdentificacion");

		if (result.hasErrors()) {
			return this.validarCampos(result, validaciones);
		}

		if (Boolean.FALSE.equals(personaInDTO.getEsValidar())) {
			PersonaOutDTO personaOutDTO = this.personaRestMapper.toPersonaOutDTO(
					this.gestionarPersonaCUIntPort.guardarPersona(this.personaRestMapper.toPersona(personaInDTO)));

			if (Objects.equals(personaOutDTO.getIdPersona(), personaOutDTO.getIdPersona())) {
				return new ResponseEntity<PersonaOutDTO>(personaOutDTO, HttpStatus.OK);
			} else {
				return new ResponseEntity<PersonaOutDTO>(personaOutDTO, HttpStatus.OK);
			}
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
}