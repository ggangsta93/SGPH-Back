package co.edu.unicauca.sgph.aula.domain.useCase;

import java.util.List;

import co.edu.unicauca.sgph.aula.aplication.input.GestionarTipoAulaCUIntPort;
import co.edu.unicauca.sgph.aula.aplication.output.GestionarTipoAulaGatewayIntPort;
import co.edu.unicauca.sgph.aula.aplication.output.TipoAulaFormatterResultsIntPort;
import co.edu.unicauca.sgph.aula.domain.model.TipoAula;

public class GestionarTipoAulaCUAdapter implements GestionarTipoAulaCUIntPort {

	private final GestionarTipoAulaGatewayIntPort gestionarTipoAulaGatewayIntPort;
	private final TipoAulaFormatterResultsIntPort tipoAulaFormatterResultsIntPort;

	public GestionarTipoAulaCUAdapter(GestionarTipoAulaGatewayIntPort gestionarTipoAulaGatewayIntPort,
			TipoAulaFormatterResultsIntPort tipoAulaFormatterResultsIntPort) {
		this.gestionarTipoAulaGatewayIntPort = gestionarTipoAulaGatewayIntPort;
		this.tipoAulaFormatterResultsIntPort = tipoAulaFormatterResultsIntPort;
	}

	@Override
	public List<TipoAula> consultarTipoAulasPorIdFacultad(List<Long> lstIdFacultad) {
		return this.gestionarTipoAulaGatewayIntPort.consultarTipoAulasPorIdFacultad(lstIdFacultad);
	}
}