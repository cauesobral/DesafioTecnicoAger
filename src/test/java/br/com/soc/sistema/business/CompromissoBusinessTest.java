package br.com.soc.sistema.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.soc.sistema.dao.CompromissoDao;
import br.com.soc.sistema.exception.ValidationException;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;
import br.com.soc.sistema.vo.Periodo;

@ExtendWith(MockitoExtension.class)
class CompromissoBusinessTest {

	@Mock
	private CompromissoDao dao;

	@Mock
	private AgendaBusiness agendaBusiness;

	@InjectMocks
	private CompromissoBusiness compromissoBusiness;

	private CompromissoVo compromissoValido(LocalTime horario, Periodo periodo) {
		FuncionarioVo funcionario = new FuncionarioVo("1", "João Silva");
		AgendaVo agenda = new AgendaVo("2", "Consultório", periodo);

		when(agendaBusiness.buscarAgendaPor("2")).thenReturn(agenda);

		CompromissoVo compromisso = new CompromissoVo();
		compromisso.setFuncionario(funcionario);
		compromisso.setAgenda(agenda);
		compromisso.setDataCompromisso(LocalDate.of(2026, 9, 10));
		compromisso.setHorarioCompromisso(horario);

		return compromisso;
	}

	@Test
	void deveSalvarCompromissoDentroDoPeriodoManha() {
		CompromissoVo compromisso = compromissoValido(LocalTime.of(8, 0), Periodo.MANHA);

		compromissoBusiness.salvarCompromisso(compromisso);

		verify(dao).insertCompromisso(compromisso);
	}

	@Test
	void naoDeveSalvarCompromissoForaDoPeriodoManha() {
		CompromissoVo compromisso = compromissoValido(LocalTime.of(15, 0), Periodo.MANHA);

		assertThrows(ValidationException.class, () -> compromissoBusiness.salvarCompromisso(compromisso));
		verify(dao, never()).insertCompromisso(any());
	}

	@Test
	void naoDeveSalvarCompromissoForaDoPeriodoTarde() {
		CompromissoVo compromisso = compromissoValido(LocalTime.of(8, 0), Periodo.TARDE);

		assertThrows(ValidationException.class, () -> compromissoBusiness.salvarCompromisso(compromisso));
		verify(dao, never()).insertCompromisso(any());
	}

	@Test
	void deveSalvarCompromissoEmQualquerHorarioQuandoPeriodoAmbos() {
		CompromissoVo compromisso = compromissoValido(LocalTime.of(23, 0), Periodo.AMBOS);

		compromissoBusiness.salvarCompromisso(compromisso);

		verify(dao).insertCompromisso(compromisso);
	}

	@Test
	void naoDeveSalvarSemFuncionario() {
		CompromissoVo compromisso = new CompromissoVo();
		compromisso.setAgenda(new AgendaVo("2", "Consultório", Periodo.AMBOS));
		compromisso.setDataCompromisso(LocalDate.now());
		compromisso.setHorarioCompromisso(LocalTime.of(10, 0));

		assertThrows(ValidationException.class, () -> compromissoBusiness.salvarCompromisso(compromisso));
	}

	@Test
	void naoDeveAlterarCompromissoSemCodigo() {
		CompromissoVo compromisso = compromissoValido(LocalTime.of(8, 0), Periodo.MANHA);

		assertThrows(ValidationException.class, () -> compromissoBusiness.alterarCompromisso(compromisso));
		verify(dao, never()).updateCompromisso(any());
	}

	@Test
	void naoDeveBuscarPorPeriodoComDatasNulas() {
		assertThrows(ValidationException.class,
				() -> compromissoBusiness.buscarCompromissosPorPeriodo(null, LocalDate.now()));
	}

	@Test
	void naoDeveBuscarPorPeriodoComDataInicialAposDataFinal() {
		LocalDate inicio = LocalDate.of(2026, 9, 10);
		LocalDate fim = LocalDate.of(2026, 9, 1);

		assertThrows(ValidationException.class,
				() -> compromissoBusiness.buscarCompromissosPorPeriodo(inicio, fim));
	}

	@Test
	void deveBuscarCompromissosPorPeriodoValido() {
		LocalDate inicio = LocalDate.of(2026, 9, 1);
		LocalDate fim = LocalDate.of(2026, 9, 30);

		compromissoBusiness.buscarCompromissosPorPeriodo(inicio, fim);

		verify(dao).findByPeriodo(inicio, fim);
	}
}