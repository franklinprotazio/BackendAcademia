package com.example.academia.core.service;

import com.example.academia.v1.dto.AlunoDTO;
import com.example.academia.v1.dto.ContatoSemAlunoDTO;

public interface ContatoService {

	AlunoDTO buscarAlunoPorId (Long idAluno);

	ContatoSemAlunoDTO salvarContatoDeAluno(ContatoSemAlunoDTO enderecoDTO, Long idAluno);
}
