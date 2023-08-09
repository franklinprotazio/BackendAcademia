package com.example.academia.v1.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurmaRetornoDTOSemQtoProfessor {
	private Long idProfessor;

	private String nomeProfessor;
	
	private List<ProfessorDTO> professores;
	
	private List<AlunoSemTurmaDTO> alunos;

}
