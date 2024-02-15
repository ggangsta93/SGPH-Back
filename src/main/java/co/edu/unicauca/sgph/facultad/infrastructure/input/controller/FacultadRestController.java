package co.edu.unicauca.sgph.facultad.infrastructure.input.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.facultad.aplication.input.GestionarFacultadCUIntPort;
import co.edu.unicauca.sgph.facultad.domain.model.Facultad;
import co.edu.unicauca.sgph.facultad.infrastructure.input.DTORequest.FacultadInDTO;
import co.edu.unicauca.sgph.facultad.infrastructure.input.DTOResponse.FacultadOutDTO;
import co.edu.unicauca.sgph.facultad.infrastructure.input.mapper.FacultadRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarFacultad")
//@RequiredArgsConstructor  TODO: Lombok no está funcionando att:javier arias
public class FacultadRestController {

	private GestionarFacultadCUIntPort gestionarFacultadCUIntPort;
	private FacultadRestMapper facultadRestMapper;

	public FacultadRestController(GestionarFacultadCUIntPort gestionarFacultadCUIntPort,
			FacultadRestMapper facultadRestMapper) {
		this.gestionarFacultadCUIntPort = gestionarFacultadCUIntPort;
		this.facultadRestMapper = facultadRestMapper;
	}

	@GetMapping("/consultarFacultadPorNombre")
	public FacultadOutDTO consultarFacultadPorNombre(@RequestParam String nombre) {
		return this.facultadRestMapper
				.toFacultadOutDTO(gestionarFacultadCUIntPort.consultarFacultadPorNombre(nombre));
	}

	@PostMapping("/guardarFacultad")
	public FacultadOutDTO guardarFacultad(@RequestBody FacultadInDTO facultadInDTO) {
		Facultad facultad = facultadRestMapper.toFacultad(facultadInDTO);
		return this.facultadRestMapper.toFacultadOutDTO(this.gestionarFacultadCUIntPort.guardarFacultad(facultad));
	}

	/**
	 * Método encargado de consultar todas las facultades
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	@GetMapping("/consultarFacultades")
	public List<FacultadOutDTO> consultarFacultades() {
		return this.facultadRestMapper.toLstFacultadOutDTO(this.gestionarFacultadCUIntPort.consultarFacultades());
	}
}
