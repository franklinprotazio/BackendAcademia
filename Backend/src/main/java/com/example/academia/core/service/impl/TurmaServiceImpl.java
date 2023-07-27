package com.example.academia.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.academia.core.entity.Academia;
import com.example.academia.core.entity.Turma;
import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.service.TurmaService;
import com.example.academia.integration.repository.TurmaRepository;
import com.example.academia.v1.dto.AcademiaRetornoDTO;
import com.example.academia.v1.dto.TurmaDTO;

@Service
public class TurmaServiceImpl implements TurmaService {

	private static final String MENSAGEM_ACADEMIA_INESISTENTE = "Não foram encontradas uma turma a com o id = ";

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void deletarTurmaPorAcademia(Academia academia) {
		turmaRepository.deletarTurmaPorAcademia(academia.getIdAcademia());

	}

	@Override
	public List<TurmaDTO> buscarTurmaPorAcademia(Academia academia) {
		List<Turma> turmas = turmaRepository.findTurmaPorAcademia(academia.getIdAcademia());
		List<TurmaDTO> turmasDTO = new ArrayList<>();

		for (Turma turma : turmas) {
			TurmaDTO turmaDTO = modelMapper.map(turma, TurmaDTO.class);
			turmasDTO.add(turmaDTO);

		}

		return turmasDTO;
	}

	@Override
	public TurmaDTO salvarTurma(TurmaDTO turmaDTO) {
		Turma turma = modelMapper.map(turmaDTO, Turma.class);
		TurmaDTO turmaRetornoDTO = modelMapper.map(turma, TurmaDTO.class);

		return turmaRetornoDTO;
	}

	@Override
	public TurmaDTO buscarTurmaPorId(Long idTurma) throws EntidadeNaoEncontradaException {

		Turma turma = turmaRepository.findById(idTurma)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MENSAGEM_ACADEMIA_INESISTENTE + idTurma));
		TurmaDTO turmaDTO = modelMapper.map(turma, TurmaDTO.class);

		return turmaDTO;
	}

	@Override
	public void deletarTurmaPorId(Long idTurma) throws EntidadeNaoEncontradaException {

		Turma turmaRetorno = turmaRepository.findById(idTurma)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MENSAGEM_ACADEMIA_INESISTENTE + idTurma));

		turmaRepository.deleteById(idTurma);

	}

	@Override
	public List<TurmaDTO> buscarTurmaPorCurso(String cursoTurma) {

		List<Turma> turmas = turmaRepository.findByCurso(cursoTurma);
		List<TurmaDTO> turmasDTO = new ArrayList<>();

		for (Turma turma : turmas) {
			TurmaDTO turmaDTO = convertter(turma);
			turmasDTO.add(turmaDTO);
		}

		return turmasDTO;
	}

	@Override
	public List<TurmaDTO> getTurmas(String horarios, String nome) {
		horarios = Objects.nonNull(horarios) ? horarios : "";
		nome = Objects.nonNull(nome) ? nome : "";

		List<Turma> turmas = turmaRepository.getTurmas(horarios, nome);
		List<TurmaDTO> turmasDTO = new ArrayList<>();

		for (Turma turma : turmas) {
			TurmaDTO dto = modelMapper.map(turma, TurmaDTO.class);
			turmasDTO.add(dto);
		}

		return turmasDTO;
	}

	private TurmaDTO convertter(Turma turma) {
		TurmaDTO turmaDTO = new TurmaDTO();

		turmaDTO.setIdTurma(turma.getIdTurma());
		turmaDTO.setCurso(turma.getCurso());
		turmaDTO.setNomeProfessor(turma.getNomeProfessor());
		turmaDTO.setHorario(turma.getHorario());
		AcademiaRetornoDTO academiaRetornoDTO = modelMapper.map(turma.getAcademia(), AcademiaRetornoDTO.class);
		turmaDTO.setAcademia(academiaRetornoDTO);
		return turmaDTO;

	}

}
