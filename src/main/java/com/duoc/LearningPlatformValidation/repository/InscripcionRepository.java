package com.duoc.LearningPlatformValidation.repository;

import java.util.List;

import com.duoc.LearningPlatformValidation.model.InscripcionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<InscripcionEntity, Long>{

	List<InscripcionEntity> findByCurso_Id(Long cursoId);
}
