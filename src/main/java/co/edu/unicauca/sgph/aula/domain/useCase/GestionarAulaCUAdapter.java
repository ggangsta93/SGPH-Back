package co.edu.unicauca.sgph.aula.domain.useCase;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.aula.aplication.input.GestionarAulaCUIntPort;
import co.edu.unicauca.sgph.aula.aplication.output.AulaFormatterResultsIntPort;
import co.edu.unicauca.sgph.aula.aplication.output.GestionarAulaGatewayIntPort;
import co.edu.unicauca.sgph.aula.domain.model.Aula;
import co.edu.unicauca.sgph.aula.domain.model.TipoAula;
import co.edu.unicauca.sgph.aula.infrastructure.input.DTORequest.FiltroAulaDTO;
import co.edu.unicauca.sgph.aula.infrastructure.input.DTOResponse.AulaDTO;

public class GestionarAulaCUAdapter implements GestionarAulaCUIntPort {

	private final GestionarAulaGatewayIntPort gestionarAulaGatewayIntPort;
	private final AulaFormatterResultsIntPort aulaFormatterResultsIntPort;

	public GestionarAulaCUAdapter(GestionarAulaGatewayIntPort gestionarAulaGatewayIntPort,
			AulaFormatterResultsIntPort aulaFormatterResultsIntPort) {
		this.gestionarAulaGatewayIntPort = gestionarAulaGatewayIntPort;
		this.aulaFormatterResultsIntPort = aulaFormatterResultsIntPort;
	}

	@Override
	public Aula consultarAulaPorIdAula(Long idAula) {
		return this.gestionarAulaGatewayIntPort.consultarAulaPorIdAula(idAula);
	}

	@Override
	public Aula guardarAula(Aula aula) {
		return this.gestionarAulaGatewayIntPort.guardarAula(aula);
	}

	@Override
	public List<String> consultarAulaHorarioPorIdCurso(Long idCurso) {
		return this.gestionarAulaGatewayIntPort.consultarAulaHorarioPorIdCurso(idCurso);
	}

	@Override
	public Page<AulaDTO> consultarAulas(FiltroAulaDTO filtroAulaDTO) {
		return this.gestionarAulaGatewayIntPort.consultarAulas(filtroAulaDTO);
	}

	@Override
	public List<TipoAula> consultarTipoAulasPorIdEdificio(List<Long> lstIdEdificio) {
		return this.gestionarAulaGatewayIntPort.consultarTipoAulasPorIdEdificio(lstIdEdificio);
	}
}