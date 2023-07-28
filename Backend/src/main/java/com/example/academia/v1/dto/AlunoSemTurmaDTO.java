package com.example.academia.v1.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlunoSemTurmaDTO {

	private Long idAluno;

	private String nomeAluno;

	private Date dataMatricula;

}
