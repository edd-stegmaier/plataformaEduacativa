package com.duoc.plataformaEducativa.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoEntity{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String nombre;

	@Column(nullable = false, length = 255)
	private String descripcion;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "profesor_id", nullable = false)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private UsuarioEntity profesor;

	@OneToMany(mappedBy = "curso")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<InscripcionEntity> inscripciones;

}
