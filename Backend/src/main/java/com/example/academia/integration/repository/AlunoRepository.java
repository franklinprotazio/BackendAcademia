package com.example.academia.integration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.academia.core.entity.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	List<Aluno> findByNomeAluno (String nomeAluno);

	
	// Selecione da tabela Aluno onde
	@Query(value = "SELECT * FROM TB_ALUNO as t WHERE t.turma_id = :turma_id", nativeQuery = true)
	List<Aluno> findAlunoPorTurma(@Param("turma_id") Long id);
	
	@Query(value = "SELECT * FROM Aluno as t WHERE t.academia_id = :idAcademia", nativeQuery = true)
	List<Aluno> findAlunoPorAcademia(@Param("idAcademia") Long idAcademia);
	
	@Modifying
	@Query(value = "DELETE FROM Aluno as t WHERE t.turma_id = :turma_id", nativeQuery = true)
	void deletarAlunoPorTurma(@Param ("turma_id") Long idTurma);
	
//	boolean deleteAllAcademia(Academia academia);
	
	@Query("SELECT t FROM Aluno t WHERE (:inicio IS NULL OR :inicio = '' OR t.dataMatricula >= PARSEDATETIME(:inicio, 'yyyy-MM-dd')) "
			+ "AND (:fim IS NULL OR :fim = '' OR t.dataMatricula <= PARSEDATETIME(:fim, 'yyyy-MM-dd')) "
			+ "AND (:nome IS NULL OR :nome = '' OR t.nomeAluno = :nome)")
		List<Aluno> getAlunos(@Param("inicio") String inicio, @Param("fim") String fim, @Param("nome") String nome);
	
}

