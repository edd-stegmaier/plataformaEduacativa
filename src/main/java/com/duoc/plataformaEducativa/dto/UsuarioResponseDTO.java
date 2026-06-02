package com.duoc.plataformaEducativa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

	private Long id;

	private String nombre;

	private String correo;

	private String contrasena;

	private String rol;
}