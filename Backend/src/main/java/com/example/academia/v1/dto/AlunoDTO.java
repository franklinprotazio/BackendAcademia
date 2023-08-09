package com.example.academia.v1.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlunoDTO {
	
	private Long idAluno;
	
	@NotBlank
	private String nomeAluno;
	
	private Date dataMatricula;
	
	private AcademiaRetornoDTO academia;
	
	private TurmaRetornoDTOSemQtoAluno turma;
	
	private List<TurmaSemAcademiaDTO> turmas;

}