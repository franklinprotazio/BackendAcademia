package com.example.academia.core.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "PROFESSOR")
public class Professor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_professor")
	private Long idProfessor;
	
	@Column(name = "nome_Professor")
	private String nomeProfessor;
	
	@OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
	private List<Turma> turmas;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "academia_id")
	private Academia academia;
	
}
