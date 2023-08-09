package com.example.academia.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.academia.core.entity.Professor;
import com.example.academia.core.entity.Turma;
import com.example.academia.core.enums.StatusTurmaeNUM;
import com.example.academia.core.exception.EntidadeNaoEncontradaException;
import com.example.academia.core.exception.TurmaInativaException;
import com.example.academia.core.exception.TurmaJaVinculadaException;
import com.example.academia.core.exception.TurmaNaoVinculadaException;
import com.example.academia.core.service.ProfessorService;
import com.example.academia.integration.repository.ProfessorRepository;
import com.example.academia.integration.repository.TurmaRepository;
import com.example.academia.v1.dto.ProfessorDTO;
import com.example.academia.v1.dto.TurmaRetornoDTOSemQtoProfessor;
import com.example.academia.v1.dto.VincularProfessorDTO;

@Service
public class ProfessorServiceImpl implements ProfessorService {

	private static final String MSG_TURMA_ID_INATIVO = "A turma com o id está inativa ou indisponivel";

	private static final String MSG_PROFESSOR_HORARIO_OCUPADO = "O professor já está vinculado a turma com o mesmo horario";

	private static final String MSG_PROFESSOR_JA_VINCULADO = "O professor já está vinculado a esta turma";

	private static final String MENSAGEM_PROFESSOR_INESISTENTE = "Não foi possivel encontrar o professor com o id = ";

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	@Override
	public void deletarProfessorPorTurma(Turma turma) {
		professorRepository.deletarProfessorPorTurma(turma.getIdTurma());
	}

	@Override
	public List<ProfessorDTO> buscarProfessorPorTurma(Turma turma) {

		List<Professor> professors = professorRepository.findProfessorPorTurma(turma.getIdTurma());
		List<ProfessorDTO> professorsDTO = new ArrayList<>();

		for (Professor professor : professors) {
			ProfessorDTO professorDTO = modelMapper.map(professor, ProfessorDTO.class);
			professorsDTO.add(professorDTO);
		}

		return professorsDTO;
	}

	@Transactional
	@Override
	public ProfessorDTO salvarProfessor(ProfessorDTO professorDTO) {

		Professor professor = modelMapper.map(professorDTO, Professor.class);

		ProfessorDTO professorRetornoDTO = modelMapper.map(professorRepository.save(professor), ProfessorDTO.class);

		return professorRetornoDTO;
	}

	@Override
	public ProfessorDTO buscarProfessorPorId(Long idProfessor) throws EntidadeNaoEncontradaException {

		Professor professor = professorRepository.findById(idProfessor)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MENSAGEM_PROFESSOR_INESISTENTE + idProfessor));
		ProfessorDTO professorDTO = modelMapper.map(professor, ProfessorDTO.class);

		return professorDTO;
	}

	@Override
	public void deletarProfessorPorId(Long idProfessor) throws EntidadeNaoEncontradaException {

		Professor professorRetorno = professorRepository.findById(idProfessor)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MENSAGEM_PROFESSOR_INESISTENTE + idProfessor));

		professorRepository.deleteById(idProfessor);
	}

	@Override
	public List<ProfessorDTO> buscarProfessorPorNome(String nomeProfessor) {
		List<Professor> professors = professorRepository.findByNomeProfessor(nomeProfessor);
		List<ProfessorDTO> professorsDTO = new ArrayList<>();

		for (Professor professor : professors) {
			ProfessorDTO professorDTO = convertter(professor);
			professorsDTO.add(professorDTO);
		}

		return professorsDTO;
	}

	private ProfessorDTO convertter(Professor professor) {
		ProfessorDTO professorDTO = new ProfessorDTO();

		professorDTO.setIdProfessor(professor.getIdProfessor());
		professorDTO.setNomeProfessor(professor.getNomeProfessor());
		TurmaRetornoDTOSemQtoProfessor turmaRetornoDTO = modelMapper.map(professor.getTurmas(),
				TurmaRetornoDTOSemQtoProfessor.class);
		professorDTO.setTurma(turmaRetornoDTO);
		return professorDTO;

	}

	@Override
	public List<ProfessorDTO> getProfessores() {


		List<Professor> professores = professorRepository.findAll();
		List<ProfessorDTO> professorsDTO = new ArrayList<>();

		for (Professor professor : professores) {
			ProfessorDTO dto = modelMapper.map(professor, ProfessorDTO.class);
			professorsDTO.add(dto);
		}

		return professorsDTO;
	}

	@Override
	public ProfessorDTO vincularProfessorEmTurma(VincularProfessorDTO vincularProfessorDTO)
			throws TurmaNaoVinculadaException, TurmaJaVinculadaException, TurmaInativaException {

		ProfessorDTO professorDTO;

		Professor professor = professorRepository.findById(vincularProfessorDTO.getIdProfessor())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Profesosr não encontrado"));
		
		Turma turma = turmaRepository.findById(vincularProfessorDTO.getIdTurma())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Turma não encontrada"));

		if (turma.getStatus() == StatusTurmaeNUM.INATIVA || turma.getStatus() == StatusTurmaeNUM.INDISPONIVEL) {

			throw new TurmaInativaException(MSG_TURMA_ID_INATIVO);
		}
		
		for (Turma turmaDoProfessor : professor.getTurmas()) {
			
			if(isProfessorJaVinculado(turma, turmaDoProfessor)) {
				throw new TurmaJaVinculadaException(MSG_PROFESSOR_JA_VINCULADO);
			}
			
			if (isProfessorHorarioOcupado(turma, turmaDoProfessor)) {
				throw new TurmaJaVinculadaException(MSG_PROFESSOR_HORARIO_OCUPADO);
			}
		}
		
		professor.getTurmas().add(turma);
		
		professor = professorRepository.save(professor);		

		return modelMapper.map(professor, ProfessorDTO.class);
	}

	private boolean isProfessorHorarioOcupado(Turma turma, Turma turmaDoProfessor) {
		return turma.getHorario().equals(turmaDoProfessor.getHorario());
	}

	private boolean isProfessorJaVinculado(Turma turma, Turma turmaDoProfessor) {
		return turmaDoProfessor.getIdTurma().equals(turma.getIdTurma());
	}




}