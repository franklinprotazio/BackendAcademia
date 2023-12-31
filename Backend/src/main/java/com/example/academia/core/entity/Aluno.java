package com.example.academia.core.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ALUNO")
public class Aluno implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_aluno")
	private Long idAluno;

	@Column(name = "nome_aluno")
	private String nomeAluno;

	@Column(name = "data_matricula")
	private Date dataMatricula;

	@ManyToMany(mappedBy = "alunos", fetch = FetchType.EAGER)
	private List<Turma> turmas;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "academia_id")
	private Academia academia;

	@OneToMany(mappedBy = "aluno", fetch = FetchType.EAGER)
	private List<Endereco> enderecos;
	
	@OneToMany(mappedBy = "aluno", fetch = FetchType.EAGER)
	private List<Contato> contatos;
	

}