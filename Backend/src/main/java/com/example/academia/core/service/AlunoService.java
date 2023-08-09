package com.example.academia.core.service;

import java.util.List;

import com.example.academia.core.entity.Turma;
import com.example.academia.v1.dto.AlunoDTO;
import com.example.academia.v1.dto.MatriculaDTO;
import com.example.academia.v1.dto.TurmaDTO;

public interface AlunoService {

	void deletarAlunoPorTurma(Turma turma);

	List<AlunoDTO> buscarAlunoPorTurma(Turma turma);

	AlunoDTO salvarAluno(AlunoDTO alunoDTO);

	AlunoDTO buscarAlunoPorId(Long idAluno);

	void deletarAlunoPorId(Long idAluno);

	List<AlunoDTO> buscarAlunoPorNome(String nomeAluno);

	List<AlunoDTO> getAlunos(String dataInicio, String dataFim, String nome);
	

}
