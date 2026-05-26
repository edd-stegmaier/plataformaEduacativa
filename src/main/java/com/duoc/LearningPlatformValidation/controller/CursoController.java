package com.duoc.LearningPlatformValidation.controller;

import com.duoc.LearningPlatformValidation.dto.CursoRequestDTO;
import com.duoc.LearningPlatformValidation.dto.CursoResponseDTO;
import com.duoc.LearningPlatformValidation.service.CursoService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/cursos")
public class CursoController {
    
    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // obtiene lista de cursos
    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    // obtiene curso por id
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> obtenerCursoId(@PathVariable Long id) {
        CursoResponseDTO curso = cursoService.obtenerCursoId(id);
        if (curso == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(curso);
    }

    // registra nuevo curso
    @PostMapping
    public ResponseEntity<CursoResponseDTO> crearCurso(@Valid @RequestBody CursoRequestDTO cursoRequestDTO) {
        CursoResponseDTO nuevoCurso = cursoService.crearCurso(cursoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCurso);
    }

    // modifica curso existente
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> actualizarCurso(@PathVariable Long id, @RequestBody CursoRequestDTO cursoRequestDTO) {
        CursoResponseDTO cursoActualizado = cursoService.actualizarCurso(id, cursoRequestDTO);
        if (cursoActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cursoActualizado);
    }

    // elimina curso por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        boolean eliminado = cursoService.eliminarCurso(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
