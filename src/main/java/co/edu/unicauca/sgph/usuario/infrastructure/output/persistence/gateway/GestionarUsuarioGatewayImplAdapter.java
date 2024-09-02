package co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.gateway;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.usuario.aplication.output.GestionarUsuarioGatewayIntPort;
import co.edu.unicauca.sgph.usuario.domain.model.Rol;
import co.edu.unicauca.sgph.usuario.domain.model.Usuario;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest.FiltroUsuarioDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.UsuarioOutDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.EstadoUsuarioEnum;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.UsuarioEntity;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.repository.UsuarioRepositoryInt;

@Service
public class GestionarUsuarioGatewayImplAdapter implements GestionarUsuarioGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;
	private PasswordEncoder passwordEncoder;

	private UsuarioRepositoryInt usuarioRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarUsuarioGatewayImplAdapter(UsuarioRepositoryInt usuarioRepositoryInt, ModelMapper modelMapper,
			PasswordEncoder passwordEncoder) {
		this.usuarioRepositoryInt = usuarioRepositoryInt;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * @see co.edu.unicauca.sgph.usuario.aplication.output.GestionarUsuarioGatewayIntPort#consultarUsuariosPorFiltro(co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest.FiltroUsuarioDTO)
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
		queryBuilder.append(" SELECT NEW co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.UsuarioOutDTO(");
		queryBuilder.append(" usu.idUsuario, per.idPersona, ti.idTipoIdentificacion, per.numeroIdentificacion, ");
		queryBuilder.append(" ti.codigoTipoIdentificacion, per.primerNombre, per.segundoNombre, per.primerApellido, ");
		queryBuilder.append(" per.segundoApellido, per.email, usu.nombreUsuario, usu.password, usu.estado ");
		queryBuilder.append(" )");
		queryBuilder.append(" FROM UsuarioEntity usu ");
		queryBuilder.append(" JOIN usu.persona per ");
		queryBuilder.append(" JOIN per.tipoIdentificacion ti ");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroUsuarioDTO.getNombre())) {
			queryBuilder.append(" AND UPPER(TRIM(BOTH ' ' FROM CONCAT( ");
			queryBuilder.append(" per.primerNombre,");
			queryBuilder.append(
					" CASE WHEN per.segundoNombre IS NOT NULL THEN CONCAT(' ', per.segundoNombre) ELSE '' END,");
			queryBuilder.append(" CONCAT(' ',per.primerApellido),");
			queryBuilder.append(
					" CASE WHEN per.segundoApellido IS NOT NULL THEN CONCAT(' ', per.segundoApellido) ELSE '' END )))");
			queryBuilder.append(" LIKE UPPER(:nombre)");
			parametros.put("nombre", "%" + filtroUsuarioDTO.getNombre().replaceAll("\\s+", " ").trim() + "%");
		}
		if (Objects.nonNull(filtroUsuarioDTO.getNumeroIdentificacion())) {
			queryBuilder.append(" AND per.numeroIdentificacion LIKE (:numeroIdentificacion) ");
			parametros.put("numeroIdentificacion",
					"%" + filtroUsuarioDTO.getNumeroIdentificacion().replaceAll("\\s+", " ").trim() + "%");
		}
		if (Objects.nonNull(filtroUsuarioDTO.getEstado())) {
			queryBuilder.append(" AND usu.estado = :estado");
			parametros.put("estado", filtroUsuarioDTO.getEstado());
		}

		queryBuilder.append(" ORDER BY per.primerNombre asc");

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
		queryBuilder.append(" JOIN usu.persona per ");
		queryBuilder.append(" JOIN per.tipoIdentificacion ti ");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroUsuarioDTO.getNombre())) {
			queryBuilder.append(" AND UPPER(TRIM(BOTH ' ' FROM CONCAT( ");
			queryBuilder.append(" per.primerNombre,");
			queryBuilder.append(
					" CASE WHEN per.segundoNombre IS NOT NULL THEN CONCAT(' ', per.segundoNombre) ELSE '' END,");
			queryBuilder.append(" per.primerApellido,");
			queryBuilder.append(
					" CASE WHEN per.segundoApellido IS NOT NULL THEN CONCAT(' ', per.segundoApellido) ELSE '' END )))");
			queryBuilder.append(" LIKE UPPER(:nombre)");
			parametros.put("nombre", "%" + filtroUsuarioDTO.getNombre().replaceAll("\\s+", " ").trim() + "%");
		}
		if (Objects.nonNull(filtroUsuarioDTO.getNumeroIdentificacion())) {
			queryBuilder.append(" AND per.numeroIdentificacion LIKE (:numeroIdentificacion) ");
			parametros.put("numeroIdentificacion",
					"%" + filtroUsuarioDTO.getNumeroIdentificacion().replaceAll("\\s+", " ").trim() + "%");
		}
		if (Objects.nonNull(filtroUsuarioDTO.getEstado())) {
			queryBuilder.append(" AND usu.estado = :estado");
			parametros.put("estado", filtroUsuarioDTO.getEstado());
		}

		queryBuilder.append(" ORDER BY per.primerNombre asc");

		// Realiza la consulta para contar
		TypedQuery<Long> countQuery = entityManager.createQuery(queryBuilder.toString(), Long.class);

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return countQuery.getSingleResult();
	}

	/**
	 * @see co.edu.unicauca.sgph.usuario.aplication.output.GestionarUsuarioGatewayIntPort#guardarUsuario(co.edu.unicauca.sgph.usuario.domain.model.Usuario)
	 */
	@Override
	@Transactional
	public Usuario guardarUsuario(Usuario usuario) {
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		return this.modelMapper.map(this.usuarioRepositoryInt.save(this.modelMapper.map(usuario, UsuarioEntity.class)),
				Usuario.class);
	}

	/**
	 * Método encargado de consultar los identificadores de roles asociados a un
	 * usuario <br>
	 * 
	 * @author apedro
	 * 
	 * @param idPersona
	 * @return
	 */
	private List<Long> consultarIdentificadoresRolesUsuarioPorPersona(Long idPersona) {
		return this.usuarioRepositoryInt.consultarIdentificadoresRolesUsuarioPorPersona(idPersona);
	}

	/**
	 * Método encargado de consultar los identificadores de programas asociados a un
	 * usuario <br>
	 * 
	 * @author apedro
	 * 
	 * @param idPersona
	 * @return
	 */
	private List<Long> consultarIdentificadoresProgramasUsuarioPorPersona(Long idPersona) {
		return this.usuarioRepositoryInt.consultarIdentificadoresProgramasUsuarioPorPersona(idPersona);
	}

	/**
	 * @see co.edu.unicauca.sgph.usuario.aplication.output.GestionarUsuarioGatewayIntPort#consultarRoles()
	 */
	@Override
	public List<Rol> consultarRoles() {
		return this.modelMapper.map(this.usuarioRepositoryInt.consultarRoles(), new TypeToken<List<Rol>>() {
		}.getType());
	}

	/**
	 * @see co.edu.unicauca.sgph.usuario.aplication.output.GestionarUsuarioGatewayIntPort#consultarEstadosUsuario()
	 */
	@Override
	public List<String> consultarEstadosUsuario() {
		return Arrays.stream(EstadoUsuarioEnum.values()).map(Enum::name).collect(Collectors.toList());
	}

	/**
	 * @see co.edu.unicauca.sgph.usuario.aplication.output.GestionarUsuarioGatewayIntPort#existeUsuarioPorNombreUsuario(java.lang.String,
	 *      java.lang.Long)
	 */
	@Override
	public Boolean existeUsuarioPorNombreUsuario(String nombreUsuario, Long idUsuario) {
		UsuarioEntity usuarioEntity = this.usuarioRepositoryInt.consultarUsuarioPorNombreUsuario(nombreUsuario,
				idUsuario);
		if (Objects.nonNull(usuarioEntity)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/** 
	 * @see co.edu.unicauca.sgph.usuario.aplication.output.GestionarUsuarioGatewayIntPort#existeUsuarioPorIdPersona(java.lang.Long, java.lang.Long)
	 */
	@Override
	public Boolean existeUsuarioPorIdPersona(Long idPersona, Long idUsuario) {
		UsuarioEntity usuarioEntity = this.usuarioRepositoryInt.consultarUsuarioPorIdPersona(idPersona, idUsuario);
		if (Objects.nonNull(usuarioEntity)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/** 
	 * @see co.edu.unicauca.sgph.usuario.aplication.output.GestionarUsuarioGatewayIntPort#cambiarEstadoUsuarioPorIdUsuario(java.lang.Long)
	 */
	@Override
	public Usuario cambiarEstadoUsuarioPorIdUsuario(Long idUsuario) {
		Optional<UsuarioEntity> usuario = this.usuarioRepositoryInt.findById(idUsuario);
		if (usuario.isPresent()) {
			UsuarioEntity usuarioEntity = usuario.get();
			if (usuarioEntity.getEstado() != null && usuarioEntity.getEstado().equals(EstadoUsuarioEnum.ACTIVO)) {
				usuarioEntity.setEstado(EstadoUsuarioEnum.INACTIVO);
			} else {
				usuarioEntity.setEstado(EstadoUsuarioEnum.ACTIVO);
			}
			return this.modelMapper.map(this.usuarioRepositoryInt.save(usuarioEntity), Usuario.class);
		}
		return null;
	}

	@Override
	public Usuario consultarUsuarioPorIdPersona(Long idPersona) {
		Optional<UsuarioEntity> usuario = this.usuarioRepositoryInt.findByPersonaIdPersonaAndEstado(idPersona, EstadoUsuarioEnum.ACTIVO);
		if (usuario.isPresent()) {
			return this.modelMapper.map(usuario.get(), Usuario.class);
		}
		return null;
	}
	
	/** 
	 * @see co.edu.unicauca.sgph.usuario.aplication.output.GestionarUsuarioGatewayIntPort#consultarUsuarioPorNombreUsuario(java.lang.String)
	 */
	@Override
	public Usuario consultarUsuarioPorNombreUsuario(String nombreUsuario) {
		Optional<UsuarioEntity> usuarioEntity = this.usuarioRepositoryInt.findByNombreUsuario(nombreUsuario);
		if (usuarioEntity.isPresent()) {

			// Convertir manualmente
			Usuario usuario = new Usuario();
			usuario.setNombreUsuario(usuarioEntity.get().getNombreUsuario());
			usuario.setPassword(usuarioEntity.get().getPassword());
			usuario.setEstado(usuarioEntity.get().getEstado());
			usuario.setPersona(new Persona());
			usuario.getPersona().setEmail(usuarioEntity.get().getPersona().getEmail());

			// Manejar las colecciones manualmente
			usuario.setRoles(usuarioEntity.get().getRoles().stream().map(obj -> {
				Rol rol = new Rol();
				rol.setIdRol(obj.getIdRol());
				rol.setRolUsuario(obj.getRolUsuario());
				return rol;
			}).collect(Collectors.toSet()));
			usuario.setProgramas(usuarioEntity.get().getProgramas().stream().map(obj -> {
				Programa programa = new Programa();
				programa.setIdPrograma(obj.getIdPrograma());
				programa.setNombre(obj.getNombre());
				return programa;
			}).distinct().collect(Collectors.toList()));

			return usuario;
		}	
		return null;
	}
}