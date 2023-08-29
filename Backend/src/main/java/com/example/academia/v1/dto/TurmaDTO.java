package com.example.academia.v1.dto;

import java.util.List;

import com.example.academia.core.enums.StatusTurmaeNUM;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurmaDTO {
	
	private Long idTurma;
	
	private String curso;
	
	private String horario;
	
	//private Professor professor;
	
	private int qtoAluno;
	
	private StatusTurmaeNUM status;
	
	@NotNull
	private AcademiaRetornoDTO academia;
	
	private ProfessoresSemTurmaSemAcademiaESemAlunosDTO professor;
	
	private List<AlunoSemTurmaDTO> alunos;

	
	
}