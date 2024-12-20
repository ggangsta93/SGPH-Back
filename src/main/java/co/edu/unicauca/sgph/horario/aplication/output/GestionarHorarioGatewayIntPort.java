package co.edu.unicauca.sgph.horario.aplication.output;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.espaciofisico.domain.model.HorarioEspacio;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.horario.domain.model.Horario;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.FranjaLibreOutDTO;

public interface GestionarHorarioGatewayIntPort {

	/**
	 * Método encargado de crear un horario principal
	 * 
	 * @author apedro
	 * 
	 * @param horario
	 * @return
	 */
	public Horario crearHorarioPrincipal(Horario horario);
	
	/**
	 * Método encargado de crear un horario secundario
	 * 
	 * @author apedro
	 * 
	 * @param horarioEspacio
	 * @return
	 */
	public Horario crearHorarioSecundario(HorarioEspacio horarioEspacio);
	
	/**
	 * Método encargado de eliminar un horario 
	 * 
	 * @author apedro
	 * 
	 * @param horario
	 */
	public void eliminarHorario(Horario horario);	
	
	/**
	 * Método encargado de eliminar un horario secundario 
	 * 
	 * @author apedro
	 * 
	 * @param horarioEspacio
	 */
	public void eliminarHorarioSecundario(HorarioEspacio horarioEspacio);	

	/**
	 * Método encargado de obtener los horarios de un curso
	 * 
	 * @author apedro
	 * 
	 * @param curso
	 * @return
	 */
	public List<Horario> consultarHorarioPorCurso(Curso curso);
	
	/**
	 * Método encargado de consultar la entidad HorarioEspacio por idHorario y
	 * idEspacioFisico
	 * 
	 * @author apedro
	 * 
	 * @param idHorario
	 * @param idEspacioFisico
	 * @return
	 */
	public HorarioEspacio consultaHorarioEspacioPorIdHorarioYIdEspacioFisico(Long idHorario, Long idEspacioFisico);
	
	public Page<FranjaLibreOutDTO> consultarFranjasLibres(FiltroFranjaHorariaDisponibleCursoDTO filtro);

}