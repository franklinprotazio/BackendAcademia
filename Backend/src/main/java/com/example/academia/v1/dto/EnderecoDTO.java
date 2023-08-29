package com.example.academia.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnderecoDTO {

	private Long idEndereco;	

	private String cep;
	
	private String rua;
	
	private String numero;
	
	private String bairro;

	private String cidade;

	private String estado;
	
	private AlunoDTO aluno;

	
}
