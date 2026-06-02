package com.duoc.plataformaEducativa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duoc.plataformaEducativa.dto.InscripcionRequestDTO;
import com.duoc.plataformaEducativa.dto.InscripcionResponseDTO;
import com.duoc.plataformaEducativa.model.CursoEntity;
import com.duoc.plataformaEducativa.model.InscripcionEntity;
import com.duoc.plataformaEducativa.model.UsuarioEntity;
import com.duoc.plataformaEducativa.repository.CursoRepository;
import com.duoc.plataformaEducativa.repository.InscripcionRepository;
import com.duoc.plataformaEducativa.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InscripcionService {

	private final InscripcionFileService inscripcionFile;

	private final InscripcionRepository inscripcionRepository;
	private final CursoRepository cursoRepository;
	private final UsuarioRepository usuarioRepository;

	public InscripcionService(InscripcionRepository inscripcionRepository, CursoRepository cursoRepository,
			UsuarioRepository usuarioRepository, InscripcionFileService inscriptionFile ) {
		this.inscripcionRepository = inscripcionRepository;
		this.cursoRepository = cursoRepository;
		this.usuarioRepository = usuarioRepository;
		this.inscripcionFile = inscriptionFile;
	}

	public List<InscripcionResponseDTO> listarInscripcionesPorCurso(Long cursoId) {
		return inscripcionRepository.findByCurso_Id(cursoId).stream().map(this::toDTO).toList();
	}

	public InscripcionResponseDTO crearInscripcion(InscripcionRequestDTO inscripcionRequestDTO) {
		InscripcionEntity inscripcion = toEntity(inscripcionRequestDTO);
		InscripcionEntity nuevaInscripcion = inscripcionRepository.save(inscripcion);
		InscripcionResponseDTO respuesta =  toDTO(nuevaInscripcion);
		try {
			String key = inscripcionFile.subirResumen(nuevaInscripcion);
			respuesta.setS3key(key);

		} catch (Exception e) {
			log.info("Error al subir resumen de inscipcion " + respuesta.getId() + ": " + e.getMessage());
		}

		return respuesta;
	}

	public boolean eliminarInscripcion(Long id) {
		if (inscripcionRepository.existsById(id)) {
			inscripcionRepository.deleteById(id);
			return true;
		}
		return false;
	}




	// to Entity / DTO
	private InscripcionResponseDTO toDTO(InscripcionEntity inscripcion) {
		return new InscripcionResponseDTO(
			inscripcion.getId(),
			inscripcion.getCurso() != null ? inscripcion.getCurso().getId() : null,
			inscripcion.getEstudiante() != null ? inscripcion.getEstudiante().getId() : null,
			inscripcion.getFechaInscripcion(),
			null
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
