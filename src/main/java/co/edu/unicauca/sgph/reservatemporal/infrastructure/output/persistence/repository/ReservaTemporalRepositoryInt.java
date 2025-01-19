package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.EstadoReservaEntity;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.EstadoReservaEnum;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.ReservaTemporalEntity;

public interface ReservaTemporalRepositoryInt extends JpaRepository<ReservaTemporalEntity, Long>{

	@Query(value = """
		    SELECT DISTINCT
		           ef.id_espacio_fisico AS idEspacioFisico,
		           d.dia                AS dia,
		           franja.horaInicio    AS horaInicio,
		           franja.horaFin       AS horaFin,
		           ef.salon             AS salon,
		           ef.capacidad         AS capacidad,
		           te.tipo              AS tipo,
		           u.nombre             AS ubicacion
		      FROM espacio_fisico ef
		           LEFT JOIN tipo_espacio_fisico te ON ef.id_tipo_espacio_fisico = te.id_tipo_espacio_fisico
		           LEFT JOIN ubicacion u ON ef.id_ubicacion = u.id_ubicacion,
		           (
		               SELECT '07:00:00' AS horaInicio, '09:00:00' AS horaFin
		               UNION SELECT '09:00:00', '11:00:00'
		               UNION SELECT '11:00:00', '13:00:00'
		               UNION SELECT '14:00:00', '16:00:00'
		               UNION SELECT '16:00:00', '18:00:00'
		               UNION SELECT '18:00:00', '20:00:00'
		               UNION SELECT '20:00:00', '22:00:00'
		           ) AS franja,
		           (
				        SELECT 'LUNES' AS dia UNION SELECT 'MARTES' UNION SELECT 'MIERCOLES'
				        UNION SELECT 'JUEVES' UNION SELECT 'VIERNES' UNION SELECT 'SABADO'
				    ) AS d
		     WHERE 
		           -- Filtros opcionales
		           (:idEspacioFisico IS NULL OR ef.id_espacio_fisico = :idEspacioFisico)
		       AND (:dia IS NULL OR d.dia = :dia)
		       AND (:horaInicio IS NULL OR franja.horaInicio >= :horaInicio)
		       AND (:horaFin    IS NULL OR franja.horaFin    <= :horaFin)
		       AND (:salon IS NULL OR LOWER(ef.salon) LIKE LOWER(CONCAT('%', :salon, '%')))
		       AND (:ubicacion IS NULL OR ef.id_ubicacion IN (:ubicacion))
			   AND (				    
				    :recursosSize = 0 
				    OR ef.id_espacio_fisico IN (
				        SELECT ref.id_espacio_fisico
				        FROM RECURSO_ESPACIO_FISICO ref
				        WHERE ref.id_recurso_fisico IN (:recursos) -- Lista válida
				        GROUP BY ref.id_espacio_fisico
				        HAVING COUNT(DISTINCT ref.id_recurso_fisico) >= :recursosSize
				    )
				)


		       -- Se excluyen las franjas ocupadas en horario_espaciofisico
		       AND NOT EXISTS (
		           SELECT 1
		             FROM horario_espaciofisico he
		             JOIN horario h ON he.id_horario = h.id_horario
		            WHERE he.id_espacio_fisico = ef.id_espacio_fisico
		              AND h.dia = d.dia
		              AND franja.horaInicio < h.hora_fin
		              AND franja.horaFin    > h.hora_inicio
		       )
		       -- Se excluyen las franjas ocupadas en reserva_temporal
		       AND NOT EXISTS (
		           SELECT 1
		             FROM reserva_temporal r
		             JOIN estado_reserva e ON r.id_estado = e.id_estado
		            WHERE r.id_espacio_fisico = ef.id_espacio_fisico
		              AND r.fecha_reserva = :fechaReserva
		              AND franja.horaInicio < r.hora_fin
		              AND franja.horaFin    > r.hora_inicio
		              AND e.descripcion NOT IN ('RESERVA_RECHAZADA', 'RESERVA_FINALIZADA', 'RESERVA_CANCELADA')
		              AND e.descripcion IN ('RESERVA_APROBADA', 'RESERVA_PENDIENTE')
		       )
		""",
		countQuery = """
		    SELECT COUNT(*)
		      FROM espacio_fisico ef
		           LEFT JOIN tipo_espacio_fisico te ON ef.id_tipo_espacio_fisico = te.id_tipo_espacio_fisico
		           LEFT JOIN ubicacion u ON ef.id_ubicacion = u.id_ubicacion
		           CROSS JOIN (
		               SELECT '07:00:00' AS horaInicio, '09:00:00' AS horaFin
		               UNION SELECT '09:00:00', '11:00:00'
		               UNION SELECT '11:00:00', '13:00:00'
		               UNION SELECT '14:00:00', '16:00:00'
		               UNION SELECT '16:00:00', '18:00:00'
		               UNION SELECT '18:00:00', '20:00:00'
		               UNION SELECT '20:00:00', '22:00:00'
		           ) AS franja,
		           (
				        SELECT 'LUNES' AS dia UNION SELECT 'MARTES' UNION SELECT 'MIERCOLES'
				        UNION SELECT 'JUEVES' UNION SELECT 'VIERNES' UNION SELECT 'SABADO'
				    ) AS d
		     WHERE 
		           (:idEspacioFisico IS NULL OR ef.id_espacio_fisico = :idEspacioFisico)
		       AND (:dia IS NULL OR d.dia = :dia)
		       AND (:horaInicio IS NULL OR franja.horaInicio >= :horaInicio)
		       AND (:horaFin    IS NULL OR franja.horaFin    <= :horaFin)
		       AND (:salon IS NULL OR LOWER(ef.salon) LIKE LOWER(CONCAT('%', :salon, '%')))
		       AND (:ubicacion IS NULL OR ef.id_ubicacion IN (:ubicacion))
			   AND (
				    :recursosSize = 0 
				    OR ef.id_espacio_fisico IN (
				        SELECT ref.id_espacio_fisico
				        FROM RECURSO_ESPACIO_FISICO ref
				        WHERE ref.id_recurso_fisico IN (:recursos) -- Lista válida
				        GROUP BY ref.id_espacio_fisico
				        HAVING COUNT(DISTINCT ref.id_recurso_fisico) >= :recursosSize
				    )
				)


		       -- Exclusión en horario_espaciofisico
		       AND NOT EXISTS (
		           SELECT 1
		             FROM horario_espaciofisico he
		             JOIN horario h ON he.id_horario = h.id_horario
		            WHERE he.id_espacio_fisico = ef.id_espacio_fisico
		              AND h.dia = d.dia
		              AND franja.horaInicio < h.hora_fin
		              AND franja.horaFin    > h.hora_inicio
		       )
		       -- Exclusión en reserva_temporal
		       AND NOT EXISTS (
		           SELECT 1
		             FROM reserva_temporal r
		             JOIN estado_reserva e ON r.id_estado = e.id_estado
		            WHERE r.id_espacio_fisico = ef.id_espacio_fisico
		              AND r.fecha_reserva = :fechaReserva
		              AND franja.horaInicio < r.hora_fin
		              AND franja.horaFin    > r.hora_inicio
		              AND e.descripcion NOT IN ('RESERVA_RECHAZADA', 'RESERVA_FINALIZADA', 'RESERVA_CANCELADA')
		              AND e.descripcion IN ('RESERVA_APROBADA', 'RESERVA_PENDIENTE')
		       )
		""",
		nativeQuery = true)
		Page<Object[]> filtrarFranjasLibres(
		    @Param("idEspacioFisico") Long idEspacioFisico,
		    @Param("dia") String dia,
		    @Param("horaInicio") String horaInicio,
		    @Param("horaFin") String horaFin,
		    @Param("salon") String salon,
		    @Param("ubicacion") List<Long> ubicacion,
		    @Param("fechaReserva") LocalDate fechaReserva,
		    @Param("recursos") List<Long> recursos,
		    @Param("recursosSize") Long recursosSize,
		    Pageable pageable
		);

		@Query("SELECT r FROM ReservaTemporalEntity r " +
		           "WHERE (:tipoIdentificacion IS NULL OR r.tipoIdentificacion = :tipoIdentificacion) " +
		           "AND (:numeroIdentificacion IS NULL OR r.numeroIdentificacion = :numeroIdentificacion)" +
				   "AND (:estado IS NULL OR r.estado.descripcion = :estado)" +
			       "AND r.periodo.idPeriodoAcademico = :idPeriodo")
		    Page<ReservaTemporalEntity> findByFilters(
		            @Param("tipoIdentificacion") String tipoIdentificacion,
		            @Param("numeroIdentificacion") String numeroIdentificacion,
		            @Param("estado") EstadoReservaEnum estado,
		            @Param("idPeriodo") Long idPeriodo,
		            Pageable pageable);
		
		@Query("""
			    SELECT r 
			    FROM ReservaTemporalEntity r
			    JOIN FETCH r.espacioFisico ef
			    LEFT JOIN FETCH ef.ubicacion u
			    WHERE r.idReserva = :reservaId
			""")
			Optional<ReservaTemporalEntity> findByIdWithDetails(@Param("reservaId") Long reservaId);

		@Query("SELECT r FROM ReservaTemporalEntity r " +
				"WHERE (r.estado.descripcion = 'RESERVA_APROBADA' OR r.estado.descripcion = 'RESERVA_PENDIENTE') " +
			       "AND (r.fechaReserva < :currentDate OR (r.fechaReserva = :currentDate AND r.horaFin <= :currentTime))")
			List<ReservaTemporalEntity> findReservasAprobadasVencidas(@Param("currentDate") LocalDate currentDate, 
				    @Param("currentTime") LocalTime currentTime);

		boolean existsByFechaReservaAndEspacioFisico_IdEspacioFisicoAndHoraInicioAndHoraFinAndEstado_IdEstado(
			    LocalDate fechaReserva,
			    Long idEspacioFisico,
			    LocalTime horaInicio,
			    LocalTime horaFin,
			    Long idEstado
			);

		@Query("SELECT r FROM ReservaTemporalEntity r WHERE r.periodo.idPeriodoAcademico = :idPeriodo")
	    List<ReservaTemporalEntity> findByPeriodoAcademico(@Param("idPeriodo") Long idPeriodo);

		@Query("SELECT r, l FROM ReservaTemporalEntity r " +
			       "JOIN LogReservasEntity l ON r.idReserva = l.reserva.idReserva " +
			       "WHERE r.periodo.idPeriodoAcademico = :idPeriodo")
			List<Object[]> findHistorialReservasPorPeriodo(@Param("idPeriodo") Long idPeriodo);

}
