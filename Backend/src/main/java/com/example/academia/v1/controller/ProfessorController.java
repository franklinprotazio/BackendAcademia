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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.academia.core.exception.AlunoMatriculadoException;
import com.example.academia.core.exception.AlunoNaoMatriculadoException;
import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.exception.TurmaInativaException;
import com.example.academia.core.service.ProfessorService;
import com.example.academia.integration.repository.ProfessorRepository;
import com.example.academia.v1.dto.MatriculaDTO;
import com.example.academia.v1.dto.ProfessorDTO;
import com.example.academia.v1.dto.TurmaDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/professor")
public class ProfessorController {

	@Autowired
	ProfessorService professorService;

	@Autowired
	ProfessorRepository professorRepository;

	@GetMapping()
	public ResponseEntity<Object> getProfessor() {
		
		List<ProfessorDTO> lista = professorService.getProfessores();
		
		if (lista != null && lista.isEmpty() ) {
			return ResponseEntity.status(HttpStatus.OK).body(lista);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foram encontrados Professores com o filtro informado");
	}
	
	@PostMapping()
	public ResponseEntity<Object> salvarProfessor(@RequestBody @Valid ProfessorDTO professorDTO){
			
		ProfessorDTO ProfessorRetornoDTo = professorService.salvarProfessor(professorDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(ProfessorRetornoDTo);
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarProfessor(@PathVariable(value = "id") Long idProfessor){
		professorService.deletarProfessorPorId(idProfessor);
		
		return ResponseEntity.status(HttpStatus.OK).body("Professor deletado com sucesso");
	}

	@GetMapping("/{nomeProfessor}")
	public ResponseEntity<Object> buscarProfessor(@PathVariable(value = "nomeProfessor") String nomeProfessor) {
		List<ProfessorDTO> ProfessorsDTO = new ArrayList<>();
		ProfessorsDTO = professorService.buscarProfessorPorNome(nomeProfessor);
		return ResponseEntity.status(HttpStatus.OK).body(ProfessorsDTO);
	}
	

	

}

