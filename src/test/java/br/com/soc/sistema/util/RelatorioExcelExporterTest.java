package br.com.soc.sistema.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;
import br.com.soc.sistema.vo.Periodo;

class RelatorioExcelExporterTest {

	private final RelatorioExcelExporter exporter = new RelatorioExcelExporter();

	private CompromissoVo compromisso(String rowid, String nomeFunc, String nomeAgenda) {
		CompromissoVo compromisso = new CompromissoVo();
		compromisso.setRowid(rowid);
		compromisso.setFuncionario(new FuncionarioVo("1", nomeFunc));
		compromisso.setAgenda(new AgendaVo("2", nomeAgenda, Periodo.MANHA));
		compromisso.setDataCompromisso(LocalDate.of(2026, 9, 10));
		compromisso.setHorarioCompromisso(LocalTime.of(8, 30));
		return compromisso;
	}

	@Test
	void deveGerarPlanilhaComCabecalhoCorreto() throws IOException {
		InputStream resultado = exporter.exportar(new ArrayList<>());

		try (XSSFWorkbook workbook = new XSSFWorkbook(resultado)) {
			XSSFSheet sheet = workbook.getSheetAt(0);
			Row cabecalho = sheet.getRow(0);

			assertEquals("Código Compromisso", cabecalho.getCell(0).getStringCellValue());
			assertEquals("Código Funcionário", cabecalho.getCell(1).getStringCellValue());
			assertEquals("Nome Funcionário", cabecalho.getCell(2).getStringCellValue());
			assertEquals("Código Agenda", cabecalho.getCell(3).getStringCellValue());
			assertEquals("Nome Agenda", cabecalho.getCell(4).getStringCellValue());
			assertEquals("Data", cabecalho.getCell(5).getStringCellValue());
			assertEquals("Hora", cabecalho.getCell(6).getStringCellValue());
		}
	}

	@Test
	void deveGerarPlanilhaSemLinhasDeDadosQuandoListaVazia() throws IOException {
		InputStream resultado = exporter.exportar(new ArrayList<>());

		try (XSSFWorkbook workbook = new XSSFWorkbook(resultado)) {
			XSSFSheet sheet = workbook.getSheetAt(0);

			assertEquals(0, sheet.getLastRowNum());
		}
	}

	@Test
	void deveEscreverDadosDoCompromissoNaLinhaCorreta() throws IOException {
		List<CompromissoVo> compromissos = List.of(compromisso("10", "João Silva", "Consultório"));

		InputStream resultado = exporter.exportar(compromissos);

		try (XSSFWorkbook workbook = new XSSFWorkbook(resultado)) {
			XSSFSheet sheet = workbook.getSheetAt(0);
			Row linha = sheet.getRow(1);

			assertEquals("10", linha.getCell(0).getStringCellValue());
			assertEquals("1", linha.getCell(1).getStringCellValue());
			assertEquals("João Silva", linha.getCell(2).getStringCellValue());
			assertEquals("2", linha.getCell(3).getStringCellValue());
			assertEquals("Consultório", linha.getCell(4).getStringCellValue());
			assertEquals("10/09/2026", linha.getCell(5).getStringCellValue());
			assertEquals("08:30", linha.getCell(6).getStringCellValue());
		}
	}

	@Test
	void deveEscreverMultiplosCompromissosEmLinhasSequenciais() throws IOException {
		List<CompromissoVo> compromissos = List.of(
				compromisso("1", "João Silva", "Sala A"),
				compromisso("2", "Maria Santos", "Sala B")
		);

		InputStream resultado = exporter.exportar(compromissos);

		try (XSSFWorkbook workbook = new XSSFWorkbook(resultado)) {
			XSSFSheet sheet = workbook.getSheetAt(0);

			assertEquals(2, sheet.getLastRowNum());
			assertEquals("João Silva", sheet.getRow(1).getCell(2).getStringCellValue());
			assertEquals("Maria Santos", sheet.getRow(2).getCell(2).getStringCellValue());
		}
	}
}