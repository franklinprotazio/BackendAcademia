package com.example.academia.core.entity;

import java.io.Serializable;
import java.util.Date;

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
	private Date horario;
	
	@Column(name = "nome_professor")
	private String nomeProfessor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "academia_id")
	private Academia academia;

}
