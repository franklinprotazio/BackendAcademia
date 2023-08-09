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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ACADEMIA")
public class Academia implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_academia")
	private Long idAcademia;
	
	@Column(name = "nome_academia")
	private String nomeAcademia;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "academia_professor",
	joinColumns = @JoinColumn(name = "academia_id"),
	inverseJoinColumns = @JoinColumn(name = "professor_id"))
	private List<Turma> turmas;

}