package com.duoc.LearningPlatformValidation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.LearningPlatformValidation.dto.InscripcionRequestDTO;
import com.duoc.LearningPlatformValidation.dto.InscripcionResponseDTO;
import com.duoc.LearningPlatformValidation.service.InscripcionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/inscripciones")
public class InscripcionController {

	private final InscripcionService inscripcionService;

	public InscripcionController(InscripcionService inscripcionService) {
		this.inscripcionService = inscripcionService;
	}

	// obtiene inscripciones por curso
	@GetMapping("/curso/{cursoId}")
	public ResponseEntity<List<InscripcionResponseDTO>> listarInscripcionesPorCurso(@PathVariable Long cursoId) {
		return ResponseEntity.ok(inscripcionService.listarInscripcionesPorCurso(cursoId));
	}

	// registra nueva inscripcion
	@PostMapping
	public ResponseEntity<InscripcionResponseDTO> crearInscripcion(
			@Valid @RequestBody InscripcionRequestDTO inscripcionRequestDTO) {
		InscripcionResponseDTO nuevaInscripcion = inscripcionService.crearInscripcion(inscripcionRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcion);
	}

	// elimina inscripcion por id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long id) {
		boolean eliminado = inscripcionService.eliminarInscripcion(id);
		if (eliminado) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}