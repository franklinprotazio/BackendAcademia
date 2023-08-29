package com.example.academia.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContatoDTO {

	private Long idContato;

	private String email;

	private String telefone;

	private String celular;

	private String outrosTelefones;
	
	private AlunoDTO aluno;

	
}
