package co.edu.unicauca.sgph.departamento.domain.useCase;

import java.util.List;

import co.edu.unicauca.sgph.departamento.aplication.input.GestionarDepartamentoCUIntPort;
import co.edu.unicauca.sgph.departamento.aplication.output.GestionarDepartamentoGatewayIntPort;
import co.edu.unicauca.sgph.departamento.domain.model.Departamento;

public class GestionarDepartamentoCUAdapter implements GestionarDepartamentoCUIntPort {

	private final GestionarDepartamentoGatewayIntPort gestionarDepartamentoGatewayIntPort;

	public GestionarDepartamentoCUAdapter(GestionarDepartamentoGatewayIntPort gestionarDepartamentoGatewayIntPort) {
		this.gestionarDepartamentoGatewayIntPort = gestionarDepartamentoGatewayIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.departamento.aplication.input.GestionarDepartamentoCUIntPort#guardarDepartamento(co.edu.unicauca.sgph.departamento.domain.model.Departamento)
	 */
	@Override
	public Departamento guardarDepartamento(Departamento departamento) {
		return this.gestionarDepartamentoGatewayIntPort.guardarDepartamento(departamento);
	}

	/**
	 * @see co.edu.unicauca.sgph.departamento.aplication.input.GestionarDepartamentoCUIntPort#consultarDepartamentos()
	 */
	@Override
	public List<Departamento> consultarDepartamentos() {
		return this.gestionarDepartamentoGatewayIntPort.consultarDepartamentos();
	}
}