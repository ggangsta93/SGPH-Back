package co.edu.unicauca.sgph.departamento.infrastructure.input.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.departamento.aplication.input.GestionarDepartamentoCUIntPort;
import co.edu.unicauca.sgph.departamento.infrastructure.input.DTORequest.DepartamentoInDTO;
import co.edu.unicauca.sgph.departamento.infrastructure.input.DTOResponse.DepartamentoOutDTO;
import co.edu.unicauca.sgph.departamento.infrastructure.input.mapper.DepartamentoRestMapper;

@RestController
@RequestMapping("/AdministrarDepartamento")
public class DepartamentoController extends CommonEJB {

	// Gestionadores
	private GestionarDepartamentoCUIntPort gestionarDepartamentoCUIntPort;
	// Mapers
	private DepartamentoRestMapper departamentoRestMapper;

	public DepartamentoController(GestionarDepartamentoCUIntPort gestionarDepartamentoCUIntPort,
			DepartamentoRestMapper departamentoRestMapper) {
		this.gestionarDepartamentoCUIntPort = gestionarDepartamentoCUIntPort;
		this.departamentoRestMapper = departamentoRestMapper;
	}

	public DepartamentoOutDTO guardarDepartamento(@RequestBody DepartamentoInDTO departamentoInDTO) {
		return this.departamentoRestMapper.toDepartamentoOutDTO(this.gestionarDepartamentoCUIntPort
				.guardarDepartamento(this.departamentoRestMapper.toDepartamento(departamentoInDTO)));
	}

	/**
	 * Método encargado de consultar todos los departamentos<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	@GetMapping("/consultarDepartamentos")
	public List<DepartamentoOutDTO> consultarDepartamentos() {
		return this.departamentoRestMapper
				.toLstDepartamentoOutDTO(this.gestionarDepartamentoCUIntPort.consultarDepartamentos());
	}
}