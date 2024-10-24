package co.edu.unicauca.sgph.common.domain.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public abstract class CommonEJB {

	/**
	 * Método encargado de manejar la validación de errores en las peticiones.<br>
	 * 
	 * @author apedro
	 * 
	 * @param result El resultado de la validación.
	 * @param codes  Codigos personalizados
	 * @return ResponseEntity con los errores de validación.
	 * 
	 */
	protected ResponseEntity<?> validacion(BindingResult result, Set<String> codes) {
		Map<String, String> errores = new HashMap<>();
		// Se validan restricciones de campos
		result.getAllErrors().forEach(error -> {
			if (codes.contains(error.getCode())) {
				errores.put(error.getCode(), error.getDefaultMessage());
			}
		});
		// Se validan restricciones de campos
		result.getFieldErrors().forEach(error -> {
			errores.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
		});
		return ResponseEntity.accepted().body(errores);
	}
	
	/**
	 * Método encargado de manejar la validación de errores en las campos.<br>
	 * 
	 * @author apedro
	 * 
	 * @param result El resultado de la validación.
	 * @param codes  Codigos personalizados
	 * @return ResponseEntity con los errores de validación.
	 * 
	 */
	protected ResponseEntity<?> validarCampos(BindingResult result, Set<String> codes) {
		Map<String, String> errores = new HashMap<>();
		// Se validan restricciones de campos
		result.getAllErrors().forEach(error -> {
			if (codes.contains(error.getCode())) {
				errores.put(error.getCode(), error.getDefaultMessage());
			}
		});
		// Se validan restricciones de campos
		result.getFieldErrors().forEach(error -> {
			errores.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
}
