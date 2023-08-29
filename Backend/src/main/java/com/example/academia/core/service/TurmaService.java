package com.example.academia.core.service;

import java.util.List;

import com.example.academia.core.entity.Academia;
import com.example.academia.core.entity.Turma;
import com.example.academia.core.enums.StatusTurmaeNUM;
import com.example.academia.core.exception.AlunoMatriculadoException;
import com.example.academia.core.exception.AlunoNaoMatriculadoException;
import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.exception.TurmaInativaException;
import com.example.academia.v1.dto.MatriculaDTO;
import com.example.academia.v1.dto.TurmaDTO;

public interface TurmaService {
	void deletarTurmaPorAcademia(Academia academia);

	List<TurmaDTO> buscarTurmaPorAcademia(Academia academia);

	TurmaDTO salvarTurma(TurmaDTO turmaDTO);

	TurmaDTO buscarTurmaPorId(Long idTurma);

	void deletarTurmaPorId(Long idTurma);

	List<TurmaDTO> buscarTurmaPorCurso(String cursoTurma);

	List<TurmaDTO> getTurmas(String horario, String nome);

	TurmaDTO matricularAlunoEmTurma(MatriculaDTO matriculaDTO) throws EntidadeNaoEncontradaException, TurmaInativaException, AlunoNaoMatriculadoException, AlunoMatriculadoException;

	StatusTurmaeNUM ativarTurma(Long idTurma);
	

//	TurmaDTO matricularAlunoEmTurma(Long idTurma, Long idAluno)  throws EntidadeNaoEncontradaException, TurmaInativaException, AlunoNaoMatriculadoException ;
	
	
}