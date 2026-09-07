package br.com.soc.sistema.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.soc.sistema.exception.TechnicalException;
import br.com.soc.sistema.vo.CompromissoVo;

public class RelatorioExcelExporter {

	private static final String[] CABECALHO = {
			"Código Funcionário", "Nome Funcionário", "Código Agenda", "Nome Agenda", "Data", "Hora"
	};

	public InputStream exportar(List<CompromissoVo> compromissos) {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {

			XSSFSheet sheet = workbook.createSheet("Compromissos");

			CellStyle estiloCabecalho = criarEstiloCabecalho(workbook);

			Row linhaCabecalho = sheet.createRow(0);
			for (int i = 0; i < CABECALHO.length; i++) {
				Cell celula = linhaCabecalho.createCell(i);
				celula.setCellValue(CABECALHO[i]);
				celula.setCellStyle(estiloCabecalho);
			}

			int numeroLinha = 1;
			for (CompromissoVo compromisso : compromissos) {
				Row linha = sheet.createRow(numeroLinha++);

				linha.createCell(0).setCellValue(compromisso.getFuncionario().getRowid());
				linha.createCell(1).setCellValue(compromisso.getFuncionario().getNome());
				linha.createCell(2).setCellValue(compromisso.getAgenda().getRowid());
				linha.createCell(3).setCellValue(compromisso.getAgenda().getNome());
				linha.createCell(4).setCellValue(compromisso.getDataCompromisso().toString());
				linha.createCell(5).setCellValue(compromisso.getHorarioCompromisso().toString());
			}

			for (int i = 0; i < CABECALHO.length; i++) {
				sheet.autoSizeColumn(i);
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);

			return new ByteArrayInputStream(outputStream.toByteArray());

		} catch (IOException e) {
			throw new TechnicalException("Erro ao gerar arquivo Excel do relatório.");
		}
	}

	private CellStyle criarEstiloCabecalho(XSSFWorkbook workbook) {
		Font fonte = workbook.createFont();
		fonte.setBold(true);

		CellStyle estilo = workbook.createCellStyle();
		estilo.setFont(fonte);

		return estilo;
	}
}