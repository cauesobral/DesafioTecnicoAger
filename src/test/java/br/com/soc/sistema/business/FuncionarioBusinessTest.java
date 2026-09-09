package br.com.soc.sistema.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.soc.sistema.dao.FuncionarioDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.FuncionarioFilter;
import br.com.soc.sistema.infra.OpcoesComboBuscar;
import br.com.soc.sistema.vo.FuncionarioVo;

@ExtendWith(MockitoExtension.class)
class FuncionarioBusinessTest {

	@Mock
	private FuncionarioDao dao;

	@Mock
	private CompromissoBusiness compromissoBusiness;

	@InjectMocks
	private FuncionarioBusiness funcionarioBusiness;

	@Test
	void deveSalvarFuncionarioValido() {
		FuncionarioVo funcionario = new FuncionarioVo(null, "João Silva");

		funcionarioBusiness.salvarFuncionario(funcionario);

		verify(dao).insertFuncionario(funcionario);
	}

	@Test
	void naoDeveSalvarFuncionarioComNomeVazio() {
		FuncionarioVo funcionario = new FuncionarioVo(null, "");

		assertThrows(BusinessException.class, () -> funcionarioBusiness.salvarFuncionario(funcionario));
		verify(dao, never()).insertFuncionario(any());
	}

	@Test
	void naoDeveAlterarFuncionarioComNomeVazio() {
		FuncionarioVo funcionario = new FuncionarioVo("3", "");

		assertThrows(BusinessException.class, () -> funcionarioBusiness.alterarFuncionario(funcionario));
		verify(dao, never()).updateFuncionario(any());
	}

	@Test
	void naoDeveAlterarFuncionarioSemCodigo() {
		FuncionarioVo funcionario = new FuncionarioVo(null, "João Silva");

		assertThrows(BusinessException.class, () -> funcionarioBusiness.alterarFuncionario(funcionario));
		verify(dao, never()).updateFuncionario(any());
	}

	@Test
	void deveExcluirCompromissosAntesDeExcluirFuncionario() {
		funcionarioBusiness.excluirFuncionario("3");

		InOrder ordem = inOrder(compromissoBusiness, dao);
		ordem.verify(compromissoBusiness).excluirCompromissosDoFuncionario(3);
		ordem.verify(dao).deleteFuncionario(3);
	}

	@Test
	void deveLancarBusinessExceptionParaCodigoInvalidoNaExclusao() {
		assertThrows(BusinessException.class, () -> funcionarioBusiness.excluirFuncionario("abc"));
		verify(dao, never()).deleteFuncionario(anyInt());
	}

	@Test
	void deveFiltrarPorId() {
		FuncionarioVo funcionario = new FuncionarioVo("2", "Maria Silva Santos");
		when(dao.findByCodigo(2)).thenReturn(funcionario);

		FuncionarioFilter filtro = FuncionarioFilter.builder();
		filtro.setOpcoesCombo(OpcoesComboBuscar.ID);
		filtro.setValorBusca("2");

		assertEquals(1, funcionarioBusiness.filtrarFuncionarios(filtro).size());
	}

	@Test
	void deveLancarBusinessExceptionParaIdNaoNumerico() {
		FuncionarioFilter filtro = FuncionarioFilter.builder();
		filtro.setOpcoesCombo(OpcoesComboBuscar.ID);
		filtro.setValorBusca("abc");

		assertThrows(BusinessException.class, () -> funcionarioBusiness.filtrarFuncionarios(filtro));
	}
}