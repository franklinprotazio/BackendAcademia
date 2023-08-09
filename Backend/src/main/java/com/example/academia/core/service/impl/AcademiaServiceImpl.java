package com.example.academia.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.academia.core.entity.Academia;
import com.example.academia.core.entity.Aluno;
import com.example.academia.core.entity.Professor;
import com.example.academia.core.entity.Turma;
import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.service.AcademiaService;
import com.example.academia.integration.repository.AcademiaRepository;
import com.example.academia.integration.repository.AlunoRepository;
import com.example.academia.integration.repository.ProfessorRepository;
import com.example.academia.integration.repository.TurmaRepository;
import com.example.academia.v1.dto.AcademiaDTO;
import com.example.academia.v1.dto.AlunoSemAcademiaDTO;
import com.example.academia.v1.dto.ProfessorSemAcademiaDTO;
import com.example.academia.v1.dto.TurmaSemAcademiaDTO;

@Service
public class AcademiaServiceImpl implements AcademiaService {

	private static final String MENSAGEM_ACADEMIA_INESISTENTE = "Não foram encontradas uma academia a com o id = ";

	@Autowired
	private AcademiaRepository academiaRepository;

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<AcademiaDTO> getAcademias() {

		List<Academia> academias = academiaRepository.findAll();
		List<AcademiaDTO> listaRetornos = new ArrayList<>();

		if (!academias.isEmpty()) {
			for (Academia academia : academias) {
				List<Turma> listaTurmas = turmaRepository.findTurmaPorAcademia(academia.getIdAcademia());
				List<TurmaSemAcademiaDTO> listaTurmasDTO = new ArrayList<>();

				for (Turma turma : listaTurmas) {
					TurmaSemAcademiaDTO turmasDTO = modelMapper.map(turma, TurmaSemAcademiaDTO.class);
					listaTurmasDTO.add(turmasDTO);
				}

				List<Aluno> listaAlunos = alunoRepository.findAlunoPorAcademia(academia.getIdAcademia());
				List<AlunoSemAcademiaDTO> listaAlunosDTO = new ArrayList<>();

				for (Aluno aluno : listaAlunos) {
					AlunoSemAcademiaDTO alunosDTO = modelMapper.map(aluno, AlunoSemAcademiaDTO.class);
					listaAlunosDTO.add(alunosDTO);
				}

				List<Professor> listaProfessores = professorRepository.findProfessorPorAcademia(academia.getIdAcademia());
				List<ProfessorSemAcademiaDTO> listaProfessoresDTO = new ArrayList<>();
				
				for (Professor professor : listaProfessores) {
					ProfessorSemAcademiaDTO professoresDTO = modelMapper.map(professor, ProfessorSemAcademiaDTO.class);
					listaProfessoresDTO.add(professoresDTO);

				}

				AcademiaDTO academiaDTO = modelMapper.map(academia, AcademiaDTO.class);
				academiaDTO.setTurmas(listaTurmasDTO);
				academiaDTO.setListaAlunos(listaAlunosDTO);
				academiaDTO.setProfessores(listaProfessoresDTO);
				listaRetornos.add(academiaDTO);
			}
		}

		return listaRetornos;
	}

	@Override
	public List<AcademiaDTO> getAcademiasComAlunos() {

		List<Academia> academias = academiaRepository.findAll();
		List<AcademiaDTO> listaRetornos = new ArrayList<>();

		if (!academias.isEmpty()) {
			for (Academia academia : academias) {
				List<Aluno> listaAlunos = alunoRepository.findAlunoPorAcademia(academia.getIdAcademia());
				List<AlunoSemAcademiaDTO> listaAlunosDTO = new ArrayList<>();

				for (Aluno aluno : listaAlunos) {
					AlunoSemAcademiaDTO alunosDTO = modelMapper.map(aluno, AlunoSemAcademiaDTO.class);
					listaAlunosDTO.add(alunosDTO);
				}

				List<Turma> listaTurmas = turmaRepository.findTurmaPorAcademia(academia.getIdAcademia());
				List<TurmaSemAcademiaDTO> listaTurmasDTO = new ArrayList<>();

				for (Turma turma : listaTurmas) {
					TurmaSemAcademiaDTO turmasDTO = modelMapper.map(turma, TurmaSemAcademiaDTO.class);
					listaTurmasDTO.add(turmasDTO);
				}

				AcademiaDTO academiaDTO = modelMapper.map(academia, AcademiaDTO.class);
				academiaDTO.setTurmas(listaTurmasDTO);
				academiaDTO.setListaAlunos(listaAlunosDTO);
				listaRetornos.add(academiaDTO);
			}
		}
		return listaRetornos;
	}

	@Override
	public List<AcademiaDTO> getAcademiasComTurmas() {

		List<Academia> academias = academiaRepository.findAll();
		List<AcademiaDTO> listaRetornos = new ArrayList<>();

		if (!academias.isEmpty()) {
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
	public void deletarAcademia(Long idAcademia) throws EntidadeNaoEncontradaException {

		Academia academiaRetorno = academiaRepository.findById(idAcademia)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MENSAGEM_ACADEMIA_INESISTENTE + idAcademia));

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

		academiaDTO.setTurmas(turmasDTO);

		List<Aluno> listaAlunos = alunoRepository.findAlunoPorAcademia(academia.getIdAcademia());
		List<AlunoSemAcademiaDTO> listaAlunosDTO = new ArrayList<>();

		for (Aluno aluno : listaAlunos) {
			AlunoSemAcademiaDTO alunosDTO = modelMapper.map(aluno, AlunoSemAcademiaDTO.class);
			listaAlunosDTO.add(alunosDTO);
		}

		academiaDTO.setListaAlunos(listaAlunosDTO);
		
		List<Professor> listaProfessores = professorRepository.findProfessorPorAcademia(academia.getIdAcademia());
		List<ProfessorSemAcademiaDTO> listaProfessoresDTO = new ArrayList<>();
		
		for (Professor professor : listaProfessores) {
			ProfessorSemAcademiaDTO professoresDTO = modelMapper.map(professor, ProfessorSemAcademiaDTO.class);
			listaProfessoresDTO.add(professoresDTO);

		}

		academiaDTO.setProfessores(listaProfessoresDTO);

		return academiaDTO;

	}

	@Override
	public AcademiaDTO atualizarAcademia(Long idAcademia, AcademiaDTO academiaDTO)
			throws EntidadeNaoEncontradaException {

		Academia academiaRetorno = academiaRepository.findById(idAcademia)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MENSAGEM_ACADEMIA_INESISTENTE + idAcademia));

		academiaRetorno.setNomeAcademia(academiaDTO.getNomeAcademia());
		AcademiaDTO academiaRetornoDTO = modelMapper.map(academiaRepository.save(academiaRetorno), AcademiaDTO.class);
		return academiaRetornoDTO;
	}

}