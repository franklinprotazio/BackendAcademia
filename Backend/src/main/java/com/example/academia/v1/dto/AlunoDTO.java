package com.example.academia.v1.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlunoDTO {
	
	private Long idAluno;
	
	private String nomeAluno;
	
	private Date dataMatricula;
	
	private TurmaRetornoDTOSemQtoAluno turma;
	
	private AcademiaRetornoDTO academia;

}