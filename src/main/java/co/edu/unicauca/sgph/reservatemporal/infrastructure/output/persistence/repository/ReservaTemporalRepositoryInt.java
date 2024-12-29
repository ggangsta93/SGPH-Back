package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
		            WHERE r.id_espacio_fisico = ef.id_espacio_fisico
		              AND r.fecha_reserva = :fechaReserva
		              AND franja.horaInicio < r.hora_fin
		              AND franja.horaFin    > r.hora_inicio
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
		            WHERE r.id_espacio_fisico = ef.id_espacio_fisico
		              AND r.fecha_reserva = :fechaReserva
		              AND franja.horaInicio < r.hora_fin
		              AND franja.horaFin    > r.hora_inicio
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
		    Pageable pageable
		);


}
