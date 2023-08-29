package com.example.academia.core.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.academia.core.entity.Aluno;
import com.example.academia.core.entity.Endereco;
import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.service.EnderecoService;
import com.example.academia.integration.repository.AlunoRepository;
import com.example.academia.integration.repository.EnderecoRepository;
import com.example.academia.v1.dto.AlunoDTO;
import com.example.academia.v1.dto.EnderecoSemAlunoDTO;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	private static final String MENSAGEM_ALUNO_INESISTENTE = "Não foi possivel encontrar o aluno com o id = ";

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AlunoDTO buscarAlunoPorId(Long idAluno) throws EntidadeNaoEncontradaException {

		Aluno aluno = alunoRepository.findById(idAluno)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MENSAGEM_ALUNO_INESISTENTE + idAluno));

		AlunoDTO alunoDTO = modelMapper.map(aluno, AlunoDTO.class);

		return alunoDTO;
	}

	@Override
	public EnderecoSemAlunoDTO salvarEnderecoDeAluno(EnderecoSemAlunoDTO enderecoDTO, Long idAluno)
			throws EntidadeNaoEncontradaException {

		Aluno aluno = alunoRepository.findById(idAluno)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MENSAGEM_ALUNO_INESISTENTE + idAluno));

		Endereco endereco = modelMapper.map(enderecoDTO, Endereco.class);

		endereco.setAluno(aluno);

		EnderecoSemAlunoDTO enderecoRetornoDTO = modelMapper.map(enderecoRepository.save(endereco),
				EnderecoSemAlunoDTO.class);

		return enderecoRetornoDTO;
	}
}
