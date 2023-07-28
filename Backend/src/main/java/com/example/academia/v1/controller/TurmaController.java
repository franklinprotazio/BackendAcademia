package com.example.academia.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.academia.core.service.TurmaService;
import com.example.academia.integration.repository.TurmaRepository;
import com.example.academia.v1.dto.TurmaDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/turma")
public class TurmaController {
	
	@Autowired
	TurmaService turmaService;
	
	@Autowired
	TurmaRepository turmaRepository;
	
	@GetMapping
	public ResponseEntity<Object> getTurmas(
			@RequestParam(name = "horario", required = false) String horario,
			@RequestParam(name = "curso", required = false) String curso){
		
		List<TurmaDTO> turmas = turmaService.getTurmas(horario, curso);
		if(turmas.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(turmas);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foram encontradas turmas disponiveis");
	}
	
	@PostMapping
	public ResponseEntity<Object> salvarTurma(@RequestBody @Valid TurmaDTO turmaDTO){
		
		TurmaDTO turmaRetornoDTO = turmaService.salvarTurma(turmaDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(turmaRetornoDTO);
	}

}


