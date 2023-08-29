package com.example.academia.v1.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcademiaDTO {
	
	private Long idAcademia;
	
	@NotBlank
	private String nomeAcademia;
	
	private List<ProfessorSemAcademiaDTO> professores;
	
	private List<TurmaSemAcademiaDTO> turmas;
	
	private List<AlunoSemAcademiaDTO> listaAlunos; 

}
