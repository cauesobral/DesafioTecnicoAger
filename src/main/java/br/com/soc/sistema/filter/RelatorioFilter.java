package br.com.soc.sistema.filter;

import java.time.LocalDate;

public class RelatorioFilter {
	private LocalDate dataInicial;
	private LocalDate dataFinal;

	public LocalDate getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}
	public LocalDate getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}
}