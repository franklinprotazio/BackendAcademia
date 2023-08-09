package com.example.academia.v1.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfessorSemAcademiaDTO {

	private Long idProfessor;

	private String nomeProfessor;
	
	private TurmaSemAcademiaDTO turma;
	
	private List<TurmaSemAcademiaDTO> turmas;
	
}
