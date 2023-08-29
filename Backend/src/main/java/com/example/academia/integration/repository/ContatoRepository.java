package com.example.academia.integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.academia.core.entity.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

}
