package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.AgendaBusiness;
import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.filter.CompromissoFilter;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class CompromissoAction extends Action {
	private List<CompromissoVo> compromissos = new ArrayList<>();
	private CompromissoBusiness business = new CompromissoBusiness();
	private FuncionarioBusiness funcionarioBusiness = new FuncionarioBusiness();
	private AgendaBusiness agendaBusiness = new AgendaBusiness();
	private CompromissoVo compromissoVo = new CompromissoVo();
	private CompromissoFilter filtro = new CompromissoFilter();

	public String todos() {
		compromissos.addAll(business.trazerTodosOsCompromissos());
		return SUCCESS;
	}

	public String novo() {
		if (compromissoVo.getFuncionario() == null || compromissoVo.getFuncionario().getRowid() == null
				|| compromissoVo.getFuncionario().getRowid().isEmpty()
				|| compromissoVo.getAgenda() == null || compromissoVo.getAgenda().getRowid() == null
				|| compromissoVo.getAgenda().getRowid().isEmpty()
				|| compromissoVo.getDataCompromisso() == null
				|| compromissoVo.getHorarioCompromisso() == null) {
			return INPUT;
		}
		if (compromissoVo.getRowid() == null || compromissoVo.getRowid().isEmpty()) {
			business.salvarCompromisso(compromissoVo);
		} else {
			business.alterarCompromisso(compromissoVo);
		}
		return REDIRECT;
	}

	public String editar() {
		if (compromissoVo.getRowid() != null && !compromissoVo.getRowid().isEmpty()) {
			compromissoVo = business.buscarCompromissoPor(compromissoVo.getRowid());
		}
		return INPUT;
	}

	public String excluir() {
		if (compromissoVo.getRowid() == null || compromissoVo.getRowid().isEmpty()) {
			return REDIRECT;
		}
		business.excluirCompromisso(compromissoVo.getRowid());
		return REDIRECT;
	}

	public List<FuncionarioVo> getListaFuncionarios() {
		return funcionarioBusiness.trazerTodosOsFuncionarios();
	}

	public List<AgendaVo> getListaAgendas() {
		return agendaBusiness.trazerTodasAsAgendas();
	}

	public List<CompromissoVo> getCompromissos() {
		return compromissos;
	}
	public void setCompromissos(List<CompromissoVo> compromissos) {
		this.compromissos = compromissos;
	}
	public CompromissoVo getCompromissoVo() {
		return compromissoVo;
	}
	public void setCompromissoVo(CompromissoVo compromissoVo) {
		this.compromissoVo = compromissoVo;
	}
	
	public String filtrar() {
		compromissos.addAll(business.filtrarCompromissos(filtro));
		return SUCCESS;
	}

	public CompromissoFilter getFiltro() {
		return filtro;
	}
	public void setFiltro(CompromissoFilter filtro) {
		this.filtro = filtro;
	}
}