package com.example.academia.v1.dto;

import java.util.List;

import com.example.academia.core.entity.Professor;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurmaRetornoDTOSemQtoAluno {
	private Long idTurma;

	private String curso;

	private String horario;
	
	private Professor professor;
	
	private List<TurmaSemAcademiaDTO> turmas;

}
