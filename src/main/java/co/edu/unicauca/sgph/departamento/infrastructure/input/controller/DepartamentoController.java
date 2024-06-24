package co.edu.unicauca.sgph.departamento.infrastructure.input.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.departamento.aplication.input.GestionarDepartamentoCUIntPort;
import co.edu.unicauca.sgph.departamento.infrastructure.input.DTORequest.DepartamentoInDTO;
import co.edu.unicauca.sgph.departamento.infrastructure.input.DTOResponse.DepartamentoOutDTO;
import co.edu.unicauca.sgph.departamento.infrastructure.input.mapper.DepartamentoRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarDepartamento")
public class DepartamentoController {

	// Gestionadores
	private GestionarDepartamentoCUIntPort gestionarDepartamentoCUIntPort;
	// Mapers
	private DepartamentoRestMapper departamentoRestMapper;

	public DepartamentoController(GestionarDepartamentoCUIntPort gestionarDepartamentoCUIntPort,
			DepartamentoRestMapper departamentoRestMapper) {
		this.gestionarDepartamentoCUIntPort = gestionarDepartamentoCUIntPort;
		this.departamentoRestMapper = departamentoRestMapper;
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