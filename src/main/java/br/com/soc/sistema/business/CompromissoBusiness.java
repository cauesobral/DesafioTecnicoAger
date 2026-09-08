package br.com.soc.sistema.business;

import br.com.soc.sistema.filter.CompromissoFilter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.soc.sistema.dao.CompromissoDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.exception.ValidationException;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.Periodo;

public class CompromissoBusiness {

	private static final LocalTime INICIO_MANHA = LocalTime.of(6, 0);
	private static final LocalTime FIM_MANHA = LocalTime.of(11, 59);
	private static final LocalTime INICIO_TARDE = LocalTime.of(12, 0);
	private static final LocalTime FIM_TARDE = LocalTime.of(18, 59);

	private CompromissoDao dao;
	private AgendaBusiness agendaBusiness;

	public CompromissoBusiness() {
		this.dao = new CompromissoDao();
		this.agendaBusiness = new AgendaBusiness();
	}

	public List<CompromissoVo> trazerTodosOsCompromissos() {
		return dao.findAllCompromissos();
	}

	public void salvarCompromisso(CompromissoVo compromissoVo) {
		validarCompromisso(compromissoVo);
		dao.insertCompromisso(compromissoVo);
	}

	public void alterarCompromisso(CompromissoVo compromissoVo) {
		if (compromissoVo.getRowid() == null || compromissoVo.getRowid().isEmpty()) {
			throw new ValidationException("Código do compromisso deve ser informado.");
		}
		validarCompromisso(compromissoVo);
		dao.updateCompromisso(compromissoVo);
	}

	public void excluirCompromisso(String codigo) {
		try {
			Integer codigoNumero = Integer.parseInt(codigo);
			dao.deleteCompromisso(codigoNumero);
		} catch (NumberFormatException e) {
			throw new ValidationException("Código do compromisso inválido.");
		}
	}

	public void excluirCompromissosDoFuncionario(Integer codigoFuncionario) {
		dao.deleteByFuncionario(codigoFuncionario);
	}

	public boolean existemCompromissosNaAgenda(Integer codigoAgenda) {
		return dao.existsByAgenda(codigoAgenda);
	}

	public CompromissoVo buscarCompromissoPor(String codigo) {
		try {
			Integer codigoNumero = Integer.parseInt(codigo);
			CompromissoVo compromisso = dao.findByCodigo(codigoNumero);
			if (compromisso == null) {
				throw new BusinessException("Compromisso não encontrado.");
			}
			return compromisso;
		} catch (NumberFormatException e) {
			throw new ValidationException("Código do compromisso inválido.");
		}
	}

	private void validarCompromisso(CompromissoVo compromissoVo) {
		if (compromissoVo.getFuncionario() == null || compromissoVo.getFuncionario().getRowid() == null
				|| compromissoVo.getFuncionario().getRowid().isEmpty()) {
			throw new ValidationException("Funcionário deve ser informado.");
		}
		if (compromissoVo.getAgenda() == null || compromissoVo.getAgenda().getRowid() == null
				|| compromissoVo.getAgenda().getRowid().isEmpty()) {
			throw new ValidationException("Agenda deve ser informada.");
		}
		if (compromissoVo.getDataCompromisso() == null) {
			throw new ValidationException("Data do compromisso deve ser informada.");
		}
		if (compromissoVo.getHorarioCompromisso() == null) {
			throw new ValidationException("Horário do compromisso deve ser informado.");
		}

		AgendaVo agenda = agendaBusiness.buscarAgendaPor(compromissoVo.getAgenda().getRowid());

		if (agenda == null) {
			throw new BusinessException("Agenda não encontrada.");
		}

		validarHorarioDentroDoPeriodo(compromissoVo.getHorarioCompromisso(), agenda.getPeriodo());
	}

	private void validarHorarioDentroDoPeriodo(LocalTime horario, Periodo periodo) {
		switch (periodo) {
			case MANHA:
				if (horario.isBefore(INICIO_MANHA) || horario.isAfter(FIM_MANHA)) {
					throw new ValidationException("Horário fora da disponibilidade da agenda (Manhã: 06:00 às 11:59).");
				}
				break;
			case TARDE:
				if (horario.isBefore(INICIO_TARDE) || horario.isAfter(FIM_TARDE)) {
					throw new ValidationException("Horário fora da disponibilidade da agenda (Tarde: 12:00 às 18:59).");
				}
				break;
			case AMBOS:
				break;
		}
	}
	
	public List<CompromissoVo> buscarCompromissosPorPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
		if (dataInicial == null || dataFinal == null) {
			throw new ValidationException("Data inicial e data final devem ser informadas.");
		}
		if (dataInicial.isAfter(dataFinal)) {
			throw new ValidationException("Data inicial não pode ser posterior à data final.");
		}
		return dao.findByPeriodo(dataInicial, dataFinal);
	}
	
	public List<CompromissoVo> filtrarCompromissos(CompromissoFilter filtro) {
		return dao.findByFiltro(filtro);
	}
}