package com.example.academia.v1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.academia.core.service.AlunoService;
import com.example.academia.integration.repository.AlunoRepository;
import com.example.academia.v1.dto.AlunoDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/aluno")
public class AlunoController {

	@Autowired
	AlunoService alunoService;

	@Autowired
	AlunoRepository alunoRepository;

	@GetMapping()
	public ResponseEntity<Object> getAlunos(
			@RequestParam(name = "dataInicio", required = false) String dataInicio,
			@RequestParam(name = "dataFim", required = false) String dataFim,
			@RequestParam(name = "nome", required = false) String nome) {
		
		List<AlunoDTO> lista = alunoService.getAlunos(dataInicio, dataFim, nome);
		if (lista.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(lista);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foram encontrados alunos com o filtro informado");
	}
	
	@PostMapping()
	public ResponseEntity<Object> salvarAluno(@RequestBody @Valid AlunoDTO alunoDTO){
		
		AlunoDTO alunoRetornoDTo = alunoService.salvarAluno(alunoDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(alunoRetornoDTo);
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarAluno(@PathVariable(value = "id") Long idAluno){
		alunoService.deletarAlunoPorId(idAluno);
		
		return ResponseEntity.status(HttpStatus.OK).body("Aluno deletado com sucesso");
	}

	@GetMapping("/{nomeAluno}")
	public ResponseEntity<Object> buscarAluno(@PathVariable(value = "nomeAluno") String nomeAluno) {
		List<AlunoDTO> alunosDTO = new ArrayList<>();
		alunosDTO = alunoService.buscarAlunoPorNome(nomeAluno);
		return ResponseEntity.status(HttpStatus.OK).body(alunosDTO);
	}
}