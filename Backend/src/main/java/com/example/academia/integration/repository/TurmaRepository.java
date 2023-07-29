package com.example.academia.integration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.academia.core.entity.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

	@Modifying
	@Query(value = "DELETE FROM Turma as t WHERE t.academia_id = :academia_id", nativeQuery = true)
	void deletarTurmaPorAcademia(@Param("academia_id")Long idAcademia);
	
	@Query(value = "SELECT * FROM Turma as t WHERE t.academia_id = :idAcademia", nativeQuery = true)
	List<Turma> findTurmaPorAcademia(@Param("idAcademia") Long idAcademia);

	List<Turma> findByCurso(String cursoTurma);

	@Query("SELECT t FROM Turma t WHERE "
		       + "(:horario IS NULL OR :horario = '' OR t.horario = :horario) "
		       + "AND (:curso IS NULL OR :curso = '' OR t.curso = :curso)")
		List<Turma> getTurmas(@Param("horario") String horario, @Param("curso") String curso);

}