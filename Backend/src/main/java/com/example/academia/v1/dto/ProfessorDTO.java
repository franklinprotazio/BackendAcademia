package com.example.academia.v1.dto;

import java.util.List;

import com.example.academia.core.entity.Aluno;
import com.example.academia.core.entity.Turma;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfessorDTO {

	private Long idProfessor;

	@NotBlank
	private String nomeProfessor;
	
	private TurmaRetornoDTOSemQtoProfessor turma;

	private List<Turma> turmas;
	
	private List<Aluno> alunos;
	
	@NotNull
	private AcademiaRetornoDTO academia;
}
