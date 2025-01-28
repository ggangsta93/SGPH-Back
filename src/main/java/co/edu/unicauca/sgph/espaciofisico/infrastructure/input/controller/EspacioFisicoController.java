package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEdificioCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEspacioFisicoCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarTipoEspacioFisicoCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarUbicacionCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.infrastructura.input.validation.ValidationGroups;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.EspacioFisicoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoAgrupadorDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroFranjaLibreDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EdificioOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.RecursoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.TipoEspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.UbicacionOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper.EdificioRestMapper;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper.EspacioFisicoRestMapper;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper.TipoEspacioFisicoRestMapper;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper.UbicacionRestMapper;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EstadoEspacioFisicoEnum;

@RestController
@RequestMapping("/AdministrarEspacioFisico")
public class EspacioFisicoController extends CommonEJB {

	 @Autowired
	 private Validator validator;
	 
	// Gestionadores
	private GestionarEspacioFisicoCUIntPort gestionarEspacioFisicoCUIntPort;
	private GestionarTipoEspacioFisicoCUIntPort gestionarTipoEspacioFisicoCUIntPort;
	private GestionarEdificioCUIntPort gestionarEdificioCUIntPort;
	private GestionarUbicacionCUIntPort gestionarUbicacionCUIntPort;
	// Mapers
	private EspacioFisicoRestMapper espacioFisicoRestMapper;
	private TipoEspacioFisicoRestMapper tipoEspacioFisicoRestMapper;
	private EdificioRestMapper edificioRestMapper;
	private UbicacionRestMapper ubicacionRestMapper;

	public EspacioFisicoController(GestionarEspacioFisicoCUIntPort gestionarEspacioFisicoCUIntPort,
			EspacioFisicoRestMapper espacioFisicoRestMapper,
			GestionarTipoEspacioFisicoCUIntPort gestionarTipoEspacioFisicoCUIntPort,
			TipoEspacioFisicoRestMapper tipoEspacioFisicoRestMapper,
			GestionarEdificioCUIntPort gestionarEdificioCUIntPort, EdificioRestMapper edificioRestMapper,
			GestionarUbicacionCUIntPort gestionarUbicacionCUIntPort, UbicacionRestMapper ubicacionRestMapper) {
		this.gestionarEspacioFisicoCUIntPort = gestionarEspacioFisicoCUIntPort;
		this.espacioFisicoRestMapper = espacioFisicoRestMapper;
		this.gestionarTipoEspacioFisicoCUIntPort = gestionarTipoEspacioFisicoCUIntPort;
		this.tipoEspacioFisicoRestMapper = tipoEspacioFisicoRestMapper;
		this.gestionarEdificioCUIntPort = gestionarEdificioCUIntPort;
		this.edificioRestMapper = edificioRestMapper;
		this.gestionarUbicacionCUIntPort = gestionarUbicacionCUIntPort;
		this.ubicacionRestMapper = ubicacionRestMapper;
	}

	@PostMapping("/guardarEspacioFisico")
	@Transactional
	public ResponseEntity<?> guardarEspacioFisico( @RequestBody EspacioFisicoInDTO espacioFisicoInDTO, BindingResult result) {
		Class<?> validationGroup = (espacioFisicoInDTO.getIdEspacioFisico() == null) 
                ? ValidationGroups.OnCreate.class 
                : ValidationGroups.OnUpdate.class;

		// Realizar la validación con el grupo correspondiente
		Set<ConstraintViolation<EspacioFisicoInDTO>> violations = validator.validate(espacioFisicoInDTO, validationGroup);
		
		// Convertir las violaciones en errores del BindingResult
		for (ConstraintViolation<EspacioFisicoInDTO> violation : violations) {
		String fieldName = violation.getPropertyPath().toString();
		String errorMessage = violation.getMessage();
		result.addError(new FieldError("espacioFisicoInDTO", fieldName, errorMessage));
		}
		
		// Si hay errores, retornar los mensajes de error
		if (result.hasErrors()) {
		return ResponseEntity.badRequest().body(result.getAllErrors());
		}

		
		if(Boolean.FALSE.equals(espacioFisicoInDTO.getEsValidar())) {
			EspacioFisicoOutDTO espacioFisicoOutDTO = this.espacioFisicoRestMapper.toEspacioFisicoOutDTO(
					this.gestionarEspacioFisicoCUIntPort.guardarEspacioFisico(
							this.espacioFisicoRestMapper.toEspacioFisico(espacioFisicoInDTO)));
			
			
	        return new ResponseEntity<>(espacioFisicoOutDTO, HttpStatus.OK);
	        
	    } else {
	        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	    }
	}
	
	@GetMapping("activarInactivarEspacioFisicio/{id}")
	public void activarInactivarEspacioFisico(@PathVariable Long id) {
		this.gestionarEspacioFisicoCUIntPort.activarInactivarEspacioFisico(id);
	}

	/**
	 * Método encargado de consultar un espacio físico por su identificador
	 * único<br>
	 * -Utilizado en la pantalla de Gestionar espacios físicos<br>
	 * 
	 * @author apedro
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
	 * Método encargado de consultar los tipos de espacios físicos por
	 * ubicaciones<br>
	 * 
	 * @author apedro
	 * 
	 * @param lstUbicaciones
	 * @return
	 */
	@GetMapping("/consultarTiposEspaciosFisicosPorUbicaciones")
	public List<TipoEspacioFisicoOutDTO> consultarTiposEspaciosFisicosPorUbicaciones(
			@RequestParam List<Long> lstIdUbicacion) {
		return this.tipoEspacioFisicoRestMapper.toLstTipoEspacioFisicoOutDTO(
				this.gestionarTipoEspacioFisicoCUIntPort.consultarTiposEspaciosFisicosPorUbicaciones(lstIdUbicacion));
	}

	/**
	 * Método encargado de consultar los espacios físicos por diferentes criterios
	 * de busqueda y retornarlos de manera paginada<br>
	 * 
	 * @author apedro
	 * 
	 * @param filtroEspacioFisicoDTO DTO con los filtros de busqueda
	 * @return
	 */
	@GetMapping("/consultarEspaciosFisicos")
	public Page<EspacioFisicoDTO> consultarEspaciosFisicos(
	    @RequestParam(required = false) List<Long> listaIdUbicacion,
	    @RequestParam(required = false) List<Long> listaIdEdificio,
	    @RequestParam(required = false) List<Long> listaIdTipoEspacioFisico,
	    @RequestParam(required = false) String salon,
	    @RequestParam(required = false) EstadoEspacioFisicoEnum estado,
	    @RequestParam(required = false) Long capacidad,
	    @RequestParam(defaultValue = "0") Integer pagina,
	    @RequestParam(defaultValue = "10") Integer registrosPorPagina) {

	    FiltroEspacioFisicoDTO filtro = new FiltroEspacioFisicoDTO();
	    filtro.setListaIdUbicacion(listaIdUbicacion);
	    filtro.setListaIdEdificio(listaIdEdificio);
	    filtro.setListaIdTipoEspacioFisico(listaIdTipoEspacioFisico);
	    filtro.setSalon(salon);
	    filtro.setEstado(estado);
	    filtro.setCapacidad(capacidad);
	    filtro.setPagina(pagina);
	    filtro.setRegistrosPorPagina(registrosPorPagina);

	    return this.gestionarEspacioFisicoCUIntPort.consultarEspaciosFisicos(filtro);
	}

	/**
	 * Método encargado de consultar los tipos de espacios físicos asociados a una
	 * lista de edificios <br>
	 * 
	 * @author apedro
	 * 
	 * @param lstIdEdificio
	 * @return
	 */
	@GetMapping("/consultarTiposEspaciosFisicosPorEdificio")
	public List<TipoEspacioFisicoOutDTO> consultarTiposEspaciosFisicosPorEdificio(
			@RequestParam List<Long> lstIdEdificio) {
		return this.espacioFisicoRestMapper.toLstTipoEspacioFisicoOutDTO(
				this.gestionarEspacioFisicoCUIntPort.consultarTiposEspaciosFisicosPorEdificio(lstIdEdificio));
	}

	/**
	 * Método encargado de consultar todos los edificios <br>
	 * 
	 * @author apedro
	 * 
	 * @return Listas de instancias EdificioOutDTO
	 */
	@GetMapping("/consultarEdificios")
	public List<EdificioOutDTO> consultarEdificios() {
		return this.edificioRestMapper.toLstEdificioOutDTO(this.gestionarEdificioCUIntPort.consultarEdificios());
	}

	/**
	 * Método encargado de consultar todas las ubicaciones <br>
	 * 
	 * @author apedro
	 * 
	 * @return Lista de instancias UbicacionOutDTO
	 */
	@GetMapping("/consultarUbicaciones")
	public List<UbicacionOutDTO> consultarUbicaciones() {
		return this.ubicacionRestMapper.toLstUbicacionOutDTO(this.gestionarUbicacionCUIntPort.consultarUbicaciones());
	}

	/**
	 * Método encargado de consultar los edificios de los espacios físicos por
	 * ubicación <br>
	 * 
	 * @author apedro
	 * 
	 * @return Nombres de los edificios
	 */
	@GetMapping("/consultarEdificiosPorUbicacion")
	public List<EdificioOutDTO> consultarEdificiosPorUbicacion(@RequestParam List<Long> lstIdUbicacion) {
		return this.edificioRestMapper.toLstEdificioOutDTO(this.gestionarEspacioFisicoCUIntPort.consultarEdificiosPorUbicacion(lstIdUbicacion));
	}

	@GetMapping("/obtenerEspaciosFisicosAsignadosAAgrupadorId/{idAgrupador}")
	public List<EspacioFisicoDTO> obtenerEspaciosFisicosPorAgrupadorId(@PathVariable Long idAgrupador) {
		return this.gestionarEspacioFisicoCUIntPort.obtenerEspaciosFisicosPorAgrupadorId(idAgrupador);
	}

	@GetMapping("/obtenerEspaciosFisicosSinAsignarAAgrupadorId/{idAgrupador}")
	public List<EspacioFisicoDTO> obtenerEspaciosFisicosSinAsignarAAgrupadorId(@PathVariable Long idAgrupador) {
		return this.gestionarEspacioFisicoCUIntPort.obtenerEspaciosFisicosSinAsignarAAgrupadorId(idAgrupador);
	}

	@GetMapping("/consultarEspacioFisicoConFiltro")
	public List<EspacioFisicoDTO> consultarEspaciosFisicosConFiltro(
	        @RequestParam(value = "ubicacion", required = false) String ubicacion,
	        @RequestParam(value = "nombre", required = false) String nombre,
	        @RequestParam(value = "tipo", required = false) String tipo,
	        @RequestParam(value = "idAgrupador", required = false) Long idAgrupador) {

	    FiltroEspacioFisicoAgrupadorDTO filtro = new FiltroEspacioFisicoAgrupadorDTO();
	    filtro.setUbicacion(ubicacion);
	    filtro.setNombre(nombre);
	    filtro.setTipo(tipo);
	    filtro.setIdAgrupador(idAgrupador);

	    return this.gestionarEspacioFisicoCUIntPort.consultarEspaciosFisicosConFiltro(filtro);
	}

	@GetMapping("/obtenerListaRecursos")
	public List<RecursoOutDTO> obtenerListaRecursos() {
		return this.gestionarEspacioFisicoCUIntPort.obtenerListaRecursos();
	}
	@GetMapping("/consultarTiposEspaciosFisicos")
	public List<TipoEspacioFisicoOutDTO> consultarTiposEspaciosFisicos() {
		return this.tipoEspacioFisicoRestMapper.toLstTipoEspacioFisicoOutDTO(
				this.gestionarTipoEspacioFisicoCUIntPort.consultarTiposEspaciosFisicos());
	}
	
	@PostMapping("cargaMasiva")
	private MensajeOutDTO cargaMasivaEspacioFisico(@RequestBody EspacioFisicoInDTO espacioFisico) {
		return this.gestionarEspacioFisicoCUIntPort.cargaMasivaEspacioFisico(espacioFisico);
	}
	
	@PostMapping("/consultarFranjasLibres")
	public ResponseEntity<List<EspacioFisicoDTO>> consultarFranjasLibres(@Valid @RequestBody FiltroFranjaLibreDTO filtroFranjaLibreDTO) {
	    // Llamar al puerto de entrada correspondiente
	    List<EspacioFisicoDTO> franjasLibres = this.gestionarEspacioFisicoCUIntPort.consultarFranjasLibres(filtroFranjaLibreDTO);
	    
	    if (franjasLibres.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }
	    
	    return ResponseEntity.ok(franjasLibres);
	}
	
	@GetMapping("/obtenerRecursosPorEspacioFisico")
    public ResponseEntity<List<RecursoOutDTO>> obtenerRecursosPorEspacioFisico(@RequestParam Long idEspacioFisico) {
        List<RecursoOutDTO> recursos = gestionarEspacioFisicoCUIntPort.obtenerRecursosPorEspacioFisico(idEspacioFisico);
        if (recursos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recursos);
    }
	
	@PostMapping("/cargarEspaciosFisicos")
	public ResponseEntity<?> cargarEspaciosFisicos(@RequestParam("file") MultipartFile file) {
	    try {
	    	Map<String, Object> resultado = gestionarEspacioFisicoCUIntPort.cargarEspaciosFisicos(file);
	        resultado.put("archivo", file.getOriginalFilename());
	        return ResponseEntity.ok(resultado);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body("Error de validación: " + e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
	    }
	}


}