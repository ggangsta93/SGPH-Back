package co.edu.unicauca.sgph.reservatemporal.infrastructure.input.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.horario.aplication.input.GestionarHorarioCUIntPort;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.FranjaLibreOutDTO;
import co.edu.unicauca.sgph.reservatemporal.application.input.GestionarReservaTemporalCUIntPort;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTORequest.ReservaTemporalInDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTOResponse.FormularioReservaDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTOResponse.ReservaTemporalOutDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.mapper.ReservaTemporalRestMapper;
import co.edu.unicauca.sgph.usuario.aplication.input.GestionarUsuarioCUIntPort;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.UsuarioReservasDTO;

@RestController
@RequestMapping("/AdministrarReservaTemporal")
public class ReservaTemporalController {

	private final GestionarReservaTemporalCUIntPort gestionarReservaTemporalCUIntPort;
	private final GestionarUsuarioCUIntPort gestionarUsuarioCUIntPort;
	private final GestionarHorarioCUIntPort gestionarHorarioCUIntPort;
    private final ReservaTemporalRestMapper mapper;

    public ReservaTemporalController(GestionarReservaTemporalCUIntPort useCase, ReservaTemporalRestMapper mapper, GestionarUsuarioCUIntPort gestionarUsuarioCUIntPort,
    		GestionarHorarioCUIntPort gestionarHorarioCUIntPort) {
        this.gestionarReservaTemporalCUIntPort = useCase;
        this.mapper = mapper;
        this.gestionarUsuarioCUIntPort = gestionarUsuarioCUIntPort;
        this.gestionarHorarioCUIntPort = gestionarHorarioCUIntPort;
    }

    @PostMapping("/guardarReserva")
    public ResponseEntity<ReservaTemporalOutDTO> guardarReserva(@RequestBody ReservaTemporalInDTO inDTO) {
    	// Llamar al caso de uso pasando directamente el DTO de entrada
        var reservaGuardada = this.gestionarReservaTemporalCUIntPort.guardarReserva(inDTO);

        // Retornar el DTO de salida como respuesta
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaGuardada);
    }


    @GetMapping("/consultarReservas")
    public List<ReservaTemporalOutDTO> consultarReservas() {
        return this.mapper.toLstReservaTemporalOutDTO(this.gestionarReservaTemporalCUIntPort.consultarReservas());
    }
	
    @GetMapping("/formulario")
    public ResponseEntity<FormularioReservaDTO> cargarFormulario(@RequestParam String username) {
        // 1. Obtener datos del usuario desde el servicio externo
    	UsuarioReservasDTO usuario = gestionarUsuarioCUIntPort.obtenerDatosUsuarioExterno(username);

        // 2. Crear el DTO del formulario con los datos del usuario
        FormularioReservaDTO formulario = new FormularioReservaDTO();
        formulario.setUsuario(usuario);

        // Retornar el formulario con la información del usuario
        return ResponseEntity.ok(formulario);
    }
    
    @GetMapping("/consultarFranjasLibresReservas")
	public ResponseEntity<Page<FranjaLibreOutDTO>> consultarFranjasLibresReservas(
	    @RequestParam(required = false) Long idEspacioFisico,	
	    @RequestParam(required = false) DiaSemanaEnum diaSemana,
	    @RequestParam(required = false) String horaInicio,
	    @RequestParam(required = false) String horaFin,
	    @RequestParam(required = false) String salon,
	    @RequestParam(required = false) List<Long> ubicacion,
	    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReserva,
	    @RequestParam(defaultValue = "0") Integer pagina,
	    @RequestParam(defaultValue = "10") Integer registrosPorPagina
	) {
	    // Limpiar valores recibidos
	    String horaInicioLimpia = horaInicio != null ? horaInicio.trim() : null;
	    String horaFinLimpia = horaFin != null ? horaFin.trim() : null;

	    // Crear el filtro
	    FiltroFranjaHorariaDisponibleCursoDTO filtro = new FiltroFranjaHorariaDisponibleCursoDTO();
	    if (idEspacioFisico != null) {
	        filtro.setListaIdUbicacion(List.of(idEspacioFisico));
	    }
	    if (diaSemana != null) {
	        filtro.setListaDiaSemanaEnum(List.of(diaSemana));
	    }
	    filtro.setHoraInicio(horaInicioLimpia);
	    filtro.setHoraFin(horaFinLimpia);
	    filtro.setSalon(salon);
	    filtro.setListaIdUbicacion(ubicacion);
	    filtro.setFecha(fechaReserva);
	    filtro.setPagina(pagina);
	    filtro.setRegistrosPorPagina(registrosPorPagina);

	    // Llama al caso de uso para obtener las franjas libres paginadas
	    Page<FranjaLibreOutDTO> franjasLibres = gestionarReservaTemporalCUIntPort.consultarFranjasLibres(filtro);

	    // Retorna la respuesta con el objeto paginado
	    return ResponseEntity.ok(franjasLibres);
	}
}
