package co.edu.unicauca.sgph.horario.infrastructure.output.persistence.gateway;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;
import co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort;
import co.edu.unicauca.sgph.horario.domain.model.Horario;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEntity;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.repository.HorarioRepositoryInt;

@Service
public class GestionarHorarioGatewayImplAdapter implements GestionarHorarioGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private HorarioRepositoryInt horarioRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarHorarioGatewayImplAdapter(HorarioRepositoryInt horarioRepositoryInt, ModelMapper modelMapper) {
		this.horarioRepositoryInt = horarioRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/** 
	 * @see co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort#guardarHorario(co.edu.unicauca.sgph.horario.domain.model.Horario)
	 */
	@Override
	public Horario guardarHorario(Horario horario) {
		return this.saveHorario(horario);
	}
	
	/**
	 * Método encargado de guardar o actualizar un horario

	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param horario
	 * @return
	 */
	private Horario saveHorario(Horario horario) {
		HorarioEntity horarioEntity = this.horarioRepositoryInt
				.save(this.modelMapper.map(horario, HorarioEntity.class));
		return this.modelMapper.map(horarioEntity, Horario.class);
	}
	
	/** 
	 * @see co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort#guardarHorarioConNuevaTransaccion(co.edu.unicauca.sgph.horario.domain.model.Horario)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Horario guardarHorarioConNuevaTransaccion(Horario horario) {
		return this.saveHorario(horario) ;
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
}
