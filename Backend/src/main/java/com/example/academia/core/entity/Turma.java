package com.example.academia.core.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TURMA")
public class Turma implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_turma")
	private Long idTurma;
	
	@Column(name = "curso")
	private String curso;
	
	@Column(name = "horario")
	private String horario;
	
	@Column(name = "nome_professor")
	private String nomeProfessor;
	
	@Column(name = "qto_Aluno")
	private int qtoAluno;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "aluno_id")
	private Aluno aluno;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "academia_id")
	private Academia academia;

}