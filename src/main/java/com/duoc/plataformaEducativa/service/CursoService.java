package com.duoc.plataformaEducativa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duoc.plataformaEducativa.dto.CursoRequestDTO;
import com.duoc.plataformaEducativa.dto.CursoResponseDTO;
import com.duoc.plataformaEducativa.model.CursoEntity;
import com.duoc.plataformaEducativa.model.UsuarioEntity;
import com.duoc.plataformaEducativa.repository.CursoRepository;
import com.duoc.plataformaEducativa.repository.UsuarioRepository;

@Service
public class CursoService {
    
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public CursoService(CursoRepository cursoRepository, UsuarioRepository usuarioRepository) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // obtiene lista de cursos
    public List<CursoResponseDTO> listarCursos() {
        return cursoRepository.findAll().stream().map(this::toDTO).toList();
    }

    // obtiene curso por id
    public CursoResponseDTO obtenerCursoId(Long id) {
        return cursoRepository.findById(id).map(this::toDTO).orElse(null);
    }

    // registra nuevo curso
    public CursoResponseDTO crearCurso(CursoRequestDTO cursoRequestDTO) {
        CursoEntity curso = toEntity(cursoRequestDTO);
        return toDTO(cursoRepository.save(curso));
    }

    // modifica curso existente
    public CursoResponseDTO actualizarCurso(Long id, CursoRequestDTO cursoRequestDTO) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setNombre(cursoRequestDTO.getNombre());
            curso.setDescripcion(cursoRequestDTO.getDescripcion());
            if (!curso.getProfesor().getId().equals(cursoRequestDTO.getProfesorId())) {
                UsuarioEntity nuevoProfesor = usuarioRepository.findById(cursoRequestDTO.getProfesorId())
                    .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado con id: " + cursoRequestDTO.getProfesorId()));
                curso.setProfesor(nuevoProfesor);
            }
            return toDTO(cursoRepository.save(curso));
        }).orElse(null);
    }

    // elimina curso por id
    public boolean eliminarCurso(Long id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // mapea entidad a DTO
    private CursoResponseDTO toDTO(CursoEntity curso) {
        return new CursoResponseDTO(
            curso.getId(),
            curso.getNombre(),
            curso.getDescripcion(),
            curso.getProfesor() != null ? curso.getProfesor().getId() : null
        );
    }

    // mapea DTO a entidad
    private CursoEntity toEntity(CursoRequestDTO cursoRequestDTO) {
        UsuarioEntity profesor = usuarioRepository.findById(cursoRequestDTO.getProfesorId())
            .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado con id: " + cursoRequestDTO.getProfesorId()));

        CursoEntity curso = new CursoEntity();
        curso.setNombre(cursoRequestDTO.getNombre());
        curso.setDescripcion(cursoRequestDTO.getDescripcion());
        curso.setProfesor(profesor);
        return curso;
    }
}
