package co.edu.unicauca.sgph.horario.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEspacioEntity;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEspacioPk;

public interface HorarioEspacioRepositoryInt extends JpaRepository<HorarioEspacioEntity, HorarioEspacioPk> {
					    	
    public HorarioEspacioEntity findByHorarioIdHorarioAndEspacioFisicoIdEspacioFisico(Long idHorario, Long idEspacioFisico);

}
