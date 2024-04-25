package co.edu.unicauca.sgph.asignatura.infrastructure.input.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.mapper.AsignaturaRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarAsignatura")
public class AsignaturaController {

	private GestionarAsignaturaCUIntPort gestionarAsignaturaCUIntPort;
	private AsignaturaRestMapper asignaturaRestMapper;

	public AsignaturaController(GestionarAsignaturaCUIntPort gestionarAsignaturaCUIntPort,
			AsignaturaRestMapper asignaturaRestMapper) {
		this.gestionarAsignaturaCUIntPort = gestionarAsignaturaCUIntPort;
		this.asignaturaRestMapper = asignaturaRestMapper;
	}

	/**
	 * Método encargado de guardar o actualizar una asignatura <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param asignaturaInDTO
	 * @return
	 */
	@PostMapping("/guardarAsignatura")
	public AsignaturaOutDTO guardarAsignatura(@RequestBody AsignaturaInDTO asignaturaInDTO) {
		return this.asignaturaRestMapper.toAsignaturaOutDTO(this.gestionarAsignaturaCUIntPort
				.guardarAsignatura(this.asignaturaRestMapper.toAsignatura(asignaturaInDTO)));
	}

	/**
	 * Método encargado de consultar las asignaturas por programa <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idPrograma
	 * @return
	 */
	@GetMapping("/consultarAsignaturasPorIdPrograma")
	public List<AsignaturaOutDTO> consultarAsignaturasPorIdPrograma(@RequestParam Long idPrograma) {
		List<Asignatura> asignaturas = this.gestionarAsignaturaCUIntPort.consultarAsignaturasPorIdPrograma(idPrograma);
		return this.asignaturaRestMapper.toLstAsignaturaOutDTO(asignaturas);
	}
	
	/**
	 * Método encargado de obtener las asignaturas dado un conjunto de criterios de
	 * busqueda.
	 * 
	 * @author Julieth Fernanda Hurtado Sanchez <juliethhs@unicauca.edu.co>
	 * 
	 * @param filtroAsignaturaDTO
	 * @return
	 */	
	@PostMapping("/consultarAsignaturasPorFiltro")
	public Page<AsignaturaOutDTO> consultarAsignaturasPorFiltro(
			@RequestBody FiltroAsignaturaDTO filtroAsignaturaDTO){
		return this.gestionarAsignaturaCUIntPort
				.consultarAsignaturasPorFiltro(filtroAsignaturaDTO);
	}
}
