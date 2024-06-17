package co.edu.unicauca.sgph.horario.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.espaciofisico.domain.model.HorarioEspacio;
import co.edu.unicauca.sgph.horario.domain.model.Horario;

public interface GestionarHorarioGatewayIntPort {

	/**
	 * Método encargado de crear un horario principal
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param horario
	 * @return
	 */
	public Horario crearHorarioPrincipal(Horario horario);
	
	/**
	 * Método encargado de crear un horario secundario
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param horarioEspacio
	 * @return
	 */
	public Horario crearHorarioSecundario(HorarioEspacio horarioEspacio);
	
	/**
	 * Método encargado de eliminar un horario 
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param horario
	 */
	public void eliminarHorario(Horario horario);	
	
	/**
	 * Método encargado de eliminar un horario secundario 
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param horarioEspacio
	 */
	public void eliminarHorarioSecundario(HorarioEspacio horarioEspacio);	

	/**
	 * Método encargado de obtener los horarios de un curso
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param curso
	 * @return
	 */
	public List<Horario> consultarHorarioPorCurso(Curso curso);
	
	/**
	 * Método encargado de consultar la entidad HorarioEspacio por idHorario y
	 * idEspacioFisico
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idHorario
	 * @param idEspacioFisico
	 * @return
	 */
	public HorarioEspacio consultaHorarioEspacioPorIdHorarioYIdEspacioFisico(Long idHorario, Long idEspacioFisico);

}