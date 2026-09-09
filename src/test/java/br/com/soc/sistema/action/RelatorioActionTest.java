package br.com.soc.sistema.action;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.util.RelatorioExcelExporter;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;
import br.com.soc.sistema.vo.Periodo;
import com.opensymphony.xwork2.Action;

@ExtendWith(MockitoExtension.class)
class RelatorioActionTest {

	@Mock
	private CompromissoBusiness business;

	@Mock
	private RelatorioExcelExporter excelExporter;

	@InjectMocks
	private RelatorioAction relatorioAction;

	private CompromissoVo compromissoQualquer() {
		CompromissoVo compromisso = new CompromissoVo();
		compromisso.setRowid("1");
		compromisso.setFuncionario(new FuncionarioVo("1", "João Silva"));
		compromisso.setAgenda(new AgendaVo("2", "Consultório", Periodo.MANHA));
		compromisso.setDataCompromisso(LocalDate.of(2026, 9, 10));
		return compromisso;
	}

	@Test
	void formularioDeveRetornarSuccessSemPesquisar() {
		String resultado = relatorioAction.formulario();

		assertEquals(Action.SUCCESS, resultado);
		assertFalse(relatorioAction.isPesquisado());
	}

	@Test
	void gerarSemDatasDeveRetornarInput() {
		String resultado = relatorioAction.gerar();

		assertEquals(Action.INPUT, resultado);
		assertFalse(relatorioAction.isPesquisado());
		verify(business, never()).buscarCompromissosPorPeriodo(any(), any());
	}

	@Test
	void gerarComDatasDeveBuscarEMarcarPesquisado() {
		LocalDate inicio = LocalDate.of(2026, 9, 1);
		LocalDate fim = LocalDate.of(2026, 9, 30);
		relatorioAction.getFiltro().setDataInicial(inicio);
		relatorioAction.getFiltro().setDataFinal(fim);

		List<CompromissoVo> compromissos = Arrays.asList(compromissoQualquer());
		when(business.buscarCompromissosPorPeriodo(inicio, fim)).thenReturn(compromissos);

		String resultado = relatorioAction.gerar();

		assertEquals(Action.SUCCESS, resultado);
		assertTrue(relatorioAction.isPesquisado());
		assertEquals(1, relatorioAction.getCompromissos().size());
	}

	@Test
	void exportarSemDatasDeveRetornarInput() {
		String resultado = relatorioAction.exportar();

		assertEquals(Action.INPUT, resultado);
		verify(business, never()).buscarCompromissosPorPeriodo(any(), any());
		verify(excelExporter, never()).exportar(any());
	}

	@Test
	void exportarComDatasDeveGerarInputStream() {
		LocalDate inicio = LocalDate.of(2026, 9, 1);
		LocalDate fim = LocalDate.of(2026, 9, 30);
		relatorioAction.getFiltro().setDataInicial(inicio);
		relatorioAction.getFiltro().setDataFinal(fim);

		List<CompromissoVo> compromissos = Arrays.asList(compromissoQualquer());
		when(business.buscarCompromissosPorPeriodo(inicio, fim)).thenReturn(compromissos);

		InputStream streamFalso = new ByteArrayInputStream(new byte[0]);
		when(excelExporter.exportar(compromissos)).thenReturn(streamFalso);

		String resultado = relatorioAction.exportar();

		assertEquals("excel", resultado);
		assertNotNull(relatorioAction.getExcelInputStream());
	}
}