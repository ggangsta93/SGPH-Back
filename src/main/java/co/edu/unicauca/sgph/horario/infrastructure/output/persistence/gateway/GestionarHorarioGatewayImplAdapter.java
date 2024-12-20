package co.edu.unicauca.sgph.horario.infrastructure.output.persistence.gateway;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;
import co.edu.unicauca.sgph.espaciofisico.domain.model.HorarioEspacio;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort;
import co.edu.unicauca.sgph.horario.domain.model.Horario;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.FranjaLibreOutDTO;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEntity;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEspacioEntity;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.repository.HorarioEspacioRepositoryInt;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.repository.HorarioRepositoryInt;

@Service
public class GestionarHorarioGatewayImplAdapter implements GestionarHorarioGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private HorarioRepositoryInt horarioRepositoryInt;
	private HorarioEspacioRepositoryInt horarioEspacioRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarHorarioGatewayImplAdapter(HorarioRepositoryInt horarioRepositoryInt, ModelMapper modelMapper,
			HorarioEspacioRepositoryInt horarioEspacioRepositoryInt) {
		this.horarioRepositoryInt = horarioRepositoryInt;
		this.modelMapper = modelMapper;
		this.horarioEspacioRepositoryInt = horarioEspacioRepositoryInt;
	}

	/**
	 * @see co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort#eliminarHorario(co.edu.unicauca.sgph.horario.domain.model.Horario)
	 */
	@Override
	public void eliminarHorario(Horario horario) {
		HorarioEntity horarioEntity = this.modelMapper.map(horario, HorarioEntity.class);
		this.horarioRepositoryInt.delete(horarioEntity);
	}

	/**
	 * @see co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort#consultarHorarioPorCurso(co.edu.unicauca.sgph.curso.domain.model.Curso)
	 */
	@Override
	public List<Horario> consultarHorarioPorCurso(Curso curso) {
		return this.modelMapper.map(
				this.horarioRepositoryInt.findByCurso(this.modelMapper.map(curso, CursoEntity.class)),
				new TypeToken<List<Horario>>() {
				}.getType());
	}

	/**
	 * @see co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort#crearHorarioPrincipal(co.edu.unicauca.sgph.horario.domain.model.Horario)
	 */
	@Override
	public Horario crearHorarioPrincipal(Horario horario) {
		HorarioEntity horarioEntity = null;

		horarioEntity = this.horarioRepositoryInt.save(this.modelMapper.map(horario, HorarioEntity.class));

		HorarioEspacioEntity horarioEspacioEntity = this.horarioEspacioRepositoryInt
				.findByHorarioIdHorarioAndEspacioFisicoIdEspacioFisico(horarioEntity.getIdHorario(),
						horarioEntity.getEspaciosFisicos().get(0).getIdEspacioFisico());
		horarioEspacioEntity.setEsPrincipal(Boolean.TRUE);
		this.horarioEspacioRepositoryInt.save(horarioEspacioEntity);

		return this.modelMapper.map(horarioEntity, Horario.class);
	}
	
	/** 
	 * @see co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort#consultaHorarioEspacioPorIdHorarioYIdEspacioFisico(java.lang.Long, java.lang.Long)
	 */
	@Override
	public  HorarioEspacio consultaHorarioEspacioPorIdHorarioYIdEspacioFisico(Long idHorario, Long idEspacioFisico) {
		HorarioEspacioEntity horarioEspacioEntity = this.horarioEspacioRepositoryInt
				.findByHorarioIdHorarioAndEspacioFisicoIdEspacioFisico(idHorario, idEspacioFisico);
		if (Objects.nonNull(horarioEspacioEntity)) {
			return this.modelMapper.map(horarioEspacioEntity, HorarioEspacio.class);
		} else {
			return null;
		}
	}

	/**
	 * @see co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort#crearHorarioSecundario(co.edu.unicauca.sgph.espaciofisico.domain.model.HorarioEspacio)
	 */
	@Override
	public Horario crearHorarioSecundario(HorarioEspacio horarioEspacio) {

		if (Objects.nonNull(horarioEspacio.getHorario().getIdHorario())
				&& Objects.nonNull(horarioEspacio.getEspacioFisico().getIdEspacioFisico())) {
			HorarioEspacioEntity horarioEspacioEntity = this.modelMapper.map(horarioEspacio,
					HorarioEspacioEntity.class);
			EspacioFisicoEntity espacioFisico = new EspacioFisicoEntity();
			espacioFisico.setIdEspacioFisico(horarioEspacio.getEspacioFisico().getIdEspacioFisico());
			horarioEspacioEntity.setEspaciosFisico(espacioFisico);
			this.horarioEspacioRepositoryInt.save(horarioEspacioEntity);
		}

		return null;
	}

	/**
	 * @see co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort#eliminarHorarioSecundario(co.edu.unicauca.sgph.espaciofisico.domain.model.HorarioEspacio)
	 */
	@Override
	public void eliminarHorarioSecundario(HorarioEspacio horarioEspacio) {
		HorarioEspacioEntity horarioEspacioEntity = this.modelMapper.map(horarioEspacio, HorarioEspacioEntity.class);
		EspacioFisicoEntity espacioFisico = new EspacioFisicoEntity();
		espacioFisico.setIdEspacioFisico(horarioEspacio.getEspacioFisico().getIdEspacioFisico());
		horarioEspacioEntity.setEspaciosFisico(espacioFisico);
		horarioEspacioEntity.getIdHorarioEspacio()
				.setIdEspacioFisico(horarioEspacio.getEspacioFisico().getIdEspacioFisico());
		this.horarioEspacioRepositoryInt.delete(horarioEspacioEntity);
	}

	@Override
	public Page<FranjaLibreOutDTO> consultarFranjasLibres(FiltroFranjaHorariaDisponibleCursoDTO filtro) {
	    // Convertir el enum de día a la cadena exacta que está en la base.
	    // Asumiendo que tu enum tiene valores LUNES, MARTES, etc.
	    String dia = null;
	    if (filtro.getListaDiaSemanaEnum() != null && !filtro.getListaDiaSemanaEnum().isEmpty()) {
	        // Por ejemplo, si tu enum ya está en mayúsculas y en español:
	        // DiaSemanaEnum.LUNES -> "LUNES"
	        dia = filtro.getListaDiaSemanaEnum().get(0).name();
	    }

	    Pageable pageable = PageRequest.of(filtro.getPagina(), filtro.getRegistrosPorPagina());

	    List<Long> listaIdUbicacion = (filtro.getListaIdUbicacion() != null && !filtro.getListaIdUbicacion().isEmpty())
	            ? filtro.getListaIdUbicacion()
	            : null;

	    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("H:mm:ss");
	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	    String horaInicio = null;
	    if (filtro.getHoraInicio() != null) {
	        LocalTime time = LocalTime.parse(filtro.getHoraInicio(), inputFormatter);
	        horaInicio = time.format(outputFormatter);
	    }

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

	    Page<Object[]> resultado = horarioRepositoryInt.filtrarFranjasLibres(
	        idEspacioFisico,
	        dia,
	        horaInicio,
	        horaFin,
	        filtro.getSalon(),
	        listaIdUbicacion,
	        pageable
	    );

	    return resultado.map(row -> {
	        DiaSemanaEnum diaEnum = DiaSemanaEnum.valueOf((String) row[1]);
	        return new FranjaLibreOutDTO(
	            ((Number) row[0]).longValue(),
	            diaEnum,
	            LocalTime.parse((String) row[2]),
	            LocalTime.parse((String) row[3]),
	            (String) row[4],
	            ((Number) row[5]).longValue(),
	            (String) row[6],
	            (String) row[7],
	            Collections.emptyList()
	        );
	    });
	}

}
