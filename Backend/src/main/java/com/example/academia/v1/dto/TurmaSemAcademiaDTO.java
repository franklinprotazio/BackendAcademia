package com.example.academia.v1.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurmaSemAcademiaDTO {

	private Long idTurma;

	private String curso;

	private String horario;

	private String nomeProfessor;
	
	private int qtoAluno;

	private List<AlunoSemAcademiaDTO> listaAlunos;
	

	
}
