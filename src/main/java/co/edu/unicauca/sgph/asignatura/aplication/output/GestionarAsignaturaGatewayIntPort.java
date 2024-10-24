package co.edu.unicauca.sgph.asignatura.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import org.springframework.data.domain.Page;

public interface GestionarAsignaturaGatewayIntPort {

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

	List<Asignatura> obtenerAsignaturasPorListaOids(List<String> oid);

	public Boolean existeCodigoAsignatura(String codigo);
	
	public Boolean existeOidAsignatura(String oid);
	
	public List<Asignatura> guardarAsignaturasDesdeJson(String Archivo64);
	
	public List<Asignatura> obtenerAsignaturaPorCodigo(String codigo);
}
