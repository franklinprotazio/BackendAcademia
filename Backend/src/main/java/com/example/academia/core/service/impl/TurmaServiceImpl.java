package com.example.academia.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.academia.core.entity.Academia;
import com.example.academia.core.entity.Aluno;
import com.example.academia.core.entity.Turma;
import com.example.academia.core.enums.StatusTurmaeNUM;
import com.example.academia.core.exception.AlunoMatriculadoException;
import com.example.academia.core.exception.AlunoNaoMatriculadoException;
import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.exception.TurmaInativaException;
import com.example.academia.core.service.TurmaService;
import com.example.academia.integration.repository.AlunoRepository;
import com.example.academia.integration.repository.TurmaRepository;
import com.example.academia.v1.dto.AcademiaRetornoDTO;
import com.example.academia.v1.dto.MatriculaDTO;
import com.example.academia.v1.dto.ProfessoresSemTurmaSemAcademiaESemAlunosDTO;
import com.example.academia.v1.dto.TurmaDTO;

@Service
public class TurmaServiceImpl implements TurmaService {

	private static final String MENSAGEM_ACADEMIA_INESISTENTE = "Não foram encontradas uma turma a com o id = ";

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private AlunoRepository alunoRepository;

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

		turma.setStatus(StatusTurmaeNUM.INATIVA);

		TurmaDTO turmaRetornoDTO = modelMapper.map(turmaRepository.save(turma), TurmaDTO.class);

		return turmaRetornoDTO;
	}

	@Override
	public StatusTurmaeNUM ativarTurma(Long idTurma) throws EntidadeNaoEncontradaException {

		Turma turma = turmaRepository.findById(idTurma).orElseThrow(
				() -> new EntidadeNaoEncontradaException("Não foi possivel localizar turma com o id " + idTurma));

		turma.setStatus(StatusTurmaeNUM.ATIVA);
		turma = turmaRepository.save(turma);

		return turma.getStatus();
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
	public List<TurmaDTO> getTurmas(String horario, String curso) {
		horario = Objects.nonNull(horario) ? horario : "";
		curso = Objects.nonNull(curso) ? curso : "";

		List<Turma> turmas = turmaRepository.getTurmas(horario, curso);
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
		turmaDTO.setHorario(turma.getHorario());
		ProfessoresSemTurmaSemAcademiaESemAlunosDTO professorRetornoDTO = modelMapper.map(turma.getProfessor(),
				ProfessoresSemTurmaSemAcademiaESemAlunosDTO.class);
		turmaDTO.setProfessor(professorRetornoDTO);
		AcademiaRetornoDTO academiaRetornoDTO = modelMapper.map(turma.getAcademia(), AcademiaRetornoDTO.class);
		turmaDTO.setAcademia(academiaRetornoDTO);
		return turmaDTO;

	}

	@Override
	public TurmaDTO matricularAlunoEmTurma(MatriculaDTO matriculaDTO) throws EntidadeNaoEncontradaException,
			TurmaInativaException, AlunoNaoMatriculadoException, AlunoMatriculadoException {

		TurmaDTO turmaDTO;

		Turma turma = turmaRepository.findById(matriculaDTO.getIdTurma())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Turma não encontrada"));

		Aluno aluno = alunoRepository.findById(matriculaDTO.getIdAluno())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi possível encontrar o aluno"));

		if (turma.getStatus() == StatusTurmaeNUM.INATIVA || turma.getStatus() == StatusTurmaeNUM.INDISPONIVEL) {

			throw new TurmaInativaException("A turma com o id está inativa ou indisponivel");
		}

		if (aluno.getTurmas().size() >= 2) {
			throw new AlunoNaoMatriculadoException("Aluno não matriculado");
		}

		for (Turma turmaDoAluno : aluno.getTurmas()) {

			if (turmaDoAluno.getIdTurma().equals(turma.getIdTurma())) {

				throw new AlunoMatriculadoException("Aluno já está matriculado nesta turma");
			}

			if (turma.getHorario().equals(turmaDoAluno.getHorario())) {
				throw new AlunoNaoMatriculadoException("Aluno já está matriculado em outra turma com o mesmo horário");
			}
		}

		turma.getAlunos().add(aluno);

		turma = turmaRepository.save(turma);

		return modelMapper.map(turma, TurmaDTO.class);

	}

	@Override
	public TurmaDTO getTurmaComMaisAluno() throws EntidadeNaoEncontradaException {
		List<Object[]> turmas = turmaRepository.findTurmaComMaiorQuantidadeDeAlunos();

		if (!turmas.isEmpty()) {
			Object[] firstResult = turmas.get(0);
			Long idTurma = (Long) firstResult[0];
			Long totalAlunos = (Long) firstResult[1];

			// Agora que temos os valores, você pode usar esses dados para criar um DTO.
			TurmaDTO turmaDTO = new TurmaDTO();
			turmaDTO.setIdTurma(idTurma);
			turmaDTO.setQtoAluno(totalAlunos);
			return turmaDTO;

		} else {
			// Lida com o caso em que não há turmas com alunos.
			throw new EntidadeNaoEncontradaException("Não existe turmas cadastradas");

		}
	}

}