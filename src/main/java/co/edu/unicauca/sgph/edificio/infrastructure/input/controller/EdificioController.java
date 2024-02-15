package co.edu.unicauca.sgph.edificio.infrastructure.input.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.edificio.aplication.input.GestionarEdificioCUIntPort;
import co.edu.unicauca.sgph.edificio.infrastructure.input.DTORequest.EdificioInDTO;
import co.edu.unicauca.sgph.edificio.infrastructure.input.DTOResponse.EdificioOutDTO;
import co.edu.unicauca.sgph.edificio.infrastructure.input.mapper.EdificioRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarEdificio")
public class EdificioController {

	private GestionarEdificioCUIntPort gestionarEdificioCUIntPort;
	private EdificioRestMapper edificioRestMapper;
	
	public EdificioController(GestionarEdificioCUIntPort gestionarEdificioCUIntPort,
			EdificioRestMapper edificioRestMapper) {
		this.gestionarEdificioCUIntPort = gestionarEdificioCUIntPort;
		this.edificioRestMapper = edificioRestMapper;
	}

	public EdificioOutDTO consultarEdificioPorNombre(String nombre) {
		return this.edificioRestMapper
				.toEdificioOutDTO(this.gestionarEdificioCUIntPort.consultarEdificioPorNombre(nombre));
	}

	public EdificioOutDTO guardarEdificio(EdificioInDTO edificioInDTO) {
		return this.edificioRestMapper.toEdificioOutDTO(
				this.gestionarEdificioCUIntPort.guardarEdificio(this.edificioRestMapper.toEdificio(edificioInDTO)));
	}
	
	/**
	 * Método encargado de consultar los edificios asociados a una lista de
	 * facultades </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdFacultad
	 * @return
	 */
	@GetMapping("/consultarEdificiosPorIdFacultad")
	public List<EdificioOutDTO> consultarEdificiosPorIdFacultad(@RequestParam List<Long> lstIdFacultad) {
		return this.edificioRestMapper
				.toLstEdificioOutDTO(this.gestionarEdificioCUIntPort.consultarEdificiosPorIdFacultad(lstIdFacultad));
	}
}
