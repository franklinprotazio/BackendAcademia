package com.example.academia.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcademiaRetornoDTO {
	
	private Long idAcademia;
	
	private String nomeAcademia;

}
