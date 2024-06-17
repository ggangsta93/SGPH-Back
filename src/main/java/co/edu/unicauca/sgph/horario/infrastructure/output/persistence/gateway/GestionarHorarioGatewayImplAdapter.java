package co.edu.unicauca.sgph.horario.infrastructure.output.persistence.gateway;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;
import co.edu.unicauca.sgph.espaciofisico.domain.model.HorarioEspacio;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort;
import co.edu.unicauca.sgph.horario.domain.model.Horario;
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
}
