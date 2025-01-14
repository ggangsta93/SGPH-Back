package co.edu.unicauca.sgph.horario.aplication.input;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.horario.domain.model.Horario;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.QRRequestDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.FranjaLibreOutDTO;

public interface GestionarHorarioCUIntPort {

	/**
	 * Método encargado de obtener los horarios de un curso
	 * 
	 * @author apedro
	 * 
	 * @param curso
	 * @return
	 */
	public List<Horario> consultarHorarioPorCurso(Curso curso);

	public Page<FranjaLibreOutDTO> consultarFranjasLibres(FiltroFranjaHorariaDisponibleCursoDTO filtro);
	
	void guardarQR(QRRequestDTO qrRequest);
}
