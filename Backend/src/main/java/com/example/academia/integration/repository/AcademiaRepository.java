package com.example.academia.integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.academia.core.entity.Academia;

public interface AcademiaRepository extends JpaRepository<Academia, Long>{
	
	Optional<Academia> findById(Long idAcademia);

}
