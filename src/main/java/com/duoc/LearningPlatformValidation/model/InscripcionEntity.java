package com.duoc.LearningPlatformValidation.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "inscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "curso_id", nullable = false)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private CursoEntity curso;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "estudiante_id", nullable = false)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private UsuarioEntity estudiante;

	@Column(nullable = false)
	private Date fechaInscripcion;
}
