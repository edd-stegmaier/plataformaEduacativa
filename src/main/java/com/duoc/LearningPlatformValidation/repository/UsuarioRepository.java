package com.duoc.LearningPlatformValidation.repository;

import com.duoc.LearningPlatformValidation.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{
    
}
