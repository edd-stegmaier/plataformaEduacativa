package com.duoc.LearningPlatformValidation.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionResponseDTO {

	private Long id;

	private Long cursoId;

	private Long estudianteId;

	private Date fechaInscripcion;
}