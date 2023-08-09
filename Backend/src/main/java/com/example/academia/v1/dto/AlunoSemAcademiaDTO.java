package com.example.academia.v1.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlunoSemAcademiaDTO {

	private Long idAluno;

	private String nomeAluno;

	private Instant dataMatricula;
	
	private TurmaSemAcademiaDTO turma;
	
	private List<TurmaSemAcademiaDTO> turmas;

}
