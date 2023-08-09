package com.example.academia.core.service;

import java.util.List;

import com.example.academia.core.entity.Turma;
import com.example.academia.core.exception.TurmaInativaException;
import com.example.academia.core.exception.TurmaJaVinculadaException;
import com.example.academia.core.exception.TurmaNaoVinculadaException;
import com.example.academia.v1.dto.ProfessorDTO;
import com.example.academia.v1.dto.VincularProfessorDTO;

public interface ProfessorService {

	void deletarProfessorPorTurma(Turma turma);

	List<ProfessorDTO> buscarProfessorPorTurma(Turma turma);

	ProfessorDTO salvarProfessor(ProfessorDTO alunoDTO);

	ProfessorDTO buscarProfessorPorId(Long idProfessor);

	void deletarProfessorPorId(Long idProfessor);

	List<ProfessorDTO> buscarProfessorPorNome(String nomeProfessor);

	ProfessorDTO vincularProfessorEmTurma(VincularProfessorDTO vincularProfessorDTO) throws TurmaNaoVinculadaException, TurmaJaVinculadaException, TurmaInativaException;

	List<ProfessorDTO> getProfessores();
	

}
