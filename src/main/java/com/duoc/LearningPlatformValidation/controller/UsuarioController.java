package com.duoc.LearningPlatformValidation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.LearningPlatformValidation.dto.UsuarioRequestDTO;
import com.duoc.LearningPlatformValidation.dto.UsuarioResponseDTO;
import com.duoc.LearningPlatformValidation.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	// obtiene lista de usuarios
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
		return ResponseEntity.ok(usuarioService.listarUsuarios());
	}

	// obtiene usuario por id
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioId(@PathVariable Long id) {
		UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioId(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}

	// registra nuevo usuario
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
		UsuarioResponseDTO nuevoUsuario = usuarioService.crearUsuario(usuarioRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
	}

	// modifica usuario existente
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id,
			@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
		UsuarioResponseDTO usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioRequestDTO);
		if (usuarioActualizado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuarioActualizado);
	}

	// elimina usuario por id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
		boolean eliminado = usuarioService.eliminarUsuario(id);
		if (eliminado) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}