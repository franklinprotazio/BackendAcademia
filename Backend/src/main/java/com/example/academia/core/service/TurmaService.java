package com.example.academia.core.service;

import java.util.List;

import com.example.academia.core.entity.Academia;
import com.example.academia.v1.dto.TurmaDTO;

public interface TurmaService {
	void deletarTurmaPorAcademia(Academia academia);

	List<TurmaDTO> buscarTurmaPorAcademia(Academia academia);

	TurmaDTO salvarTurma(TurmaDTO turmaDTO);

	TurmaDTO buscarTurmaPorId(Long idTurma);

	void deletarTurmaPorId(Long idTurma);

	List<TurmaDTO> buscarTurmaPorCurso(String cursoTurma);

	List<TurmaDTO> getTurmas(String horario, String nome);
	
	
}
