package com.example.academia.v1.dto;

import com.example.academia.core.entity.Aluno;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurmaRetornoDTO {

	private Long idTurma;

	private String curso;

	private String horario;

	private String nomeProfessor;
	
	private Aluno aluno;
	
	private int qtoAluno;
	
	private int qtoProfessor;

}
