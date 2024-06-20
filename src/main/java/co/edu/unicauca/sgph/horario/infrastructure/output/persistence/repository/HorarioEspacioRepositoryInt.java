package co.edu.unicauca.sgph.horario.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEspacioEntity;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEspacioPk;

public interface HorarioEspacioRepositoryInt extends JpaRepository<HorarioEspacioEntity, HorarioEspacioPk> {
					    	
    public HorarioEspacioEntity findByHorarioIdHorarioAndEspacioFisicoIdEspacioFisico(Long idHorario, Long idEspacioFisico);

}
