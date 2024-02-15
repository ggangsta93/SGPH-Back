package co.edu.unicauca.sgph.edificio.domain.useCase;

import java.util.List;
import java.util.Objects;

import co.edu.unicauca.sgph.edificio.aplication.input.GestionarEdificioCUIntPort;
import co.edu.unicauca.sgph.edificio.aplication.output.EdificioFormatterResultsIntPort;
import co.edu.unicauca.sgph.edificio.aplication.output.GestionarEdificioGatewayIntPort;
import co.edu.unicauca.sgph.edificio.domain.model.Edificio;

public class GestionarEdificioCUAdapter implements GestionarEdificioCUIntPort {

	private final GestionarEdificioGatewayIntPort gestionarEdificioGatewayIntPort;
	private final EdificioFormatterResultsIntPort edificioFormatterResultsIntPort;

	public GestionarEdificioCUAdapter(GestionarEdificioGatewayIntPort gestionarEdificioGatewayIntPort,
			EdificioFormatterResultsIntPort edificioFormatterResultsIntPort) {
		this.gestionarEdificioGatewayIntPort = gestionarEdificioGatewayIntPort;
		this.edificioFormatterResultsIntPort = edificioFormatterResultsIntPort;
	}

	@Override
	public Edificio consultarEdificioPorNombre(String nombre) {
		Edificio edificio = this.gestionarEdificioGatewayIntPort.consultarEdificioPorNombre(nombre);
		return Objects.nonNull(edificio) ? edificio
				: this.edificioFormatterResultsIntPort
						.prepararRespuestaFallida("No se encontró edificio con ese nombre");
	}

	@Override
	public Edificio guardarEdificio(Edificio edificio) {
		return this.gestionarEdificioGatewayIntPort.guardarEdificio(edificio);
	}

	@Override
	public List<Edificio> consultarEdificiosPorIdFacultad(List<Long> lstIdFacultad) {
		return this.gestionarEdificioGatewayIntPort.consultarEdificiosPorIdFacultad(lstIdFacultad);
	}
}