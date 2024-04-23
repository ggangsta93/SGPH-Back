package co.edu.unicauca.sgph.periodoacademico.infrastructure.input.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.periodoacademico.aplication.input.GestionarPeriodoAcademicoCUIntPort;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest.PeriodoAcademicoInDTO;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTOResponse.PeriodoAcademicoOutDTO;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.mapper.PeriodoAcademicoRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarPeriodoAcademico")
public class PeriodoAcademicoController {

	private GestionarPeriodoAcademicoCUIntPort gestionarPeriodoAcademicoCUIntPort;
	private PeriodoAcademicoRestMapper periodoAcademicoRestMapper;

	public PeriodoAcademicoController(GestionarPeriodoAcademicoCUIntPort gestionarPeriodoAcademicoCUIntPort,
			PeriodoAcademicoRestMapper periodoAcademicoRestMapper) {
		this.gestionarPeriodoAcademicoCUIntPort = gestionarPeriodoAcademicoCUIntPort;
		this.periodoAcademicoRestMapper = periodoAcademicoRestMapper;
	}

	/**
	 * Método encargado de guardar o actualizar un periodo academico </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param periodoAcademicoInDTO
	 * @return
	 */
	@PostMapping("/guardarPeriodoAcademico")
	public ResponseEntity<?> guardarPeriodoAcademico(@Valid @RequestBody PeriodoAcademicoInDTO periodoAcademicoInDTO,
			BindingResult result) {

		Set<String> validaciones = new HashSet<String>();
		validaciones.add("ExistsByAnioAndPeriodo");
		validaciones.add("PeriodoFinalGreaterThanPeriodoInicial");
		
		if (result.hasErrors()) {
			return validacion(result, validaciones);
		}
		PeriodoAcademicoOutDTO periodoAcademicoOutDTO = this.periodoAcademicoRestMapper
				.toPeriodoAcademicoOutDTO(this.gestionarPeriodoAcademicoCUIntPort.guardarPeriodoAcademico(
						this.periodoAcademicoRestMapper.toPeriodoAcademico(periodoAcademicoInDTO)));

		if (Objects.equals(periodoAcademicoInDTO.getIdPeriodoAcademico(),
				periodoAcademicoOutDTO.getIdPeriodoAcademico())) {
			return new ResponseEntity<PeriodoAcademicoOutDTO>(periodoAcademicoOutDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<PeriodoAcademicoOutDTO>(periodoAcademicoOutDTO, HttpStatus.CREATED);
		}
	}

	/**
	 * Método encargado de consultar el periodo académico vigente<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	@GetMapping("/consultarPeriodoAcademicoVigente")
	public ResponseEntity<?> consultarPeriodoAcademicoVigente() {
		PeriodoAcademicoOutDTO periodoAcademicoOutDTO = this.periodoAcademicoRestMapper
				.toPeriodoAcademicoOutDTO(this.gestionarPeriodoAcademicoCUIntPort.consultarPeriodoAcademicoVigente());
		return new ResponseEntity<PeriodoAcademicoOutDTO>(periodoAcademicoOutDTO, HttpStatus.OK);
	}

	/**
	 * Método envargado de validar si la fecha actual del sistema se encuentra
	 * dentro del rango de fechas de inicio y fin del periodo academémico más
	 * reciente, y actualiza su estado a 'CERRADO' si no está dentro de dicho rango.
	 * 
	 * Esta tarea se ejecuta automáticamente cada día a la medianoche <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 */
	@Scheduled(cron = "0 0 0 * * *") // Se ejecuta todos los días a la medianoche
	// @Scheduled(fixedRate = 30000) // Se ejecuta cada hora
	private void actualizarEstadoPeriodoAcademico() {
		LocalDate localDate = LocalDate.now();
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		this.gestionarPeriodoAcademicoCUIntPort.actualizarEstadoPeriodoAcademicoAutomaticamente(date);
	}

	/**
	 * Método encargado de manejar la validación de errores en las peticiones.<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param result El resultado de la validación.
	 * @param codes  Codigos personalizados
	 * @return ResponseEntity con los errores de validación.
	 * 
	 */
	private ResponseEntity<?> validacion(BindingResult result, Set<String> codes) {
		Map<String, String> errores = new HashMap<>();
		//Se validan restricciones de campos
		result.getAllErrors().forEach(error -> {
			if (codes.contains(error.getCode())) {
				errores.put(error.getCode(), error.getDefaultMessage());
			}
		});
		//Se validan restricciones de campos
		result.getFieldErrors().forEach(error -> {
			errores.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
}