package com.example.academia.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.academia.core.entity.Aluno;
import com.example.academia.core.entity.Turma;
import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.service.AlunoService;
import com.example.academia.integration.repository.AlunoRepository;
import com.example.academia.integration.repository.TurmaRepository;
import com.example.academia.v1.dto.AlunoDTO;
import com.example.academia.v1.dto.TurmaRetornoDTOSemQtoAluno;

@Service
public class AlunoServiceImpl implements AlunoService {
	
	private static final String MENSAGEM_ALUNO_INESISTENTE = "Não foi possivel encontrar o aluno com o id = ";
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	@Override
	public void deletarAlunoPorTurma(Turma turma) {
		alunoRepository.deletarAlunoPorTurma(turma.getIdTurma());
	}
	
	@Override
	public List<AlunoDTO> buscarAlunoPorTurma(Turma turma) {
		
		List<Aluno> alunos = alunoRepository.findAlunoPorTurma(turma.getIdTurma());
		List<AlunoDTO> alunosDTO = new ArrayList<>();
		
		for (Aluno aluno : alunos) {
			AlunoDTO alunoDTO = modelMapper.map(aluno, AlunoDTO.class);
			alunosDTO.add(alunoDTO);			
		}
		
		return alunosDTO;
	}

	@Transactional
	@Override
	public AlunoDTO salvarAluno (AlunoDTO alunoDTO) {
		
		Aluno aluno = modelMapper.map(alunoDTO, Aluno.class);
	
		AlunoDTO alunoRetornoDTO = modelMapper.map(alunoRepository.save(aluno), AlunoDTO.class);

		return alunoRetornoDTO;
	}
		
	@Override
	public AlunoDTO buscarAlunoPorId(Long idAluno) throws EntidadeNaoEncontradaException  {
		
		Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(
				() -> new EntidadeNaoEncontradaException(MENSAGEM_ALUNO_INESISTENTE + idAluno ));
		AlunoDTO alunoDTO = modelMapper.map(aluno, AlunoDTO.class);
		
		return alunoDTO;
	}
	
	@Override
	public void deletarAlunoPorId(Long idAluno) throws EntidadeNaoEncontradaException {
		
		Aluno alunoRetorno = alunoRepository.findById(idAluno).orElseThrow(
				() -> new EntidadeNaoEncontradaException(MENSAGEM_ALUNO_INESISTENTE + idAluno));
		
		alunoRepository.deleteById(idAluno);
	}
	
	@Override
	public List<AlunoDTO> buscarAlunoPorNome(String nomeAluno) {
		List<Aluno> alunos = alunoRepository.findByNomeAluno(nomeAluno);
		List<AlunoDTO> alunosDTO = new ArrayList<>();
		
		for (Aluno aluno : alunos) {
			AlunoDTO alunoDTO = convertter(aluno);
			alunosDTO.add(alunoDTO);
		}
	
	return alunosDTO;
	}
	
	private AlunoDTO convertter(Aluno aluno) {
		AlunoDTO alunoDTO = new AlunoDTO();
		
		alunoDTO.setDataMatricula(aluno.getDataMatricula());
		alunoDTO.setIdAluno(aluno.getIdAluno());
		alunoDTO.setNomeAluno(aluno.getNomeAluno());
		TurmaRetornoDTOSemQtoAluno turmaRetornoDTO = modelMapper.map(aluno.getTurmas(), TurmaRetornoDTOSemQtoAluno.class);
		alunoDTO.setTurma(turmaRetornoDTO);
		
		return alunoDTO;
		
	}
	
	@Override
	public List<AlunoDTO> getAlunos(String dataInicio, String dataFim, String nome) {
		
		dataInicio = Objects.nonNull(dataInicio) ? dataInicio : "";
		dataFim = Objects.nonNull(dataFim) ? dataFim : "";
		nome = Objects.nonNull(nome) ? nome : "";
		
		List<Aluno> alunos = alunoRepository.getAlunos(dataInicio, dataFim, nome);
		List<AlunoDTO> alunosDTO = new ArrayList<>();
		
		for (Aluno aluno : alunos) {
			AlunoDTO dto = modelMapper.map(aluno, AlunoDTO.class);
			alunosDTO.add(dto);
		}
		
		return alunosDTO;
	}
	
}