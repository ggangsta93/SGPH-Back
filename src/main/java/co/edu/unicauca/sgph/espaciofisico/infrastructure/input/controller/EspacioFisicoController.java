package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarAgrupadorEspacioFisicoCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEspacioFisicoCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarTipoEspacioFisicoCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.EspacioFisicoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.TipoEspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper.AgrupadorEspacioFisicoRestMapper;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper.EspacioFisicoRestMapper;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper.TipoEspacioFisicoRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarEspacioFisico")
public class EspacioFisicoController {

	private GestionarEspacioFisicoCUIntPort gestionarEspacioFisicoCUIntPort;
	private EspacioFisicoRestMapper espacioFisicoRestMapper;
	private GestionarTipoEspacioFisicoCUIntPort gestionarTipoEspacioFisicoCUIntPort;
	private TipoEspacioFisicoRestMapper tipoEspacioFisicoRestMapper;
	private GestionarAgrupadorEspacioFisicoCUIntPort gestionarAgrupadorEspacioFisicoCUIntPort;
	private AgrupadorEspacioFisicoRestMapper agrupadorEspacioFisicoRestMapper;

	public EspacioFisicoController(GestionarEspacioFisicoCUIntPort gestionarEspacioFisicoCUIntPort,
			EspacioFisicoRestMapper espacioFisicoRestMapper,
			GestionarTipoEspacioFisicoCUIntPort gestionarTipoEspacioFisicoCUIntPort,
			TipoEspacioFisicoRestMapper tipoEspacioFisicoRestMapper,
			GestionarAgrupadorEspacioFisicoCUIntPort gestionarAgrupadorEspacioFisicoCUIntPort,
			AgrupadorEspacioFisicoRestMapper agrupadorEspacioFisicoRestMapper) {
		this.gestionarEspacioFisicoCUIntPort = gestionarEspacioFisicoCUIntPort;
		this.espacioFisicoRestMapper = espacioFisicoRestMapper;
		this.gestionarTipoEspacioFisicoCUIntPort = gestionarTipoEspacioFisicoCUIntPort;
		this.tipoEspacioFisicoRestMapper = tipoEspacioFisicoRestMapper;
		this.gestionarAgrupadorEspacioFisicoCUIntPort = gestionarAgrupadorEspacioFisicoCUIntPort;
		this.agrupadorEspacioFisicoRestMapper = agrupadorEspacioFisicoRestMapper;
	}

	@PostMapping("/guardarEspacioFisico")
	@Transactional
	public EspacioFisicoOutDTO guardarEspacioFisico(@RequestBody EspacioFisicoInDTO espacioFisicoInDTO) {
		EspacioFisico espacioFisico = this.gestionarEspacioFisicoCUIntPort
				.guardarEspacioFisico(this.espacioFisicoRestMapper.toEspacioFisico(espacioFisicoInDTO));

		return this.espacioFisicoRestMapper.toEspacioFisicoOutDTO(espacioFisico);
	}

	/**
	 * Método encargado de consultar un espacio físico por su identificador
	 * único<br>
	 * -Utilizado en la pantalla de Gestionar espacios físicos<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idEspacioFisico
	 * @return
	 */
	@GetMapping("/consultarEspacioFisicoPorIdEspacioFisico")
	public EspacioFisicoOutDTO consultarEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico) {
		return this.espacioFisicoRestMapper.toEspacioFisicoOutDTO(
				this.gestionarEspacioFisicoCUIntPort.consultarEspacioFisicoPorIdEspacioFisico(idEspacioFisico));
	}

	/**
	 * Método encargado de consultar los tipos de espacios físicos por facultad<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdFacultad
	 * @return
	 */
	@GetMapping("/consultarTiposEspaciosFisicosPorIdFacultad")
	public List<TipoEspacioFisicoOutDTO> consultarTiposEspaciosFisicosPorIdFacultad(
			@RequestParam List<Long> lstIdFacultad) {
		return this.tipoEspacioFisicoRestMapper.toLstTipoEspacioFisicoOutDTO(
				this.gestionarTipoEspacioFisicoCUIntPort.consultarTiposEspaciosFisicosPorIdFacultad(lstIdFacultad));
	}

	/**
	 * Método encargado de consultar los espacios físicos por diferentes criterios
	 * de busqueda y retornarlos de manera paginada<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param filtroEspacioFisicoDTO DTO con los filtros de busqueda
	 * @return
	 */
	@PostMapping("/consultarEspaciosFisicos")
	public Page<EspacioFisicoDTO> consultarEspaciosFisicos(@RequestBody FiltroEspacioFisicoDTO filtroEspacioFisicoDTO) {
		return this.gestionarEspacioFisicoCUIntPort.consultarEspaciosFisicos(filtroEspacioFisicoDTO);
	}

	/**
	 * Método encargado de consultar los tipos de espacios físicos asociados a una
	 * lista de edificios <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstEdificio
	 * @return
	 */
	@GetMapping("/consultarTiposEspaciosFisicosPorEdificio")
	public List<TipoEspacioFisicoOutDTO> consultarTiposEspaciosFisicosPorEdificio(
			@RequestParam List<String> lstEdificio) {
		return this.espacioFisicoRestMapper.toLstTipoEspacioFisicoOutDTO(
				this.gestionarEspacioFisicoCUIntPort.consultarTiposEspaciosFisicosPorEdificio(lstEdificio));
	}

	/**
	 * Método encargado de consultar todos los edificios de los espacios físicos
	 * <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return Nombres de los edificios
	 */
	@GetMapping("/consultarEdificios")
	public List<String> consultarEdificios() {
		return this.gestionarEspacioFisicoCUIntPort.consultarEdificios();
	}

	/**
	 * Método encargado de consultar todas las ubicaciones de los espacios físicos
	 * <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return Nombres de las ubicaciones
	 */
	@GetMapping("/consultarUbicaciones")
	public List<String> consultarUbicaciones() {
		return this.gestionarEspacioFisicoCUIntPort.consultarUbicaciones();
	}

	/**
	 * Método encargado de consultar los edificios de los espacios físicos por
	 * ubicación <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return Nombres de los edificios
	 */
	@GetMapping("/consultarEdificiosPorUbicacion")
	public List<String> consultarEdificiosPorUbicacion(@RequestParam List<String> lstUbicacion) {
		return this.gestionarEspacioFisicoCUIntPort.consultarEdificiosPorUbicacion(lstUbicacion);
	}

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos dado una
	 * lista de identificadores únicos<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idAgrupadorEspacioFisico
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	@GetMapping("/consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico")
	public List<AgrupadorEspacioFisicoOutDTO> consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(
			@RequestParam List<Long> idAgrupadorEspacioFisico) {
		return this.agrupadorEspacioFisicoRestMapper
				.toLstAgrupadorEspacioFisicoOutDTO(this.gestionarAgrupadorEspacioFisicoCUIntPort
						.consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(idAgrupadorEspacioFisico));
	}

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos dado una
	 * lista de identificadores únicos de facultades<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idFacultad
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	@GetMapping("/consultarAgrupadoresEspaciosFisicosPorIdFacultad")
	public List<AgrupadorEspacioFisicoOutDTO> consultarAgrupadoresEspaciosFisicosPorIdFacultad(
			@RequestParam List<Long> idFacultad) {
		return this.agrupadorEspacioFisicoRestMapper
				.toLstAgrupadorEspacioFisicoOutDTO(this.gestionarAgrupadorEspacioFisicoCUIntPort
						.consultarAgrupadoresEspaciosFisicosPorIdFacultad(idFacultad));
	}
}