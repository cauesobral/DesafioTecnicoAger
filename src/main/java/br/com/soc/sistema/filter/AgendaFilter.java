package br.com.soc.sistema.filter;

import br.com.soc.sistema.infra.OpcoesComboBuscarAgenda;

public class AgendaFilter {
	private OpcoesComboBuscarAgenda opcoesCombo;
	private String valorBusca;

	public boolean isNullOpcoesCombo() {
		return opcoesCombo == null;
	}

	public OpcoesComboBuscarAgenda getOpcoesCombo() {
		return opcoesCombo;
	}
	public void setOpcoesCombo(OpcoesComboBuscarAgenda opcoesCombo) {
		this.opcoesCombo = opcoesCombo;
	}
	public String getValorBusca() {
		return valorBusca;
	}
	public void setValorBusca(String valorBusca) {
		this.valorBusca = valorBusca;
	}
}