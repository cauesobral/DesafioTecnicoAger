package br.com.soc.sistema.action;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.infra.OpcoesComboBuscar;
import br.com.soc.sistema.vo.FuncionarioVo;
import com.opensymphony.xwork2.Action;

@ExtendWith(MockitoExtension.class)
class FuncionarioActionTest {

	@Mock
	private FuncionarioBusiness business;

	@InjectMocks
	private FuncionarioAction funcionarioAction;

	@Test
	void deveListarTodosOsFuncionarios() {
		when(business.trazerTodosOsFuncionarios()).thenReturn(List.of(new FuncionarioVo("1", "João Silva")));

		String resultado = funcionarioAction.todos();

		assertEquals(Action.SUCCESS, resultado);
		assertEquals(1, funcionarioAction.getFuncionarios().size());
	}

	@Test
	void filtrarSemOpcaoDeveRedirecionar() {
		String resultado = funcionarioAction.filtrar();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business, never()).filtrarFuncionarios(any());
	}

	@Test
	void filtrarComOpcaoDeveRetornarSuccess() {
		funcionarioAction.getFiltrar().setOpcoesCombo(OpcoesComboBuscar.NOME);
		funcionarioAction.getFiltrar().setValorBusca("Silva");

		when(business.filtrarFuncionarios(funcionarioAction.getFiltrar()))
				.thenReturn(List.of(new FuncionarioVo("1", "João Silva")));

		String resultado = funcionarioAction.filtrar();

		assertEquals(Action.SUCCESS, resultado);
		assertEquals(1, funcionarioAction.getFuncionarios().size());
	}

	@Test
	void novoSemNomeDeveRetornarInput() {
		String resultado = funcionarioAction.novo();

		assertEquals(Action.INPUT, resultado);
		verify(business, never()).salvarFuncionario(any());
	}

	@Test
	void novoComNomeSemRowidDeveSalvarERedirecionar() {
		funcionarioAction.getFuncionarioVo().setNome("Maria Santos");

		String resultado = funcionarioAction.novo();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business).salvarFuncionario(funcionarioAction.getFuncionarioVo());
		verify(business, never()).alterarFuncionario(any());
	}

	@Test
	void novoComRowidDeveAlterarERedirecionar() {
		funcionarioAction.getFuncionarioVo().setRowid("4");
		funcionarioAction.getFuncionarioVo().setNome("Maria Santos");

		String resultado = funcionarioAction.novo();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business).alterarFuncionario(funcionarioAction.getFuncionarioVo());
		verify(business, never()).salvarFuncionario(any());
	}

	@Test
	void excluirSemRowidDeveRedirecionarSemChamarBusiness() {
		String resultado = funcionarioAction.excluir();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business, never()).excluirFuncionario(any());
	}

	@Test
	void excluirComRowidDeveChamarBusinessERedirecionar() {
		funcionarioAction.getFuncionarioVo().setRowid("7");

		String resultado = funcionarioAction.excluir();

		assertEquals(br.com.soc.sistema.infra.Action.REDIRECT, resultado);
		verify(business).excluirFuncionario("7");
	}

	@Test
	void editarSemRowidDeveAbrirFormularioVazio() {
		String resultado = funcionarioAction.editar();

		assertEquals(Action.INPUT, resultado);
		verify(business, never()).buscarFuncionarioPor(any());
	}

	@Test
	void editarComRowidDeveBuscarEPreencherFormulario() {
		funcionarioAction.getFuncionarioVo().setRowid("2");
		FuncionarioVo funcionarioEncontrado = new FuncionarioVo("2", "Maria Silva Santos");
		when(business.buscarFuncionarioPor("2")).thenReturn(funcionarioEncontrado);

		String resultado = funcionarioAction.editar();

		assertEquals(Action.INPUT, resultado);
		assertEquals("Maria Silva Santos", funcionarioAction.getFuncionarioVo().getNome());
	}

	@Test
	void listaOpcoesComboDeveConterAsDuasOpcoes() {
		List<OpcoesComboBuscar> opcoes = funcionarioAction.getListaOpcoesCombo();

		assertEquals(2, opcoes.size());
	}
}