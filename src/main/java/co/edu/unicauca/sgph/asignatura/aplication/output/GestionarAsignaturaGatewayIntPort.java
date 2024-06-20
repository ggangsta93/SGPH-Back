package co.edu.unicauca.sgph.asignatura.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import org.springframework.data.domain.Page;

public interface GestionarAsignaturaGatewayIntPort {

	/**
	 * Método encargado de guardar o actualizar una asignatura <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param asignatura
	 * @return
	 */
	public Asignatura guardarAsignatura(Asignatura asignatura);

	/**
	 * Método encargado de consultar las asignaturas por programa <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idPrograma
	 * @return
	 */
	public List<Asignatura> consultarAsignaturasPorIdPrograma(Long idPrograma);

    AsignaturaOutDTO obtenerAsignaturaPorId(Long idAsignatura);

	Page<AsignaturaOutDTO> filtrarAsignaturas(FiltroAsignaturaInDTO filtro);

	Asignatura inactivarAsignaturaPorId(Long idAsignatura);

	Boolean cargaMasivaAsignaturas(AsignaturaInDTO asignatura);
}
