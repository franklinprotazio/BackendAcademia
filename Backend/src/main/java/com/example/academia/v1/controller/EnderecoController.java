package com.example.academia.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.service.EnderecoService;
import com.example.academia.v1.dto.AlunoDTO;
import com.example.academia.v1.dto.EnderecoSemAlunoDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "v1/endereco")
public class EnderecoController {

	private static final String MENSAGEM_ALUNO_INESISTENTE = "Não foi possivel encontrar o aluno com o id = ";

	@Autowired
	EnderecoService enderecoService;

	@GetMapping("aluno/{id}")
	public ResponseEntity<Object> buscarAlunoPorId(@PathVariable(value = "id") @Valid Long idAluno) {

		AlunoDTO alunoDTO;

		try {
			alunoDTO = enderecoService.buscarAlunoPorId(idAluno);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MENSAGEM_ALUNO_INESISTENTE);
			
		}

		return ResponseEntity.status(HttpStatus.OK).body(alunoDTO);
	}

	@PostMapping("aluno/{id}")
	public ResponseEntity<Object> cadastrarEnderecoDeAluno(@RequestBody EnderecoSemAlunoDTO enderecoDTO,
			@PathVariable(value = "id") @Valid Long idAluno) {

		AlunoDTO alunoDTO;

		try {
			alunoDTO = enderecoService.buscarAlunoPorId(idAluno);

		} catch (EntidadeNaoEncontradaException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MENSAGEM_ALUNO_INESISTENTE + idAluno);
		}

		if (alunoDTO.getEnderecos().size() >= 2) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("O aluno já tem dois endereços cadastrados");
		}
		
		if(alunoDTO.getEnderecos() == alunoDTO.getEnderecos()) {
			
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Endereço já cadastrado no sistema");
		}

		return ResponseEntity.status(HttpStatus.OK).body(enderecoService.salvarEnderecoDeAluno(enderecoDTO, idAluno));
	}

}
