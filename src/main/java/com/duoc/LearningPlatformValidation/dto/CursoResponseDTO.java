package com.duoc.LearningPlatformValidation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoResponseDTO {

	private Long id;

	private String nombre;

	private String descripcion;

	private Long profesorId;
}