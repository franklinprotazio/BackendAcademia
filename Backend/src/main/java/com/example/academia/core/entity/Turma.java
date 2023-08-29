package com.example.academia.core.entity;

import java.io.Serializable;
import java.util.List;

import com.example.academia.core.enums.StatusTurmaeNUM;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "professor_id")
	private Professor professor;
		
	@Column(name = "qto_Aluno")
	private int qtoAluno;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusTurmaeNUM status;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "turma_aluno",
	joinColumns = @JoinColumn(name = "turma_id"),
	inverseJoinColumns = @JoinColumn(name = "aluno_id"))
	private List<Aluno> alunos;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "academia_id")
	private Academia academia;

}