package br.com.soc.sistema.action;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.soc.sistema.business.AgendaBusiness;
import br.com.soc.sistema.infra.OpcoesComboBuscarAgenda;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.Periodo;
import com.opensymphony.xwork2.Action;

@ExtendWith(MockitoExtension.class)
class AgendaActionTest {

	@Mock
	private AgendaBusiness business;

	@InjectMocks
	private AgendaAction agendaAction;

	@Test
	void deveListarTodasAsAgendas() {
		List<AgendaVo> agendas = Arrays.asList(new AgendaVo("1", "Sala de Reunião", Periodo.AMBOS));
		when(business.trazerTodasAsAgendas()).thenReturn(agendas);

		String resultado = agendaAction.todos();

		assertEquals(Action.SUCCESS, resultado);
		assertEquals(1, agendaAction.getAgendas().size());
	}

	@Test
	void filtrarSemOpcaoDeveRedirecionar() {
		String resultado = agendaAction.filtrar();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business, never()).filtrarAgendas(any());
	}

	@Test
	void filtrarComOpcaoDeveRetornarSuccess() {
		agendaAction.getFiltro().setOpcoesCombo(OpcoesComboBuscarAgenda.NOME);
		agendaAction.getFiltro().setValorBusca("Sala");

		when(business.filtrarAgendas(agendaAction.getFiltro()))
				.thenReturn(Arrays.asList(new AgendaVo("1", "Sala de Reunião", Periodo.AMBOS)));

		String resultado = agendaAction.filtrar();

		assertEquals(Action.SUCCESS, resultado);
		assertEquals(1, agendaAction.getAgendas().size());
	}

	@Test
	void novoSemNomeDeveRetornarInput() {
		String resultado = agendaAction.novo();

		assertEquals(Action.INPUT, resultado);
		verify(business, never()).salvarAgenda(any());
	}

	@Test
	void novoComNomeSemRowidDeveSalvarERedirecionar() {
		agendaAction.getAgendaVo().setNome("Fisioterapia");
		agendaAction.getAgendaVo().setPeriodo(Periodo.TARDE);

		String resultado = agendaAction.novo();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business).salvarAgenda(agendaAction.getAgendaVo());
		verify(business, never()).alterarAgenda(any());
	}

	@Test
	void novoComRowidDeveAlterarERedirecionar() {
		agendaAction.getAgendaVo().setRowid("3");
		agendaAction.getAgendaVo().setNome("Fisioterapia");
		agendaAction.getAgendaVo().setPeriodo(Periodo.TARDE);

		String resultado = agendaAction.novo();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business).alterarAgenda(agendaAction.getAgendaVo());
		verify(business, never()).salvarAgenda(any());
	}

	@Test
	void editarSemRowidDeveAbrirFormularioVazio() {
		String resultado = agendaAction.editar();

		assertEquals(Action.INPUT, resultado);
		verify(business, never()).buscarAgendaPor(any());
	}

	@Test
	void editarComRowidDeveBuscarEPreencherFormulario() {
		agendaAction.getAgendaVo().setRowid("5");
		AgendaVo agendaEncontrada = new AgendaVo("5", "Exame Admissional", Periodo.MANHA);
		when(business.buscarAgendaPor("5")).thenReturn(agendaEncontrada);

		String resultado = agendaAction.editar();

		assertEquals(Action.INPUT, resultado);
		assertEquals("Exame Admissional", agendaAction.getAgendaVo().getNome());
	}

	@Test
	void excluirSemRowidDeveRedirecionarSemChamarBusiness() {
		String resultado = agendaAction.excluir();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business, never()).excluirAgenda(any());
	}

	@Test
	void excluirComRowidDeveChamarBusinessERedirecionar() {
		agendaAction.getAgendaVo().setRowid("2");

		String resultado = agendaAction.excluir();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business).excluirAgenda("2");
	}

	@Test
	void listaOpcoesComboDeveConterAsTresOpcoes() {
		List<OpcoesComboBuscarAgenda> opcoes = agendaAction.getListaOpcoesCombo();

		assertEquals(3, opcoes.size());
	}
}