package com.duoc.plataformaEducativa.dto;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionRequestDTO {

	@NotNull(message = "El cursoId no puede ser nulo")
	private Long cursoId;

	@NotNull(message = "El estudianteId no puede ser nulo")
	private Long estudianteId;

	@NotNull(message = "La fecha de inscripcion no puede ser nula")
	private Date fechaInscripcion;
}