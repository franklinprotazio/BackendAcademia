package com.example.academia.integration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.academia.core.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

	List<Professor> findByNomeProfessor (String nomeProfessor);

	
	// Selecione da tabela Professor onde
	@Query(value = "SELECT * FROM Professor as t WHERE t.turma_id = :idTurma", nativeQuery = true)
	List<Professor> findProfessorPorTurma(@Param("idTurma") Long idTurma);
	
	@Query(value = "SELECT * FROM Professor as t WHERE t.academia_id = :idAcademia", nativeQuery = true)
	List<Professor> findProfessorPorAcademia(@Param("idAcademia") Long idAcademia);
	
	@Modifying
	@Query(value = "DELETE FROM Professor as t WHERE t.turma_id = :idTurma", nativeQuery = true)
	void deletarProfessorPorTurma(@Param ("idTurma") Long idTurma);
	
//	boolean deleteAllAcademia(Academia academia);
	
	@Query("SELECT t FROM Professor t WHERE (:nome IS NULL OR :nome = '' OR t.nomeProfessor = :nome)")
		List<Professor> getProfessores(@Param("nome") String nome);
	
}