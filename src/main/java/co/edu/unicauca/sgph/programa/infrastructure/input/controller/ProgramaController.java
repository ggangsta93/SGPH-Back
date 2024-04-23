package co.edu.unicauca.sgph.programa.infrastructure.input.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.programa.aplication.input.GestionarProgramaCUIntPort;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTORequest.ProgramaInDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTOResponse.ProgramaOutDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.mapper.ProgramaRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarPrograma")
public class ProgramaController {

	private GestionarProgramaCUIntPort gestionarProgramaCUIntPort;
	private ProgramaRestMapper programaRestMapper;

	public ProgramaController(GestionarProgramaCUIntPort gestionarProgramaCUIntPort,
			ProgramaRestMapper programaRestMapper) {
		this.gestionarProgramaCUIntPort = gestionarProgramaCUIntPort;
		this.programaRestMapper = programaRestMapper;
	}

	public ProgramaOutDTO consultarProgramaPorNombre(String nombre) {
		return this.programaRestMapper
				.toProgramaOutDTO(this.gestionarProgramaCUIntPort.consultarProgramaPorNombre(nombre));
	}

	public ProgramaOutDTO guardarPrograma(ProgramaInDTO programaInDTO) {
		return this.programaRestMapper.toProgramaOutDTO(
				this.gestionarProgramaCUIntPort.guardarPrograma(this.programaRestMapper.toPrograma(programaInDTO)));
	}

	/**
	 * Método encargado de consultar los programas asociados a una lista de
	 * facultades <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdFacultad
	 * @return
	 */
	@GetMapping("/consultarProgramasPorIdFacultad")
	public List<ProgramaOutDTO> consultarProgramasPorIdFacultad(@RequestParam List<Long> lstIdFacultad) {
		return this.programaRestMapper
				.toLstProgramaOutDTO(this.gestionarProgramaCUIntPort.consultarProgramasPorIdFacultad(lstIdFacultad));
	}

	/**
	 * Método encargado de consultar todos los programas<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	@GetMapping("/consultarProgramas")
	public List<ProgramaOutDTO> consultarProgramas() {
		return this.programaRestMapper.toLstProgramaOutDTO(this.gestionarProgramaCUIntPort.consultarProgramas());
	}
}
