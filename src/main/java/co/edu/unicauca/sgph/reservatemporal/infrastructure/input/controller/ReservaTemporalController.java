package co.edu.unicauca.sgph.reservatemporal.infrastructure.input.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.reservatemporal.application.input.GestionarReservaTemporalCUIntPort;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTORequest.ReservaTemporalInDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTOResponse.ReservaTemporalOutDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.mapper.ReservaTemporalRestMapper;

@RestController
@RequestMapping("/AdministrarReservaTemporal")
public class ReservaTemporalController {

	private final GestionarReservaTemporalCUIntPort gestionarReservaTemporalCUIntPort;
    private final ReservaTemporalRestMapper mapper;

    public ReservaTemporalController(GestionarReservaTemporalCUIntPort useCase, ReservaTemporalRestMapper mapper) {
        this.gestionarReservaTemporalCUIntPort = useCase;
        this.mapper = mapper;
    }

    @PostMapping("/guardarReserva")
    public ReservaTemporalOutDTO guardarReserva(@RequestBody ReservaTemporalInDTO inDTO) {
        return this.mapper.toReservaTemporalOutDTO(this.gestionarReservaTemporalCUIntPort.guardarReserva(this.mapper.toReservaTemporal(inDTO)));
    }

    @GetMapping("/consultarReservas")
    public List<ReservaTemporalOutDTO> consultarReservas() {
        return this.mapper.toLstReservaTemporalOutDTO(this.gestionarReservaTemporalCUIntPort.consultarReservas());
    }
	
}
