package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.gateway;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.repository.CursoRepositoryInt;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.EspacioFisicoRepositoryInt;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.FranjaLibreOutDTO;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.repository.HorarioRepositoryInt;
import co.edu.unicauca.sgph.reservatemporal.application.output.GestionarReservaTemporalGatewayIntPort;
import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTORequest.ReservaTemporalInDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTOResponse.ReservaTemporalOutDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.EstadoReservaEntity;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.LogReservasEntity;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.ReservaTemporalEntity;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.repository.EstadoReservaRepository;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.repository.LogReservasRepository;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.repository.NotificacionesReservaRepository;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.repository.ReservaTemporalRepositoryInt;

@Service
public class GestionarReservaTemporalGatewayImplAdapter implements GestionarReservaTemporalGatewayIntPort{

	private final ReservaTemporalRepositoryInt reservaTemporalRepositoryInt;
    private final ModelMapper mapper;
    private final EspacioFisicoRepositoryInt espacioFisicoRepositoryInt;
    private final LogReservasRepository logReservasRepository;
    private final NotificacionesReservaRepository notificacionesReservaRepository;
    private final EstadoReservaRepository estadoReservaRepository;

    public GestionarReservaTemporalGatewayImplAdapter(ReservaTemporalRepositoryInt repository, ModelMapper mapper,
    		CursoRepositoryInt cursoRepositoryInt, HorarioRepositoryInt horarioRepositoryInt, EspacioFisicoRepositoryInt espacioFisicoRepositoryInt,
    		LogReservasRepository logReservasRepository, NotificacionesReservaRepository notificacionesReservaRepository, EstadoReservaRepository estadoReservaRepository) {
        this.reservaTemporalRepositoryInt = repository;
        this.mapper = mapper;
        this.espacioFisicoRepositoryInt = espacioFisicoRepositoryInt;  
        this.logReservasRepository = logReservasRepository;
        this.notificacionesReservaRepository = notificacionesReservaRepository;
        this.estadoReservaRepository = estadoReservaRepository;
    }
    
	@Override
	public List<ReservaTemporal> consultarReservas() {
		return mapper.map(reservaTemporalRepositoryInt.findAll(), new TypeToken<List<ReservaTemporal>>() {}.getType());
	}

	@Override
	public ReservaTemporal consultarReservaPorId(Long id) {
		return reservaTemporalRepositoryInt.findById(id)
                .map(entity -> mapper.map(entity, ReservaTemporal.class))
                .orElse(null);
	}

	@Override
	public ReservaTemporalOutDTO guardarReserva(ReservaTemporalInDTO inDTO) {

	    // Mapear el DTO a la entidad
	    ReservaTemporalEntity reservaEntity = new ReservaTemporalEntity();
	    reservaEntity.setUsuario(inDTO.getUsuario());
	    reservaEntity.setTipoIdentificacion(inDTO.getTipoIdentificacion());
	    reservaEntity.setNumeroIdentificacion(inDTO.getIdentificacion());
	    reservaEntity.setCorreo(inDTO.getCorreo());
	    reservaEntity.setTipoSolicitante(inDTO.getTipoSolicitante());
	    reservaEntity.setFechaReserva(inDTO.getFechaReserva());
	    reservaEntity.setObservaciones(inDTO.getObservaciones());
	    reservaEntity.setHoraInicio(inDTO.getHoraInicio());
	    reservaEntity.setHoraFin(inDTO.getHoraFin());
	    // Asignar espacio físico
	    EspacioFisicoEntity espacioFisico = espacioFisicoRepositoryInt.findById(inDTO.getIdEspacioFisico())
	        .orElseThrow(() -> new RuntimeException("Espacio físico no encontrado"));
	    reservaEntity.setEspacioFisico(espacioFisico);
	    
	 // Buscar el estado "RESERVA_PENDIENTE" en la base de datos
	    EstadoReservaEntity estadoReserva = estadoReservaRepository.findById(2L)
	        .orElseThrow(() -> new RuntimeException("Estado de reserva no encontrado"));

	    // Asignar estado inicial
	    reservaEntity.setEstado(estadoReserva);


	    // Persistir la reserva
	    ReservaTemporalEntity reservaGuardada = reservaTemporalRepositoryInt.save(reservaEntity);
	    
	    LogReservasEntity logReservasEntity = new LogReservasEntity(); // Inicialización
	    logReservasEntity.setAccion("CREAR_RESERVA");
	    logReservasEntity.setUsuario(inDTO.getUsuario());
	    logReservasEntity.setFechaModificacion(inDTO.getFechaReserva().atStartOfDay());
	    logReservasEntity.setReserva(reservaGuardada);
	    logReservasEntity.setObservaciones(inDTO.getObservaciones());
	    logReservasRepository.save(logReservasEntity);

	    // Mapear la entidad a un DTO de salida
	    return mapper.map(reservaGuardada, ReservaTemporalOutDTO.class);
	}

	@Override
	public Page<FranjaLibreOutDTO> consultarFranjasLibres(FiltroFranjaHorariaDisponibleCursoDTO filtro) {
		String dia = null;
	    if (filtro.getListaDiaSemanaEnum() != null && !filtro.getListaDiaSemanaEnum().isEmpty()) {
	        // Por ejemplo, si tu enum ya está en mayúsculas y en español:
	        // DiaSemanaEnum.LUNES -> "LUNES"
	        dia = filtro.getListaDiaSemanaEnum().get(0).name();
	    }
	    
	    Pageable pageable = PageRequest.of(filtro.getPagina(), filtro.getRegistrosPorPagina());

	    // Tomar la lista de ubicaciones (si viene vacía, pasamos null)
	    List<Long> listaIdUbicacion = (filtro.getListaIdUbicacion() != null 
	                                   && !filtro.getListaIdUbicacion().isEmpty())
	                                   ? filtro.getListaIdUbicacion()
	                                   : null;

	    // Preparar formateadores para las horas
	    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("H:mm:ss");
	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	    // Parsear la horaInicio a un formato estándar (HH:mm:ss)
	    String horaInicio = null;
	    if (filtro.getHoraInicio() != null) {
	        LocalTime time = LocalTime.parse(filtro.getHoraInicio(), inputFormatter);
	        horaInicio = time.format(outputFormatter);
	    }

	    // Parsear la horaFin a un formato estándar (HH:mm:ss)
	    String horaFin = null;
	    if (filtro.getHoraFin() != null) {
	        LocalTime time = LocalTime.parse(filtro.getHoraFin(), inputFormatter);
	        horaFin = time.format(outputFormatter);
	    }

	    if (filtro.getListaDiaSemanaEnum() == null || filtro.getListaDiaSemanaEnum().isEmpty()) {
	        System.out.println("No se recibió ningún día en el filtro");
	    } else {
	        System.out.println("Día recibido: " + filtro.getListaDiaSemanaEnum().get(0).name());
	    }
	    
	    Long idEspacioFisico = filtro.getIdAsignatura();
	    
	    Page<Object[]> resultado = reservaTemporalRepositoryInt.filtrarFranjasLibres(
	            idEspacioFisico,
	            dia,                  // o null, si no lo usas
	            horaInicio,
	            horaFin,
	            filtro.getSalon(),
	            listaIdUbicacion,
	            filtro.getFecha(),    // Aquí pasas la fecha que viene en el DTO
	            pageable
	    );

	    // Finalmente, transformas cada fila en tu DTO de salida
	    return resultado.map(row -> {
	    	DiaSemanaEnum diaEnum = DiaSemanaEnum.valueOf((String) row[1]);
	        return new FranjaLibreOutDTO(
	            /* row[0] -> idEspacioFisico */ 
	            ((Number) row[0]).longValue(),
	            /* row[1] -> día (puedes usarlo o ignorarlo si no lo necesitas) */
	            diaEnum, // O (String) row[1] si quisieras devolverlo
	            /* row[2] -> horaInicio */
	            LocalTime.parse((String) row[2]),
	            /* row[3] -> horaFin */
	            LocalTime.parse((String) row[3]),
	            /* row[4] -> nombre del salón */
	            (String) row[4],
	            /* row[5] -> capacidad */
	            row[5] != null ? ((Number) row[5]).longValue() : 0L,
	            /* row[6] -> tipo (columna 'tipo' en la consulta) */
	            (String) row[6],
	            /* row[7] -> ubicacion */
	            (String) row[7],
	            /* si tienes más columnas, las mapearás aquí 
	               o inicias con algo por defecto */
	            Collections.emptyList()
	        );
	    });
	}
}
