package co.edu.unicauca.sgph.gestionplanificacion.labordocencia.aplication.output;

import java.io.IOException;
import java.util.List;

import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteLaborDTO;

public interface GestionarLaborDocenciaGatewayIntPort {

	public List<DocenteLaborDTO> consultarLaborDocente(String nombrePrograma, String periodoVigente) throws IOException;

}
