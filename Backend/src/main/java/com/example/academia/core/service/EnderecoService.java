package com.example.academia.core.service;

import com.example.academia.v1.dto.AlunoDTO;
import com.example.academia.v1.dto.EnderecoSemAlunoDTO;

public interface EnderecoService {

	AlunoDTO buscarAlunoPorId (Long idAluno);

	EnderecoSemAlunoDTO salvarEnderecoDeAluno(EnderecoSemAlunoDTO enderecoDTO, Long idAluno);
}
