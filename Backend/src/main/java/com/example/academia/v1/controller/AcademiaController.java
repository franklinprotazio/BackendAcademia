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

import com.example.academia.core.exception.AlunoMatriculadoException;
import com.example.academia.core.exception.AlunoNaoMatriculadoException;
import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.exception.TurmaInativaException;
import com.example.academia.core.exception.TurmaJaVinculadaException;
import com.example.academia.core.exception.TurmaNaoVinculadaException;
import com.example.academia.core.service.AcademiaService;
import com.example.academia.core.service.ProfessorService;
import com.example.academia.core.service.TurmaService;
import com.example.academia.integration.repository.AlunoRepository;
import com.example.academia.v1.dto.AcademiaDTO;
import com.example.academia.v1.dto.MatriculaDTO;
import com.example.academia.v1.dto.ProfessorDTO;
import com.example.academia.v1.dto.TurmaDTO;
import com.example.academia.v1.dto.VincularProfessorDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/academia")
public class AcademiaController {
	
	private static final String MENSAGEM_ACADEMIA_INESISTENTE = "Não foram encontradas academia uma conta com o id = ";
	
	@Autowired
	AlunoRepository alunoRepository;
	
	@Autowired
	AcademiaService academiaService;
	
	@Autowired
	TurmaService turmaService;
	
	@Autowired
	ProfessorService professorService;
	
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
	
	@PutMapping("/matricular/{idTurma}/aluno/{idAluno}")
	
	public ResponseEntity<Object> matricularAlunoEmTurma(@PathVariable Long idTurma, @PathVariable Long idAluno)  {
		
		MatriculaDTO matriculaDTO = new MatriculaDTO(); 
		
		matriculaDTO.setIdTurma(idTurma);
        matriculaDTO.setIdAluno(idAluno);
		
		TurmaDTO turmaRetorno;
		
		try {
			turmaRetorno = turmaService.matricularAlunoEmTurma(matriculaDTO);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno com id " + matriculaDTO.getIdAluno()
					+ " não encontrado ou turma com id " + matriculaDTO.getIdTurma() + " inativa");
		} catch (AlunoNaoMatriculadoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Não foi possivel matricular o aluno, pois o aluno já está matriculado em duas turmas");			
		} catch (AlunoMatriculadoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body("Aluno já está matriculado nesta turma");
		} catch (TurmaInativaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma inativa ou indisponivel");
		}

		return ResponseEntity.status(HttpStatus.OK).body("Aluno matriculado");
	}
	
	
	
	@PutMapping("/vincular/{idProfessor}/turma/{idTurma}")
	
	public ResponseEntity<Object> vincularProfessorEmTurma(@PathVariable Long idProfessor, @PathVariable Long idTurma)  {
		
		VincularProfessorDTO vincularProfessorDTO = new VincularProfessorDTO(); 
			
		vincularProfessorDTO.setIdProfessor(idProfessor);
		vincularProfessorDTO.setIdTurma(idTurma);
		
		ProfessorDTO professorRetorno;
		
		try {
			professorRetorno = professorService.vincularProfessorEmTurma(vincularProfessorDTO);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor com id " + vincularProfessorDTO.getIdTurma()
					+ " não encontrado ou turma com id " + vincularProfessorDTO.getIdTurma() + " inativa");
		} catch (TurmaNaoVinculadaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Não foi possivel vincular o professor, pois o professor já está vinculado em uma turma com o mesmo horario");			
		} catch (TurmaJaVinculadaException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body("Professor já está vinculado nesta turma");
		} catch (TurmaInativaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma inativa ou indisponivel");
		}

		return ResponseEntity.status(HttpStatus.OK).body("Professor Vinculado");
	}

}