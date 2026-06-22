package com.duoc.plataformaEducativa.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.plataformaEducativa.dto.InscripcionRequestDTO;
import com.duoc.plataformaEducativa.dto.InscripcionResponseDTO;
import com.duoc.plataformaEducativa.service.InscripcionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/inscripciones")
public class InscripcionController {

	private final InscripcionService inscripcionService;

	public InscripcionController(InscripcionService inscripcionService) {
		this.inscripcionService = inscripcionService;
	}

	// obtiene todas las inscripciones
	@GetMapping
	public ResponseEntity<List<InscripcionResponseDTO>> listarInscripciones() {
		return ResponseEntity.ok(inscripcionService.listarInscripciones());
	}

	// obtiene inscripciones por curso
	@GetMapping("/curso/{cursoId}")
	public ResponseEntity<List<InscripcionResponseDTO>> listarInscripcionesPorCurso(@PathVariable Long cursoId) {
		return ResponseEntity.ok(inscripcionService.listarInscripcionesPorCurso(cursoId));
	}

	//Descargar resumen inscripcion
	@GetMapping("/resumen/{id}")
	public ResponseEntity<byte[]> obtenerResumenInscripcion(@PathVariable Long id){
		byte[] archivo = inscripcionService.generarResumenInscripcion(id);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "resumen-inscripcion-" + id + ".txt" + "\"")
        .contentType(MediaType.APPLICATION_OCTET_STREAM).body(archivo);
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