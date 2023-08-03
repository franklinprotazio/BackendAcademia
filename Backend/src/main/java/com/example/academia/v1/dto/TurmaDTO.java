package com.example.academia.v1.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurmaDTO {
	
	private Long idTurma;
	
	private String curso;
	
	private String horario;
	
	private String nomeProfessor;
	
	private int qtoAluno;

	@NotNull
	private AcademiaRetornoDTO academia;
	
	private List<AlunoSemTurmaDTO> aluno;
}