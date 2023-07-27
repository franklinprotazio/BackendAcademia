package com.example.academia.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.academia.core.entity.Academia;
import com.example.academia.core.entity.Turma;
import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.service.AcademiaService;
import com.example.academia.integration.repository.AcademiaRepository;
import com.example.academia.integration.repository.TurmaRepository;
import com.example.academia.v1.dto.AcademiaDTO;
import com.example.academia.v1.dto.TurmaSemAcademiaDTO;

@Service
public class AcademiaServiceImpl implements AcademiaService {

	private static final String MENSAGEM_ACADEMIA_INESISTENTE = "Não foram encontradas uma academia a com o id = ";
		
	@Autowired
	private AcademiaRepository academiaRepository;
		
	@Autowired
	private TurmaRepository turmaRepository;
		
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<AcademiaDTO> getAcademias() {
		
		List<Academia> academias = academiaRepository.findAll();
		List<AcademiaDTO> listaRetornos = new ArrayList<>();
		
		if(!academias.isEmpty()) {
			for (Academia academia : academias) {
				List<Turma> listaTurmas = turmaRepository.findTurmaPorAcademia(academia.getIdAcademia());
				List<TurmaSemAcademiaDTO> listaTurmasDTO = new ArrayList<>();
				
				for (Turma turma : listaTurmas) {
					TurmaSemAcademiaDTO turmasDTO = modelMapper.map(turma, TurmaSemAcademiaDTO.class);
					listaTurmasDTO.add(turmasDTO);			
				}
				
				AcademiaDTO academiaDTO = modelMapper.map(academia, AcademiaDTO.class);
				academiaDTO.setTurmas(listaTurmasDTO);
				listaRetornos.add(academiaDTO);				
			}
		}
		return listaRetornos;
	}

	@Override
	public AcademiaDTO salvarAcademia(AcademiaDTO academiaDTO) {
		Academia academia = modelMapper.map(academiaDTO, Academia.class);
		AcademiaDTO academiaRetornoDTO = modelMapper.map(academiaRepository.save(academia), AcademiaDTO.class);
		
		return academiaRetornoDTO;
	}

	@Transactional
	@Override
	public void deletarAcademia(Long idAcademia)  throws EntidadeNaoEncontradaException {
		
		Academia academiaRetorno = academiaRepository.findById(idAcademia).orElseThrow(
				() -> new EntidadeNaoEncontradaException(MENSAGEM_ACADEMIA_INESISTENTE + idAcademia));
		
		turmaRepository.deletarTurmaPorAcademia(academiaRetorno.getIdAcademia());
		
		academiaRepository.deleteById(academiaRetorno.getIdAcademia());	
	}

	@Override
	public AcademiaDTO buscarAcademiaPorId(Long idAcademia) throws EntidadeNaoEncontradaException {
		Academia academia = academiaRepository.findById(idAcademia)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MENSAGEM_ACADEMIA_INESISTENTE + idAcademia));
		
		AcademiaDTO academiaDTO = modelMapper.map(academia, AcademiaDTO.class);
		
		// Buscar os alunos associados à academia
		List<Turma> turmas = turmaRepository.findTurmaPorAcademia(idAcademia);
		List<TurmaSemAcademiaDTO> turmasDTO = new ArrayList<>();
		
		// Mapear as turmas para o DTO
		for (Turma turma : turmas) {
			TurmaSemAcademiaDTO turmaSemAcademiaDTO = modelMapper.map(turma, TurmaSemAcademiaDTO.class);
			turmasDTO.add(turmaSemAcademiaDTO);
		}
		
		// Definir as turmas no DTO da academia
		academiaDTO.setTurmas(turmasDTO);
		
		return academiaDTO;
	}

	@Override
	public AcademiaDTO atualizarAcademia(Long idAcademia, AcademiaDTO academiaDTO) throws EntidadeNaoEncontradaException {

		Academia academiaRetorno = academiaRepository.findById(idAcademia).orElseThrow(
				() -> new EntidadeNaoEncontradaException(MENSAGEM_ACADEMIA_INESISTENTE + idAcademia));
				
		academiaRetorno.setNomeAcademia(academiaDTO.getNomeAcademia());
		AcademiaDTO academiaRetornoDTO = modelMapper.map(academiaRepository.save(academiaRetorno), AcademiaDTO.class);
		return academiaRetornoDTO;
	}

}
