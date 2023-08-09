package com.example.academia.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.academia.core.service.AlunoService;
import com.example.academia.core.service.TurmaService;
import com.example.academia.integration.repository.TurmaRepository;
import com.example.academia.v1.dto.MatriculaDTO;
import com.example.academia.v1.dto.TurmaDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/turma")
public class TurmaController {

	@Autowired
	TurmaService turmaService;

	@Autowired
	AlunoService alunoService;

	@Autowired
	TurmaRepository turmaRepository;

	@GetMapping
	public ResponseEntity<Object> getTurmas(@RequestParam(name = "horario", required = false) String horario,
			@RequestParam(name = "curso", required = false) String curso) {

		List<TurmaDTO> turmas = turmaService.getTurmas(horario, curso);
		if (turmas.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(turmas);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foram encontradas turmas disponiveis");
	}

	@PostMapping
	public ResponseEntity<Object> salvarTurma(@RequestBody @Valid TurmaDTO turmaDTO) {

		TurmaDTO turmaRetornoDTO = turmaService.salvarTurma(turmaDTO);

		return ResponseEntity.status(HttpStatus.OK).body(turmaRetornoDTO);
	}

	@PutMapping("/matricular")
	public ResponseEntity<Object> matricularAlunoEmTurma(@RequestBody @Valid MatriculaDTO matriculaDTO) throws AlunoMatriculadoException  {
		TurmaDTO turmaRetorno;
		try {
			turmaRetorno = turmaService.matricularAlunoEmTurma(matriculaDTO);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno com id " + matriculaDTO.getIdAluno()
					+ " não encontrado ou turma com id " + matriculaDTO.getIdTurma() + " inativa");
		} catch (AlunoNaoMatriculadoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Não foi possivel matricular o aluno, pois o aluno já está matriculado em duas turmas");
		} catch (TurmaInativaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma inativa ou indisponivel");
		}

		return ResponseEntity.status(HttpStatus.OK).body("Aluno matriculado");
	}

	@PutMapping("/ativarturma/{id}")
	public ResponseEntity<Object> ativarTurma(@PathVariable(value = "id") Long idTurma) {
		
		try {
			turmaService.ativarTurma(idTurma);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Não foi possivel encontrar uma turma com o id " + idTurma);
		}

		return ResponseEntity.status(HttpStatus.OK).body("Turma com o id " + idTurma + " ativa");

	}

}