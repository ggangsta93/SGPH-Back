package co.edu.unicauca.sgph.curso.infrastructure.input.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort;
import co.edu.unicauca.sgph.curso.infrastructure.input.DTORequest.CursoInDTO;
import co.edu.unicauca.sgph.curso.infrastructure.input.DTOResponse.CursoOutDTO;
import co.edu.unicauca.sgph.curso.infrastructure.input.mapper.CursoRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarCurso")
public class CursoController {

	private GestionarCursoCUIntPort gestionarCursoCUIntPort;
	private CursoRestMapper cursoRestMapper;

	public CursoController(GestionarCursoCUIntPort gestionarCursoCUIntPort, CursoRestMapper cursoRestMapper) {
		this.gestionarCursoCUIntPort = gestionarCursoCUIntPort;
		this.cursoRestMapper = cursoRestMapper;
	}

	/**
	 * Método encargado de guardar o actualizar un curso <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param cursoInDTO
	 * @return
	 */
	@PostMapping("/guardarCurso")
	public CursoOutDTO guardarCurso(@RequestBody CursoInDTO cursoInDTO) {
		return this.cursoRestMapper
				.toCursoOutDTO(this.gestionarCursoCUIntPort.guardarCurso(this.cursoRestMapper.toCurso(cursoInDTO)));
	}
	
	/**
	 * Método encargado de consultar los agrupadores de espacios físicos asociados
	 * al curso<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param cursoInDTO
	 * @return Lista de idAgrupadorEspacioFisico
	 */
	@GetMapping("/consultarAgrupadoresEspaciosFisicosPorCurso")
	public List<Long> consultarAgrupadoresEspaciosFisicosPorCurso(@RequestParam Long idCurso) {
		return this.gestionarCursoCUIntPort.consultarAgrupadoresEspaciosFisicosPorCurso(idCurso);
	}

	/**
	 * Método encargado de consultar un curso por grupo y asignatura <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param grupo
	 * @param idAsignatura
	 * @return
	 */
	@Deprecated
	public CursoOutDTO consultarCursoPorGrupoYAsignatura(String grupo, Long idAsignatura) {
		return this.cursoRestMapper
				.toCursoOutDTO(this.gestionarCursoCUIntPort.consultarCursoPorGrupoYAsignatura(grupo, idAsignatura));
	}
}