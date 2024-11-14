package co.edu.unicauca.sgph.asignatura.aplication.output;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.EstadoAsignaturaEnum;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;

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
	 * Método encargado de consultar las asignaturas por programa y estado<br>
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma
	 * @param estadoAsignaturaEnum Estado de la asignatura (Si es nulo se obtienen
	 *                             todos)
	 * @return
	 */
	public List<Asignatura> consultarAsignaturasPorIdProgramaYEstado(Long idPrograma,
			EstadoAsignaturaEnum estadoAsignaturaEnum);
	
	
	/**
	 * Método encargado de consultar las asignaturas de los cursos del periodo
	 * vigente para un programa<br>
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma
	 * @param idPeriodoAcademicoVigente Periodo académico vigente 
	 * 
	 * @return
	 */
	public List<Asignatura> consultarAsignaturasDeLosCursosPorIdPrograma(Long idPrograma, Long idPeriodoAcademicoVigente);

    AsignaturaOutDTO obtenerAsignaturaPorId(Long idAsignatura);

	Page<AsignaturaOutDTO> filtrarAsignaturas(FiltroAsignaturaInDTO filtro);

	Asignatura inactivarAsignaturaPorId(Long idAsignatura);

	MensajeOutDTO cargaMasivaAsignaturas(AsignaturaInDTO asignatura);

	List<Asignatura> obtenerAsignaturasPorListaOids(List<String> oid);

	public Boolean existeCodigoAsignatura(String codigo);
	
	public Boolean existeOidAsignatura(String oid);
	
	public List<Asignatura> guardarAsignaturasDesdeJson(String Archivo64);
	
	public List<Asignatura> obtenerAsignaturaPorCodigo(String codigo);
	
	public Long contarAsignaturas();
}
