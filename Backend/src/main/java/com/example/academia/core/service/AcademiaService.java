package com.example.academia.core.service;

import java.util.List;

import com.example.academia.v1.dto.AcademiaDTO;

public interface AcademiaService {
	
	List<AcademiaDTO> getAcademias();
	
	AcademiaDTO salvarAcademia(AcademiaDTO academiaDTO);
	
	void deletarAcademia(Long idAcademia);
	
	AcademiaDTO buscarAcademiaPorId(Long id);
	
	AcademiaDTO atualizarAcademia(Long idAcademia, AcademiaDTO academia);

}