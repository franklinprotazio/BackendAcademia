package com.example.academia.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurmaRetornoDTO {

	private Long idTurma;

	private String curso;

	private String horario;

	private String nomeProfessor;
	
	private int qtoAluno;

}
