package co.edu.unicauca.sgph.horario.domain.useCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.util.Base64Utils;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.horario.aplication.input.GestionarHorarioCUIntPort;
import co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort;
import co.edu.unicauca.sgph.horario.aplication.output.HorarioFormatterResultsIntPort;
import co.edu.unicauca.sgph.horario.domain.model.Horario;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.QRRequestDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.FranjaLibreOutDTO;

public class GestionarHorarioCUAdapter implements GestionarHorarioCUIntPort {
	
	private final GestionarHorarioGatewayIntPort gestionarHorarioGatewayIntPort;
	private final HorarioFormatterResultsIntPort horarioFormatterResultsIntPort;
			
	@Value("${qr.directory.path}")
    private String qrDirectoryPath;
	
	public GestionarHorarioCUAdapter(GestionarHorarioGatewayIntPort gestionarHorarioGatewayIntPort,
			HorarioFormatterResultsIntPort horarioFormatterResultsIntPort) {
		this.gestionarHorarioGatewayIntPort = gestionarHorarioGatewayIntPort;
		this.horarioFormatterResultsIntPort = horarioFormatterResultsIntPort;
	}
	
	/** 
	 * @see co.edu.unicauca.sgph.horario.aplication.input.GestionarHorarioCUIntPort#consultarHorarioPorCurso(co.edu.unicauca.sgph.curso.domain.model.Curso)
	 */
	@Override
	public List<Horario> consultarHorarioPorCurso(Curso curso) {
		return this.gestionarHorarioGatewayIntPort.consultarHorarioPorCurso(curso);
	}

	@Override
	public Page<FranjaLibreOutDTO> consultarFranjasLibres(FiltroFranjaHorariaDisponibleCursoDTO filtro) {
		return gestionarHorarioGatewayIntPort.consultarFranjasLibres(filtro);
	}

	@Override
	public void guardarQR(QRRequestDTO qrRequest) {
		byte[] qrDataBytes = Base64Utils.decodeFromString(qrRequest.getQrData());
        String filePath = qrDirectoryPath + File.separator + qrRequest.getNombre();

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(qrDataBytes);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el QR", e);
        }
	}

	

}