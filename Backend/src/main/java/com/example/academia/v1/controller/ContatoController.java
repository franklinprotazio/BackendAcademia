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

import com.example.academia.core.service.ContatoService;
import com.example.academia.v1.dto.AlunoDTO;
import com.example.academia.v1.dto.ContatoSemAlunoDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "v1/contato")
public class ContatoController {

	private static final String MENSAGEM_ALUNO_INESISTENTE = "Não foi possivel encontrar o aluno com o id = ";

	@Autowired
	ContatoService contatoService;

	@GetMapping("aluno/{id}")
	public ResponseEntity<Object> buscarAlunoPorId(@PathVariable(value = "id") @Valid Long idAluno) {

		AlunoDTO alunoDTO = contatoService.buscarAlunoPorId(idAluno);

		if (alunoDTO != null && alunoDTO.getIdAluno() != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alunoDTO);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MENSAGEM_ALUNO_INESISTENTE);
	}

	@PostMapping("aluno/{id}")
	public ResponseEntity<Object> cadastrarContatoDeAluno(@RequestBody ContatoSemAlunoDTO enderecoDTO,
			@PathVariable(value = "id") @Valid Long idAluno) {

		AlunoDTO alunoDTO = contatoService.buscarAlunoPorId(idAluno);


		if (alunoDTO != null && alunoDTO.getIdAluno() != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(contatoService.salvarContatoDeAluno(enderecoDTO, idAluno));
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MENSAGEM_ALUNO_INESISTENTE);
	}

}
