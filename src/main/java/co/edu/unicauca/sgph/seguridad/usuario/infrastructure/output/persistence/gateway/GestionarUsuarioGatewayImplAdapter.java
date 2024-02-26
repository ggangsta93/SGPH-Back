package co.edu.unicauca.sgph.seguridad.usuario.infrastructure.output.persistence.gateway;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.common.domain.model.TipoIdentificacion;
import co.edu.unicauca.sgph.seguridad.usuario.aplication.output.GestionarUsuarioGatewayIntPort;
import co.edu.unicauca.sgph.seguridad.usuario.domain.model.Rol;
import co.edu.unicauca.sgph.seguridad.usuario.domain.model.Usuario;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.DTORequest.FiltroUsuarioDTO;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.DTOResponse.UsuarioOutDTO;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.output.persistence.entity.EstadoUsuarioEnum;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.output.persistence.entity.UsuarioEntity;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.output.persistence.repository.UsuarioRepositoryInt;

@Service
public class GestionarUsuarioGatewayImplAdapter implements GestionarUsuarioGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private UsuarioRepositoryInt usuarioRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarUsuarioGatewayImplAdapter(UsuarioRepositoryInt usuarioRepositoryInt, ModelMapper modelMapper) {
		this.usuarioRepositoryInt = usuarioRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.seguridad.usuario.aplication.output.GestionarUsuarioGatewayIntPort#consultarUsuariosPorFiltro(co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.DTORequest.FiltroUsuarioDTO)
	 */
	@Override
	public Page<UsuarioOutDTO> consultarUsuariosPorFiltro(FiltroUsuarioDTO filtroUsuarioDTO) {
		PageRequest pageable = null;
		if (Objects.nonNull(filtroUsuarioDTO.getPagina())
				&& Objects.nonNull(filtroUsuarioDTO.getRegistrosPorPagina())) {
			// Configuración de la paginación
			pageable = PageRequest.of(filtroUsuarioDTO.getPagina(), filtroUsuarioDTO.getRegistrosPorPagina());
		}

		// Construcción de la consulta con StringBuilder
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				" SELECT NEW co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.DTOResponse.UsuarioOutDTO(");
		queryBuilder.append(" usu.idPersona, ti.idTipoIdentificacion, usu.numeroIdentificacion, ");
		queryBuilder.append(" ti.codigoTipoIdentificacion, usu.primerNombre, usu.segundoNombre, usu.primerApellido, ");
		queryBuilder.append(" usu.segundoApellido, usu.email, usu.nombreUsuario, usu.password, usu.estado)");
		queryBuilder.append(" FROM UsuarioEntity usu ");
		queryBuilder.append(" JOIN usu.tipoIdentificacion ti ");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroUsuarioDTO.getNombre())) {
			queryBuilder.append(" AND UPPER(TRIM(BOTH ' ' FROM CONCAT( ");
			queryBuilder.append(" usu.primerNombre,");
			queryBuilder.append(
					" CASE WHEN usu.segundoNombre IS NOT NULL THEN CONCAT(' ', usu.segundoNombre) ELSE '' END,");
			queryBuilder.append(" CONCAT(' ',usu.primerApellido),");
			queryBuilder.append(
					" CASE WHEN usu.segundoApellido IS NOT NULL THEN CONCAT(' ', usu.segundoApellido) ELSE '' END )))");
			queryBuilder.append(" LIKE UPPER(:nombre)");
			parametros.put("nombre", "%" + filtroUsuarioDTO.getNombre().replaceAll("\\s+", " ").trim() + "%");
		}
		if (Objects.nonNull(filtroUsuarioDTO.getNumeroIdentificacion())) {
			queryBuilder.append(" AND usu.numeroIdentificacion = :numeroIdentificacion");
			parametros.put("numeroIdentificacion", filtroUsuarioDTO.getNumeroIdentificacion().trim());
		}
		if (Objects.nonNull(filtroUsuarioDTO.getEstado())) {
			queryBuilder.append(" AND usu.estado = :estado");
			parametros.put("estado", filtroUsuarioDTO.getEstado());
		}

		queryBuilder.append(" ORDER BY usu.primerNombre asc");

		// Realiza la consulta paginada
		TypedQuery<UsuarioOutDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(), UsuarioOutDTO.class);

		if (Objects.nonNull(pageable)) {
			typedQuery.setFirstResult((int) pageable.getOffset());
			typedQuery.setMaxResults(pageable.getPageSize());
		}

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}

		Page<UsuarioOutDTO> listaUsuarios = null;
		// Si pageable es nulo, entonces retornar todos los resultados sin paginación
		if (Objects.isNull(pageable)) {
			listaUsuarios = new PageImpl<>(typedQuery.getResultList());
		} else {
			listaUsuarios = new PageImpl<>(typedQuery.getResultList(), pageable,
					contarUsuariosConsultados(filtroUsuarioDTO));
		}

		// Se establecen los identificadores de los roles y programas para cada usuario
		for (UsuarioOutDTO usuarioOutDTO : listaUsuarios) {
			usuarioOutDTO
					.setLstIdRol(this.consultarIdentificadoresRolesUsuarioPorPersona(usuarioOutDTO.getIdPersona()));
			usuarioOutDTO.setLstIdPrograma(
					this.consultarIdentificadoresProgramasUsuarioPorPersona(usuarioOutDTO.getIdPersona()));
		}

		return listaUsuarios;
	}

	private Long contarUsuariosConsultados(FiltroUsuarioDTO filtroUsuarioDTO) {

		// Construcción de la consulta con StringBuilder
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT COUNT(usu)");
		queryBuilder.append(" FROM UsuarioEntity usu ");
		queryBuilder.append(" JOIN usu.tipoIdentificacion ti ");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroUsuarioDTO.getNombre())) {
			queryBuilder.append(" AND UPPER(TRIM(BOTH ' ' FROM CONCAT( ");
			queryBuilder.append(" usu.primerNombre,");
			queryBuilder.append(
					" CASE WHEN usu.segundoNombre IS NOT NULL THEN CONCAT(' ', usu.segundoNombre) ELSE '' END,");
			queryBuilder.append(" usu.primerApellido,");
			queryBuilder.append(
					" CASE WHEN usu.segundoApellido IS NOT NULL THEN CONCAT(' ', usu.segundoApellido) ELSE '' END )))");
			queryBuilder.append(" LIKE UPPER(:nombre)");
			parametros.put("nombre", "%" + filtroUsuarioDTO.getNombre().replaceAll("\\s+", " ").trim() + "%");
		}
		if (Objects.nonNull(filtroUsuarioDTO.getNumeroIdentificacion())) {
			queryBuilder.append(" AND usu.numeroIdentificacion = :numeroIdentificacion");
			parametros.put("numeroIdentificacion", filtroUsuarioDTO.getNumeroIdentificacion().trim());
		}
		if (Objects.nonNull(filtroUsuarioDTO.getEstado())) {
			queryBuilder.append(" AND usu.estado = :estado");
			parametros.put("estado", filtroUsuarioDTO.getEstado());
		}

		queryBuilder.append(" ORDER BY usu.primerNombre asc");

		// Realiza la consulta para contar
		TypedQuery<Long> countQuery = entityManager.createQuery(queryBuilder.toString(), Long.class);

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return countQuery.getSingleResult();
	}

	/**
	 * @see co.edu.unicauca.sgph.seguridad.usuario.aplication.output.GestionarUsuarioGatewayIntPort#guardarUsuario(co.edu.unicauca.sgph.seguridad.usuario.domain.model.Usuario)
	 */
	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		return this.modelMapper.map(this.usuarioRepositoryInt.save(this.modelMapper.map(usuario, UsuarioEntity.class)),
				Usuario.class);
	}

	/**
	 * Método encargado de consultar los identificadores de roles asociados a un
	 * usuario </br>
	 * 
	 * @param idPersona
	 * @return
	 */
	private List<Long> consultarIdentificadoresRolesUsuarioPorPersona(Long idPersona) {
		return this.usuarioRepositoryInt.consultarIdentificadoresRolesUsuarioPorPersona(idPersona);
	}

	/**
	 * Método encargado de consultar los identificadores de programas asociados a un
	 * usuario </br>
	 * 
	 * @param idPersona
	 * @return
	 */
	private List<Long> consultarIdentificadoresProgramasUsuarioPorPersona(Long idPersona) {
		return this.usuarioRepositoryInt.consultarIdentificadoresProgramasUsuarioPorPersona(idPersona);
	}

	/**
	 * @see co.edu.unicauca.sgph.seguridad.usuario.aplication.output.GestionarUsuarioGatewayIntPort#consultarTiposIdentificacion()
	 */
	@Override
	public List<TipoIdentificacion> consultarTiposIdentificacion() {
		return this.modelMapper.map(this.usuarioRepositoryInt.consultarTiposIdentificacion(),
				new TypeToken<List<TipoIdentificacion>>() {
				}.getType());
	}

	/**
	 * @see co.edu.unicauca.sgph.seguridad.usuario.aplication.output.GestionarUsuarioGatewayIntPort#consultarRoles()
	 */
	@Override
	public List<Rol> consultarRoles() {
		return this.modelMapper.map(this.usuarioRepositoryInt.consultarRoles(), new TypeToken<List<Rol>>() {
		}.getType());
	}

	/**
	 * @see co.edu.unicauca.sgph.seguridad.usuario.aplication.output.GestionarUsuarioGatewayIntPort#consultarEstadosUsuario()
	 */
	@Override
	public List<String> consultarEstadosUsuario() {
		return Arrays.stream(EstadoUsuarioEnum.values()).map(Enum::name).collect(Collectors.toList());
	}
}