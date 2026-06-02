package com.duoc.plataformaEducativa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.plataformaEducativa.model.InscripcionEntity;

@Repository
public interface InscripcionRepository extends JpaRepository<InscripcionEntity, Long>{

	List<InscripcionEntity> findByCurso_Id(Long cursoId);
}
