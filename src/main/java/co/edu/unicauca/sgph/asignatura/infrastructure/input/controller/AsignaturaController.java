package co.edu.unicauca.sgph.asignatura.infrastructure.input.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.mapper.AsignaturaRestMapper;
import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.EstadoAsignaturaEnum;
import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;

@RestController
@RequestMapping("/AdministrarAsignatura")
public class AsignaturaController extends CommonEJB {

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
	 * @author apedro
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
	 * Método encargado de consultar las asignaturas activas por programa<br>
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma
	 * @return
	 */
	@GetMapping("/consultarAsignaturasActivasPorIdPrograma")
	public List<AsignaturaOutDTO> consultarAsignaturasActivasPorIdPrograma(@RequestParam Long idPrograma) {
		List<Asignatura> asignaturas = this.gestionarAsignaturaCUIntPort
				.consultarAsignaturasPorIdProgramaYEstado(idPrograma, EstadoAsignaturaEnum.ACTIVO);
		return this.asignaturaRestMapper.toLstAsignaturaOutDTO(asignaturas);
	}
	
	/**
	 * Método encargado de consultar las asignaturas de los cursos del periodo
	 * vigente para un programa<br>
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma
	 * @return
	 */
	@GetMapping("/consultarAsignaturasDeLosCursosPorIdPrograma")
	public List<AsignaturaOutDTO> consultarAsignaturasDeLosCursosPorIdPrograma(@RequestParam Long idPrograma) {
		List<Asignatura> asignaturas = this.gestionarAsignaturaCUIntPort
				.consultarAsignaturasDeLosCursosPorIdPrograma(idPrograma);
		return this.asignaturaRestMapper.toLstAsignaturaOutDTO(asignaturas);
	}

	@GetMapping("/consultarAsignaturaPorId")
	public AsignaturaOutDTO obtenerAsignaturaPorId(@RequestParam Long idAsignatura) {
		return this.gestionarAsignaturaCUIntPort.obtenerAsignaturaPorId(idAsignatura);
	}
	@PostMapping("filtrarAsignaturas")
	public Page<AsignaturaOutDTO> filtrarAsignaturas(@RequestBody FiltroAsignaturaInDTO filtro) {
		return this.gestionarAsignaturaCUIntPort.filtrarAsignaturas(filtro);
	}
	@GetMapping("inactivarAsignaturaPorId/{idAsignatura}")
	private AsignaturaOutDTO inactivarAsignaturaPorId(@PathVariable Long idAsignatura) {
		Asignatura asignatura = this.gestionarAsignaturaCUIntPort.inactivarAsignaturaPorId(idAsignatura);
		if (asignatura != null) {
			return this.asignaturaRestMapper.toAsignaturaOutDTO(asignatura);
		}
		AsignaturaOutDTO asignaturaOutDTO = new AsignaturaOutDTO();
		asignaturaOutDTO.setError(true);
		return asignaturaOutDTO;
	}
	@PostMapping("cargaMasiva")
	private MensajeOutDTO cargaMasivaAsignaturas(@RequestBody AsignaturaInDTO asignatura) {
		return this.gestionarAsignaturaCUIntPort.cargaMasivaAsignaturas(asignatura);
	}
}
