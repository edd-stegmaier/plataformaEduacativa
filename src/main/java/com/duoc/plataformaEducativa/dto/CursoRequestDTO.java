package com.duoc.plataformaEducativa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoRequestDTO {

	@NotBlank(message = "El nombre no puede estar vacio")
	@Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
	private String nombre;

	@NotBlank(message = "La descripcion no puede estar vacia")
	@Size(min = 3, max = 255, message = "La descripcion debe tener entre 3 y 255 caracteres")
	private String descripcion;

	@NotNull(message = "El profesorId no puede ser nulo")
	private Long profesorId;
}