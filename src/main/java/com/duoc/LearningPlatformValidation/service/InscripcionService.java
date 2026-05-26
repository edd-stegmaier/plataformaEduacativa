package com.duoc.LearningPlatformValidation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duoc.LearningPlatformValidation.dto.InscripcionRequestDTO;
import com.duoc.LearningPlatformValidation.dto.InscripcionResponseDTO;
import com.duoc.LearningPlatformValidation.model.CursoEntity;
import com.duoc.LearningPlatformValidation.model.InscripcionEntity;
import com.duoc.LearningPlatformValidation.model.UsuarioEntity;
import com.duoc.LearningPlatformValidation.repository.CursoRepository;
import com.duoc.LearningPlatformValidation.repository.InscripcionRepository;
import com.duoc.LearningPlatformValidation.repository.UsuarioRepository;

@Service
public class InscripcionService {

	private final InscripcionRepository inscripcionRepository;
	private final CursoRepository cursoRepository;
	private final UsuarioRepository usuarioRepository;

	public InscripcionService(InscripcionRepository inscripcionRepository, CursoRepository cursoRepository,
			UsuarioRepository usuarioRepository) {
		this.inscripcionRepository = inscripcionRepository;
		this.cursoRepository = cursoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	public List<InscripcionResponseDTO> listarInscripcionesPorCurso(Long cursoId) {
		return inscripcionRepository.findByCurso_Id(cursoId).stream().map(this::toDTO).toList();
	}

	public InscripcionResponseDTO crearInscripcion(InscripcionRequestDTO inscripcionRequestDTO) {
		InscripcionEntity inscripcion = toEntity(inscripcionRequestDTO);
		return toDTO(inscripcionRepository.save(inscripcion));
	}

	public boolean eliminarInscripcion(Long id) {
		if (inscripcionRepository.existsById(id)) {
			inscripcionRepository.deleteById(id);
			return true;
		}
		return false;
	}

	private InscripcionResponseDTO toDTO(InscripcionEntity inscripcion) {
		return new InscripcionResponseDTO(
			inscripcion.getId(),
			inscripcion.getCurso() != null ? inscripcion.getCurso().getId() : null,
			inscripcion.getEstudiante() != null ? inscripcion.getEstudiante().getId() : null,
			inscripcion.getFechaInscripcion()
		);
	}

	private InscripcionEntity toEntity(InscripcionRequestDTO inscripcionRequestDTO) {
		CursoEntity curso = cursoRepository.findById(inscripcionRequestDTO.getCursoId())
			.orElseThrow(() -> new IllegalArgumentException("Curso no encontrado con id: " + inscripcionRequestDTO.getCursoId()));
		UsuarioEntity estudiante = usuarioRepository.findById(inscripcionRequestDTO.getEstudianteId())
			.orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado con id: " + inscripcionRequestDTO.getEstudianteId()));

		InscripcionEntity inscripcion = new InscripcionEntity();
		inscripcion.setCurso(curso);
		inscripcion.setEstudiante(estudiante);
		inscripcion.setFechaInscripcion(inscripcionRequestDTO.getFechaInscripcion());
		return inscripcion;
	}
}
