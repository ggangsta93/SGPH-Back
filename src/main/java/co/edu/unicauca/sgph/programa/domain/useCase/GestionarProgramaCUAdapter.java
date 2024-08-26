package co.edu.unicauca.sgph.programa.domain.useCase;

import java.util.List;
import java.util.Objects;

import co.edu.unicauca.sgph.programa.aplication.input.GestionarProgramaCUIntPort;
import co.edu.unicauca.sgph.programa.aplication.output.GestionarProgramaGatewayIntPort;
import co.edu.unicauca.sgph.programa.aplication.output.ProgramaFormatterResultsIntPort;
import co.edu.unicauca.sgph.programa.domain.model.Programa;

public class GestionarProgramaCUAdapter implements GestionarProgramaCUIntPort {

	private final GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort;
	private final ProgramaFormatterResultsIntPort programaFormatterResultsIntPort;

	public GestionarProgramaCUAdapter(GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort,
			ProgramaFormatterResultsIntPort programaFormatterResultsIntPort) {
		this.gestionarProgramaGatewayIntPort = gestionarProgramaGatewayIntPort;
		this.programaFormatterResultsIntPort = programaFormatterResultsIntPort;
	}

	@Override
	public Programa consultarProgramaPorNombre(String nombre) {
		Programa programa = this.gestionarProgramaGatewayIntPort.consultarProgramaPorNombre(nombre);
		return Objects.nonNull(programa) ? programa
				: this.programaFormatterResultsIntPort
						.prepararRespuestaFallida("No se encontró programa con ese nombre");
	}

	@Override
	public Programa guardarPrograma(Programa programa) {
		return this.gestionarProgramaGatewayIntPort.guardarPrograma(programa);
	}

	/**
	 * @see co.edu.unicauca.sgph.programa.aplication.input.GestionarProgramaCUIntPort#consultarProgramasPorIdFacultad(java.util.List)
	 */
	@Override
	public List<Programa> consultarProgramasPorIdFacultad(List<Long> lstIdFacultad) {
		return this.gestionarProgramaGatewayIntPort.consultarProgramasPorIdFacultad(lstIdFacultad);
	}

	/**
	 * @see co.edu.unicauca.sgph.programa.aplication.input.GestionarProgramaCUIntPort#consultarProgramas()
	 */
	@Override
	public List<Programa> consultarProgramas() {
		return this.gestionarProgramaGatewayIntPort.consultarProgramas();
	}

	/** 
	 * @see co.edu.unicauca.sgph.programa.aplication.input.GestionarProgramaCUIntPort#consultarProgramasPermitidosPorUsuario()
	 */
	@Override
	public List<Programa> consultarProgramasPermitidosPorUsuario() {
		return this.gestionarProgramaGatewayIntPort.consultarProgramasPermitidosPorUsuario();
	}
}