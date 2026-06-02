package com.duoc.plataformaEducativa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duoc.plataformaEducativa.dto.UsuarioRequestDTO;
import com.duoc.plataformaEducativa.dto.UsuarioResponseDTO;
import com.duoc.plataformaEducativa.model.UsuarioEntity;
import com.duoc.plataformaEducativa.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public List<UsuarioResponseDTO> listarUsuarios() {
		return usuarioRepository.findAll().stream().map(this::toDTO).toList();
	}

	public UsuarioResponseDTO obtenerUsuarioId(Long id) {
		return usuarioRepository.findById(id).map(this::toDTO).orElse(null);
	}

	public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuarioRequestDTO) {
		UsuarioEntity usuario = toEntity(usuarioRequestDTO);
		return toDTO(usuarioRepository.save(usuario));
	}

	public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO) {
		return usuarioRepository.findById(id).map(usuario -> {
			usuario.setNombre(usuarioRequestDTO.getNombre());
			usuario.setCorreo(usuarioRequestDTO.getCorreo());
			usuario.setContrasena(usuarioRequestDTO.getContrasena());
			usuario.setRol(usuarioRequestDTO.getRol());
			return toDTO(usuarioRepository.save(usuario));
		}).orElse(null);
	}

	public boolean eliminarUsuario(Long id) {
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
			return true;
		}
		return false;
	}

	private UsuarioResponseDTO toDTO(UsuarioEntity usuario) {
		return new UsuarioResponseDTO(
			usuario.getId(),
			usuario.getNombre(),
			usuario.getCorreo(),
			usuario.getContrasena(),
			usuario.getRol()
		);
	}

	private UsuarioEntity toEntity(UsuarioRequestDTO usuarioRequestDTO) {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNombre(usuarioRequestDTO.getNombre());
		usuario.setCorreo(usuarioRequestDTO.getCorreo());
		usuario.setContrasena(usuarioRequestDTO.getContrasena());
		usuario.setRol(usuarioRequestDTO.getRol());
		return usuario;
	}
}
