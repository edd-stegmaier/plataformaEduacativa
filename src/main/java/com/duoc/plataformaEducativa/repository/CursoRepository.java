package com.duoc.plataformaEducativa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.plataformaEducativa.model.CursoEntity;

@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, Long>{
    
}
