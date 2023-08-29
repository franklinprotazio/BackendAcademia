package com.example.academia.core.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.academia.core.entity.Aluno;
import com.example.academia.core.entity.Contato;
import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.service.ContatoService;
import com.example.academia.integration.repository.AlunoRepository;
import com.example.academia.integration.repository.ContatoRepository;
import com.example.academia.v1.dto.AlunoDTO;
import com.example.academia.v1.dto.ContatoSemAlunoDTO;

@Service
public class ContatoServiceImpl implements ContatoService {

	private static final String MENSAGEM_ALUNO_INESISTENTE = "Não foi possivel encontrar o aluno com o id = ";

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private ContatoRepository contatoRepository;

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
	public ContatoSemAlunoDTO salvarContatoDeAluno(ContatoSemAlunoDTO ContatoDTO, Long idAluno)
			throws EntidadeNaoEncontradaException {

		Aluno aluno = alunoRepository.findById(idAluno)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MENSAGEM_ALUNO_INESISTENTE + idAluno));

		Contato contato = modelMapper.map(ContatoDTO, Contato.class);

		contato.setAluno(aluno);

		ContatoSemAlunoDTO ContatoRetornoDTO = modelMapper.map(contatoRepository.save(contato),
				ContatoSemAlunoDTO.class);

		return ContatoRetornoDTO;
	}
}
