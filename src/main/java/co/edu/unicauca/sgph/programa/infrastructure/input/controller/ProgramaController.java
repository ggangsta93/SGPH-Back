package co.edu.unicauca.sgph.programa.infrastructure.input.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.programa.aplication.input.GestionarProgramaCUIntPort;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTORequest.ProgramaInDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTOResponse.ProgramaOutDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.mapper.ProgramaRestMapper;

@RestController
@RequestMapping("/AdministrarPrograma")
public class ProgramaController extends CommonEJB {

	// Gestionadores
	private GestionarProgramaCUIntPort gestionarProgramaCUIntPort;
	// Mapers
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

	public ProgramaOutDTO guardarPrograma(@RequestBody ProgramaInDTO programaInDTO) {
		return this.programaRestMapper.toProgramaOutDTO(
				this.gestionarProgramaCUIntPort.guardarPrograma(this.programaRestMapper.toPrograma(programaInDTO)));
	}

	/**
	 * Método encargado de consultar los programas asociados a una lista de
	 * facultades <br>
	 * 
	 * @author apedro
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
	 * @author apedro
	 * 
	 * @return
	 */
	@GetMapping("/consultarProgramas")
	public List<ProgramaOutDTO> consultarProgramas() {		
		return this.programaRestMapper.toLstProgramaOutDTO(this.gestionarProgramaCUIntPort.consultarProgramas());
	}
	
	/**
	 * Método encargado de consultar los programas permitidos para el usuario que se
	 * encuentra logueado<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	@GetMapping("/consultarProgramasPermitidosPorUsuario")
	public List<ProgramaOutDTO> consultarProgramasPermitidosPorUsuario() {
		return this.programaRestMapper
				.toLstProgramaOutDTO(this.gestionarProgramaCUIntPort.consultarProgramasPermitidosPorUsuario());
	}

}