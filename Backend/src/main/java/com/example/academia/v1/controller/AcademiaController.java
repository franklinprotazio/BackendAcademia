package com.example.academia.v1.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.academia.core.service.AcademiaService;
import com.example.academia.integration.repository.AlunoRepository;
import com.example.academia.v1.dto.AcademiaDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/academia")
public class AcademiaController {
	
	private static final String MENSAGEM_ACADEMIA_INESISTENTE = "Não foram encontradas academia uma conta com o id = ";
	
	@Autowired
	AlunoRepository alunoRepository;
	
	@Autowired
	AcademiaService academiaService;
	
	@GetMapping
	public ResponseEntity<Object> getAcademias() {
		
		List<AcademiaDTO> academiasDTO = academiaService.getAcademias();
		
		if(!academiasDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(academiasDTO);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foram encontradas academias na base de dados");
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarAcademiaPorId(@PathVariable(value = "id") @Valid Long idAcademia) {
		
		AcademiaDTO academiaDTO = academiaService.buscarAcademiaPorId(idAcademia);
		
		if(academiaDTO != null && academiaDTO.getIdAcademia() != null) {
			return ResponseEntity.status(HttpStatus.OK).body(academiaDTO);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MENSAGEM_ACADEMIA_INESISTENTE + idAcademia);
	}	
	
	@PostMapping
	public ResponseEntity<AcademiaDTO> salvarAcademia(@RequestBody @Valid AcademiaDTO academiaDTO) {
		
		return ResponseEntity.status(HttpStatus.OK).body(academiaService.salvarAcademia(academiaDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarAcademia(@RequestBody @Valid AcademiaDTO academiaDTO,
													@PathVariable(value = "id") Long idAcademia) {
		
		AcademiaDTO academiaRetorno = academiaService.atualizarAcademia(idAcademia, academiaDTO);
		
		if(academiaRetorno != null && academiaRetorno.getIdAcademia() != null) {
			return ResponseEntity.status(HttpStatus.OK).body(academiaDTO);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MENSAGEM_ACADEMIA_INESISTENTE + idAcademia);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarAcademia(@PathVariable(value = "id") Long idAcademia) {
		
		academiaService.deletarAcademia(idAcademia);
		
		return ResponseEntity.status(HttpStatus.OK).body("Academia excluída com sucesso");
	}

}
