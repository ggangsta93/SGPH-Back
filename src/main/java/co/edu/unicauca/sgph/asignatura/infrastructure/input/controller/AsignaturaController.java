package co.edu.unicauca.sgph.asignatura.infrastructure.input.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;

import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.mapper.AsignaturaRestMapper;
import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
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
	public ResponseEntity<?> guardarAsignatura(@Valid @RequestBody AsignaturaInDTO asignaturaInDTO, BindingResult result) {
		Set<String> validaciones = new HashSet<String>();
		validaciones.add("ExisteCodigoAsignatura");
		validaciones.add("ExisteOidAsignatura");
		
		if (result.hasErrors()) {
			return validacion(result, validaciones);
		}
		
		if (Boolean.FALSE.equals(asignaturaInDTO.getEsValidar())) {
	        AsignaturaOutDTO asignaturaOutDTO = this.asignaturaRestMapper.toAsignaturaOutDTO(
	                this.gestionarAsignaturaCUIntPort.guardarAsignatura(
	                        this.asignaturaRestMapper.toAsignatura(asignaturaInDTO)
	                )
	        );

	        if (Objects.equals(asignaturaOutDTO.getIdAsignatura(), asignaturaOutDTO.getIdAsignatura())) {
	            return new ResponseEntity<>(asignaturaOutDTO, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(asignaturaOutDTO, HttpStatus.CREATED);
	        }
	    } else {
	        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	    }
	}

	/**
	 * Método encargado de consultar las asignaturas por programa <br>
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma
	 * @return
	 */
	@GetMapping("/consultarAsignaturasPorIdPrograma")
	public List<AsignaturaOutDTO> consultarAsignaturasPorIdPrograma(@RequestParam Long idPrograma) {
		List<Asignatura> asignaturas = this.gestionarAsignaturaCUIntPort.consultarAsignaturasPorIdPrograma(idPrograma);
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
