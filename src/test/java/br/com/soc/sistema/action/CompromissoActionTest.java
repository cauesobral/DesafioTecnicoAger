package br.com.soc.sistema.action;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.soc.sistema.business.AgendaBusiness;
import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;
import br.com.soc.sistema.vo.Periodo;
import com.opensymphony.xwork2.Action;

@ExtendWith(MockitoExtension.class)
class CompromissoActionTest {

	@Mock
	private CompromissoBusiness business;

	@Mock
	private FuncionarioBusiness funcionarioBusiness;

	@Mock
	private AgendaBusiness agendaBusiness;

	@InjectMocks
	private CompromissoAction compromissoAction;

	private CompromissoVo compromissoCompleto() {
		CompromissoVo compromisso = compromissoAction.getCompromissoVo();
		compromisso.setFuncionario(new FuncionarioVo("1", "João Silva"));
		compromisso.setAgenda(new AgendaVo("2", "Consultório", Periodo.MANHA));
		compromisso.setDataCompromisso(LocalDate.of(2026, 9, 10));
		compromisso.setHorarioCompromisso(LocalTime.of(8, 0));
		return compromisso;
	}

	@Test
	void deveListarTodosOsCompromissos() {
		when(business.trazerTodosOsCompromissos()).thenReturn(Arrays.asList(compromissoCompleto()));

		String resultado = compromissoAction.todos();

		assertEquals(Action.SUCCESS, resultado);
		assertEquals(1, compromissoAction.getCompromissos().size());
	}

	@Test
	void novoComCamposFaltandoDeveRetornarInput() {
		String resultado = compromissoAction.novo();

		assertEquals(Action.INPUT, resultado);
		verify(business, never()).salvarCompromisso(any());
	}

	@Test
	void novoSemRowidDeveSalvarERedirecionar() {
		compromissoCompleto();

		String resultado = compromissoAction.novo();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business).salvarCompromisso(compromissoAction.getCompromissoVo());
		verify(business, never()).alterarCompromisso(any());
	}

	@Test
	void novoComRowidDeveAlterarERedirecionar() {
		CompromissoVo compromisso = compromissoCompleto();
		compromisso.setRowid("9");

		String resultado = compromissoAction.novo();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business).alterarCompromisso(compromisso);
		verify(business, never()).salvarCompromisso(any());
	}

	@Test
	void editarSemRowidDeveAbrirFormularioVazio() {
		String resultado = compromissoAction.editar();

		assertEquals(Action.INPUT, resultado);
		verify(business, never()).buscarCompromissoPor(any());
	}

	@Test
	void editarComRowidDeveBuscarEPreencherFormulario() {
		compromissoAction.getCompromissoVo().setRowid("5");
		CompromissoVo encontrado = compromissoCompleto();
		when(business.buscarCompromissoPor("5")).thenReturn(encontrado);

		String resultado = compromissoAction.editar();

		assertEquals(Action.INPUT, resultado);
		assertEquals(encontrado, compromissoAction.getCompromissoVo());
	}

	@Test
	void excluirSemRowidDeveRedirecionarSemChamarBusiness() {
		String resultado = compromissoAction.excluir();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business, never()).excluirCompromisso(any());
	}

	@Test
	void excluirComRowidDeveChamarBusinessERedirecionar() {
		compromissoAction.getCompromissoVo().setRowid("6");

		String resultado = compromissoAction.excluir();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business).excluirCompromisso("6");
	}

	@Test
	void deveFiltrarCompromissos() {
		when(business.filtrarCompromissos(compromissoAction.getFiltro()))
				.thenReturn(Arrays.asList(compromissoCompleto()));

		String resultado = compromissoAction.filtrar();

		assertEquals(Action.SUCCESS, resultado);
		assertEquals(1, compromissoAction.getCompromissos().size());
	}

	@Test
	void deveRetornarListaDeFuncionariosParaOSelect() {
		when(funcionarioBusiness.trazerTodosOsFuncionarios())
				.thenReturn(Arrays.asList(new FuncionarioVo("1", "João Silva")));

		List<FuncionarioVo> lista = compromissoAction.getListaFuncionarios();

		assertEquals(1, lista.size());
	}

	@Test
	void deveRetornarListaDeAgendasParaOSelect() {
		when(agendaBusiness.trazerTodasAsAgendas())
				.thenReturn(Arrays.asList(new AgendaVo("2", "Consultório", Periodo.MANHA)));

		List<AgendaVo> lista = compromissoAction.getListaAgendas();

		assertEquals(1, lista.size());
	}
}