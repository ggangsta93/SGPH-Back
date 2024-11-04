package co.edu.unicauca.sgph.asignatura.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import org.springframework.data.domain.Page;

public interface GestionarAsignaturaCUIntPort {
	
	/**
	 * Método encargado de guardar o actualizar una asignatura <br>
	 * 
	 * @author apedro
	 * 
	 * @param asignatura
	 * @return
	 */
	public Asignatura guardarAsignatura(Asignatura asignatura);

	/**
	 * Método encargado de consultar las asignaturas por programa <br>
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma
	 * @return
	 */
	public List<Asignatura> consultarAsignaturasPorIdPrograma(Long idPrograma);

	AsignaturaOutDTO obtenerAsignaturaPorId(Long idAsignatura);

	Page<AsignaturaOutDTO> filtrarAsignaturas(FiltroAsignaturaInDTO filtro);

	Asignatura inactivarAsignaturaPorId(Long idAsignatura);

	MensajeOutDTO cargaMasivaAsignaturas(AsignaturaInDTO asignatura);
	List<Asignatura> obtenerAsignaturasPorOids(List<String> oid);
	List<Asignatura> guardarAsignaturasDesdeJson(String archivoBase64);
	List<Asignatura> obtenerAsignaturaPorCodigo(String Codigo);
	Long contarAsignaturas();
}
