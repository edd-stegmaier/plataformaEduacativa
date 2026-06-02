package com.duoc.plataformaEducativa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

	@NotBlank(message = "El nombre no puede estar vacio")
	@Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
	private String nombre;

	@NotBlank(message = "El correo no puede estar vacio")
	@Email(message = "El correo debe ser valido")
	private String correo;

	@NotBlank(message = "La contrasena no puede estar vacia")
	@Size(min = 3, max = 100, message = "La contrasena debe tener entre 3 y 100 caracteres")
	private String contrasena;

	@NotBlank(message = "El rol no puede estar vacio")
	@Size(min = 3, max = 100, message = "El rol debe tener entre 3 y 100 caracteres")
	private String rol;
}
