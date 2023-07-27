package com.example.academia.v1.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurmaDTO {
	
	private Long idTurma;
	
	private String curso;
	
	private Date horario;
	
	private String nomeProfessor;

	private AcademiaRetornoDTO academia;
}
