package co.edu.unicauca.sgph.departamento.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.departamento.domain.model.Departamento;
import co.edu.unicauca.sgph.departamento.infrastructure.output.persistence.entity.DepartamentoEntity;

public interface GestionarDepartamentoGatewayIntPort {
	
	/**
	 * Método encargado de consultar guardar y/o actualizar un departamento<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public Departamento guardarDepartamento(Departamento departamento);

	/**
	 * Método encargado de consultar todos los departamentos<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public List<Departamento> consultarDepartamentos();

	public Departamento consultarDepartamentoPorNombre(String nombre);
}