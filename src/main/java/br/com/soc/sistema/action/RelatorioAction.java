package br.com.soc.sistema.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.filter.RelatorioFilter;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.util.RelatorioExcelExporter;
import br.com.soc.sistema.vo.CompromissoVo;

public class RelatorioAction extends Action {
	private List<CompromissoVo> compromissos = new ArrayList<>();
	private CompromissoBusiness business = new CompromissoBusiness();
	private RelatorioExcelExporter excelExporter = new RelatorioExcelExporter();
	private RelatorioFilter filtro = new RelatorioFilter();
	private InputStream excelInputStream;
	private boolean pesquisado;

	public String formulario() {
		return SUCCESS;
	}

	public String gerar() {
		if (filtro.getDataInicial() == null || filtro.getDataFinal() == null) {
			return INPUT;
		}
		compromissos.addAll(business.buscarCompromissosPorPeriodo(filtro.getDataInicial(), filtro.getDataFinal()));
		pesquisado = true;
		return SUCCESS;
	}

	public String exportar() {
		if (filtro.getDataInicial() == null || filtro.getDataFinal() == null) {
			return INPUT;
		}
		List<CompromissoVo> resultado = business.buscarCompromissosPorPeriodo(filtro.getDataInicial(), filtro.getDataFinal());
		excelInputStream = excelExporter.exportar(resultado);
		return "excel";
	}

	public List<CompromissoVo> getCompromissos() {
		return compromissos;
	}
	public void setCompromissos(List<CompromissoVo> compromissos) {
		this.compromissos = compromissos;
	}
	
	public boolean isPesquisado() {
		return pesquisado;
	}
	public void setPesquisado(boolean pesquisado) {
		this.pesquisado = pesquisado;
	}
	
	public RelatorioFilter getFiltro() {
		return filtro;
	}
	public void setFiltro(RelatorioFilter filtro) {
		this.filtro = filtro;
	}
	public InputStream getExcelInputStream() {
		return excelInputStream;
	}
}