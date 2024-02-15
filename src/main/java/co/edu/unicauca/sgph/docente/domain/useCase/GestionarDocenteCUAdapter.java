package co.edu.unicauca.sgph.docente.domain.useCase;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.sgph.docente.aplication.output.DocenteFormatterResultsIntPort;
import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO;

public class GestionarDocenteCUAdapter implements GestionarDocenteCUIntPort {

	private final GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort;
	private final DocenteFormatterResultsIntPort docenteFormatterResultsIntPort;

	public GestionarDocenteCUAdapter(GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort,
			DocenteFormatterResultsIntPort docenteFormatterResultsIntPort) {
		this.gestionarDocenteGatewayIntPort = gestionarDocenteGatewayIntPort;
		this.docenteFormatterResultsIntPort = docenteFormatterResultsIntPort;
	}

	@Override
	public Docente guardarDocente(Docente docente) {
		return this.gestionarDocenteGatewayIntPort.guardarDocente(docente);
	}

	@Override
	public Docente consultarDocentePorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion) {
		return this.gestionarDocenteGatewayIntPort.consultarDocentePorIdentificacion(idTipoIdentificacion,
				numeroIdentificacion);
	}

	@Override
	public Docente consultarDocentePorIdPersona(Long idPersona) {
		Docente docente = this.gestionarDocenteGatewayIntPort.consultarDocentePorIdPersona(idPersona);
		return Objects.nonNull(docente) ? docente
				: this.docenteFormatterResultsIntPort
						.prepararRespuestaFallida("No se encontró docente con ese idPersona");
	}

	@Override
	public List<String> consultarNombresDocentesPorIdCurso(Long idCurso) {
		return this.gestionarDocenteGatewayIntPort.consultarNombresDocentesPorIdCurso(idCurso);
	}

	@Override
	public Page<DocenteOutDTO> consultarDocentes(FiltroDocenteDTO filtroDocenteDTO) {
		return this.gestionarDocenteGatewayIntPort.consultarDocentes(filtroDocenteDTO);
	}

	@Override
	public List<Docente> consultarDocentePorIdCurso(Long idCurso) {
		return this.gestionarDocenteGatewayIntPort.consultarDocentePorIdCurso(idCurso);
	}
}