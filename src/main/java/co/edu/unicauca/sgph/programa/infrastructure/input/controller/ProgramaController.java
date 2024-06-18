package co.edu.unicauca.sgph.programa.infrastructure.input.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.programa.aplication.input.GestionarDepartamentoCUIntPort;
import co.edu.unicauca.sgph.programa.aplication.input.GestionarProgramaCUIntPort;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTORequest.DepartamentoInDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTORequest.ProgramaInDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTOResponse.DepartamentoOutDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTOResponse.ProgramaOutDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.mapper.DepartamentoRestMapper;
import co.edu.unicauca.sgph.programa.infrastructure.input.mapper.ProgramaRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarPrograma")
public class ProgramaController {

	// Gestionadores
	private GestionarProgramaCUIntPort gestionarProgramaCUIntPort;
	private GestionarDepartamentoCUIntPort gestionarDepartamentoCUIntPort;
	// Mapers
	private ProgramaRestMapper programaRestMapper;
	private DepartamentoRestMapper departamentoRestMapper;

	public ProgramaController(GestionarProgramaCUIntPort gestionarProgramaCUIntPort,
			ProgramaRestMapper programaRestMapper, GestionarDepartamentoCUIntPort gestionarDepartamentoCUIntPort,
			DepartamentoRestMapper departamentoRestMapper) {
		this.gestionarProgramaCUIntPort = gestionarProgramaCUIntPort;
		this.programaRestMapper = programaRestMapper;
		this.gestionarDepartamentoCUIntPort = gestionarDepartamentoCUIntPort;
		this.departamentoRestMapper = departamentoRestMapper;
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

	/**
	 * Método encargado de consultar guardar y/o actualizar un departamento<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	@PostMapping("/guardarDepartamento")
	public DepartamentoOutDTO guardarDepartamento(@RequestBody DepartamentoInDTO departamentoInDTO) {
		return this.departamentoRestMapper.toDepartamentoOutDTO(this.gestionarDepartamentoCUIntPort
				.guardarDepartamento(this.departamentoRestMapper.toDepartamento(departamentoInDTO)));
	}

	/**
	 * Método encargado de consultar todos los departamentos<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	@GetMapping("/consultarDepartamentos")
	public List<DepartamentoOutDTO> consultarDepartamentos() {
		return this.departamentoRestMapper
				.toLstDepartamentoOutDTO(this.gestionarDepartamentoCUIntPort.consultarDepartamentos());
	}
}