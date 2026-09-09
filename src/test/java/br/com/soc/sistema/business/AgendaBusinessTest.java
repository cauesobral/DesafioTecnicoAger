package br.com.soc.sistema.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.exception.ValidationException;
import br.com.soc.sistema.filter.AgendaFilter;
import br.com.soc.sistema.infra.OpcoesComboBuscarAgenda;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.Periodo;

@ExtendWith(MockitoExtension.class)
class AgendaBusinessTest {

	@Mock
	private AgendaDao dao;

	@Mock
	private CompromissoBusiness compromissoBusiness;

	@InjectMocks
	private AgendaBusiness agendaBusiness;

	@Test
	void deveSalvarAgendaValida() {
		AgendaVo agenda = new AgendaVo(null, "Sala de Reunião", Periodo.AMBOS);

		agendaBusiness.salvarAgenda(agenda);

		verify(dao).insertAgenda(agenda);
	}

	@Test
	void naoDeveSalvarAgendaComNomeVazio() {
		AgendaVo agenda = new AgendaVo(null, "", Periodo.AMBOS);

		assertThrows(ValidationException.class, () -> agendaBusiness.salvarAgenda(agenda));
		verify(dao, never()).insertAgenda(any());
	}

	@Test
	void naoDeveSalvarAgendaSemPeriodo() {
		AgendaVo agenda = new AgendaVo(null, "Sala de Reunião", null);

		assertThrows(ValidationException.class, () -> agendaBusiness.salvarAgenda(agenda));
		verify(dao, never()).insertAgenda(any());
	}

	@Test
	void naoDeveAlterarAgendaSemCodigo() {
		AgendaVo agenda = new AgendaVo(null, "Sala de Reunião", Periodo.AMBOS);

		assertThrows(ValidationException.class, () -> agendaBusiness.alterarAgenda(agenda));
		verify(dao, never()).updateAgenda(any());
	}

	@Test
	void naoDeveExcluirAgendaInexistente() {
		when(dao.findByCodigo(1)).thenReturn(null);

		assertThrows(BusinessException.class, () -> agendaBusiness.excluirAgenda("1"));
		verify(dao, never()).deleteAgenda(anyInt());
	}

	@Test
	void naoDeveExcluirAgendaComCompromissosCadastrados() {
		AgendaVo agenda = new AgendaVo("1", "Sala de Reunião", Periodo.AMBOS);
		when(dao.findByCodigo(1)).thenReturn(agenda);
		when(compromissoBusiness.existemCompromissosNaAgenda(1)).thenReturn(true);

		assertThrows(BusinessException.class, () -> agendaBusiness.excluirAgenda("1"));
		verify(dao, never()).deleteAgenda(anyInt());
	}

	@Test
	void deveExcluirAgendaSemCompromissos() {
		AgendaVo agenda = new AgendaVo("1", "Sala de Reunião", Periodo.AMBOS);
		when(dao.findByCodigo(1)).thenReturn(agenda);
		when(compromissoBusiness.existemCompromissosNaAgenda(1)).thenReturn(false);

		agendaBusiness.excluirAgenda("1");

		verify(dao).deleteAgenda(1);
	}

	@Test
	void deveLancarValidationExceptionParaCodigoInvalidoNaExclusao() {
		assertThrows(ValidationException.class, () -> agendaBusiness.excluirAgenda("abc"));
	}

	@Test
	void deveFiltrarPorId() {
		AgendaVo agenda = new AgendaVo("5", "Fisioterapia", Periodo.TARDE);
		when(dao.findByCodigo(5)).thenReturn(agenda);

		AgendaFilter filtro = new AgendaFilter();
		filtro.setOpcoesCombo(OpcoesComboBuscarAgenda.ID);
		filtro.setValorBusca("5");

		List<AgendaVo> resultado = agendaBusiness.filtrarAgendas(filtro);

		assertEquals(1, resultado.size());
		assertEquals("Fisioterapia", resultado.get(0).getNome());
	}

	@Test
	void deveLancarValidationExceptionParaIdNaoNumerico() {
		AgendaFilter filtro = new AgendaFilter();
		filtro.setOpcoesCombo(OpcoesComboBuscarAgenda.ID);
		filtro.setValorBusca("abc");

		assertThrows(ValidationException.class, () -> agendaBusiness.filtrarAgendas(filtro));
	}

	@Test
	void deveFiltrarPorNome() {
		List<AgendaVo> agendas = Arrays.asList(new AgendaVo("1", "Sala de Reunião", Periodo.AMBOS));
		when(dao.findAllByNome("Sala")).thenReturn(agendas);

		AgendaFilter filtro = new AgendaFilter();
		filtro.setOpcoesCombo(OpcoesComboBuscarAgenda.NOME);
		filtro.setValorBusca("Sala");

		List<AgendaVo> resultado = agendaBusiness.filtrarAgendas(filtro);

		assertEquals(1, resultado.size());
	}

	@Test
	void deveFiltrarPorPeriodoIgnorandoAcentoECase() {
		List<AgendaVo> agendas = Arrays.asList(new AgendaVo("5", "Exame Admissional", Periodo.MANHA));
		when(dao.findAllByPeriodo(Periodo.MANHA)).thenReturn(agendas);

		AgendaFilter filtro = new AgendaFilter();
		filtro.setOpcoesCombo(OpcoesComboBuscarAgenda.PERIODO);
		filtro.setValorBusca("manhã");

		List<AgendaVo> resultado = agendaBusiness.filtrarAgendas(filtro);

		assertEquals(1, resultado.size());
		verify(dao).findAllByPeriodo(Periodo.MANHA);
	}

	@Test
	void naoDeveFiltrarPorPeriodoVazio() {
		AgendaFilter filtro = new AgendaFilter();
		filtro.setOpcoesCombo(OpcoesComboBuscarAgenda.PERIODO);
		filtro.setValorBusca("");

		assertThrows(ValidationException.class, () -> agendaBusiness.filtrarAgendas(filtro));
	}

	@Test
	void naoDeveFiltrarPorPeriodoInvalido() {
		AgendaFilter filtro = new AgendaFilter();
		filtro.setOpcoesCombo(OpcoesComboBuscarAgenda.PERIODO);
		filtro.setValorBusca("NOITE");

		assertThrows(ValidationException.class, () -> agendaBusiness.filtrarAgendas(filtro));
	}
}