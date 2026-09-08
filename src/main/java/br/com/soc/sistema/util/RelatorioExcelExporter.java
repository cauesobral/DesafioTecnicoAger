package br.com.soc.sistema.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.soc.sistema.exception.TechnicalException;
import br.com.soc.sistema.vo.CompromissoVo;

public class RelatorioExcelExporter {

	private static final byte[] COR_TURQUESA = {(byte) 0x00, (byte) 0xA3, (byte) 0xA6};
	private static final byte[] COR_DOURADO = {(byte) 0xFB, (byte) 0xBA, (byte) 0x00};
	private static final byte[] COR_BRANCO = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
	private static final byte[] COR_TURQUESA_CLARO = {(byte) 0xE0, (byte) 0xF5, (byte) 0xF5};

	private static final String[] CABECALHO = {
			"Código Compromisso", "Código Funcionário", "Nome Funcionário",
			"Código Agenda", "Nome Agenda", "Data", "Hora"
	};

	public InputStream exportar(List<CompromissoVo> compromissos) {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {

			XSSFSheet sheet = workbook.createSheet("Compromissos");
			sheet.createFreezePane(0, 1);

			CellStyle estiloCabecalho = criarEstiloCabecalho(workbook);
			CellStyle estiloLinhaPar = criarEstiloLinha(workbook, false);
			CellStyle estiloLinhaImpar = criarEstiloLinha(workbook, true);

			Row linhaCabecalho = sheet.createRow(0);
			linhaCabecalho.setHeightInPoints(22);

			for (int i = 0; i < CABECALHO.length; i++) {
				Cell celula = linhaCabecalho.createCell(i);
				celula.setCellValue(CABECALHO[i]);
				celula.setCellStyle(estiloCabecalho);
			}

			int numeroLinha = 1;
			for (CompromissoVo compromisso : compromissos) {
				Row linha = sheet.createRow(numeroLinha);
				CellStyle estiloAtual = (numeroLinha % 2 == 0) ? estiloLinhaPar : estiloLinhaImpar;

				preencherCelula(linha, 0, compromisso.getRowid(), estiloAtual);
				preencherCelula(linha, 1, compromisso.getFuncionario().getRowid(), estiloAtual);
				preencherCelula(linha, 2, compromisso.getFuncionario().getNome(), estiloAtual);
				preencherCelula(linha, 3, compromisso.getAgenda().getRowid(), estiloAtual);
				preencherCelula(linha, 4, compromisso.getAgenda().getNome(), estiloAtual);
				preencherCelula(linha, 5, compromisso.getDataCompromisso().toString(), estiloAtual);
				preencherCelula(linha, 6, compromisso.getHorarioCompromisso().toString(), estiloAtual);

				numeroLinha++;
			}

			if (numeroLinha > 1) {
				sheet.setAutoFilter(new CellRangeAddress(0, numeroLinha - 1, 0, CABECALHO.length - 1));
			}

			for (int i = 0; i < CABECALHO.length; i++) {
				sheet.autoSizeColumn(i);
				sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);

			return new ByteArrayInputStream(outputStream.toByteArray());

		} catch (IOException e) {
			throw new TechnicalException("Erro ao gerar arquivo Excel do relatório.");
		}
	}

	private void preencherCelula(Row linha, int coluna, String valor, CellStyle estilo) {
		Cell celula = linha.createCell(coluna);
		celula.setCellValue(valor);
		celula.setCellStyle(estilo);
	}

	private CellStyle criarEstiloCabecalho(XSSFWorkbook workbook) {
		Font fonte = workbook.createFont();
		fonte.setBold(true);
		fonte.setFontHeightInPoints((short) 11);
		fonte.setColor(new XSSFColor(COR_BRANCO, null).getIndexed() != -1
				? IndexedColors.WHITE.getIndex()
				: IndexedColors.WHITE.getIndex());

		XSSFCellStyle estilo = (XSSFCellStyle) workbook.createCellStyle();
		estilo.setFont(fonte);
		estilo.setFillForegroundColor(new XSSFColor(COR_TURQUESA, null));
		estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		estilo.setAlignment(HorizontalAlignment.CENTER);
		estilo.setBorderBottom(BorderStyle.MEDIUM);
		estilo.setBottomBorderColor(new XSSFColor(COR_DOURADO, null));
		estilo.setBorderTop(BorderStyle.THIN);
		estilo.setBorderLeft(BorderStyle.THIN);
		estilo.setBorderRight(BorderStyle.THIN);

		return estilo;
	}

	private CellStyle criarEstiloLinha(XSSFWorkbook workbook, boolean linhaImpar) {
		XSSFCellStyle estilo = (XSSFCellStyle) workbook.createCellStyle();

		estilo.setFillForegroundColor(new XSSFColor(linhaImpar ? COR_TURQUESA_CLARO : COR_BRANCO, null));
		estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		estilo.setBorderBottom(BorderStyle.THIN);
		estilo.setBorderTop(BorderStyle.THIN);
		estilo.setBorderLeft(BorderStyle.THIN);
		estilo.setBorderRight(BorderStyle.THIN);
		estilo.setAlignment(HorizontalAlignment.LEFT);

		return estilo;
	}
}