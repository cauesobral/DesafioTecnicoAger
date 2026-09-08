package br.com.soc.sistema.business;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.exception.ValidationException;
import br.com.soc.sistema.filter.AgendaFilter;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.Periodo;

public class AgendaBusiness {

    private AgendaDao dao;

    public AgendaBusiness() {
        this.dao = new AgendaDao();
    }

    public List<AgendaVo> trazerTodasAsAgendas() {
        return dao.findAllAgendas();
    }

    public void salvarAgenda(AgendaVo agendaVo) {
        validarAgenda(agendaVo);

        dao.insertAgenda(agendaVo);
    }

    public void alterarAgenda(AgendaVo agendaVo) {
        validarAgenda(agendaVo);

        if (agendaVo.getRowid() == null || agendaVo.getRowid().isEmpty()) {
            throw new ValidationException("Código da agenda deve ser informado.");
        }

        dao.updateAgenda(agendaVo);
    }

    public void excluirAgenda(String codigo) {
    	try {
    		Integer codigoNumero = Integer.parseInt(codigo);

    		AgendaVo agenda = dao.findByCodigo(codigoNumero);

    		if (agenda == null) {
    			throw new BusinessException("Agenda não encontrada.");
    		}

    		if (new CompromissoBusiness().existemCompromissosNaAgenda(codigoNumero)) {
    			throw new BusinessException("Não é possível excluir uma agenda com compromissos cadastrados.");
    		}

    		dao.deleteAgenda(codigoNumero);

    	} catch (NumberFormatException e) {
    		throw new ValidationException("Código da agenda inválido.");
    	}
    }

    public AgendaVo buscarAgendaPor(String codigo) {
        try {
            Integer codigoNumero = Integer.parseInt(codigo);

            AgendaVo agenda = dao.findByCodigo(codigoNumero);

            if (agenda == null) {
                throw new BusinessException("Agenda não encontrada.");
            }

            return agenda;

        } catch (NumberFormatException e) {
            throw new ValidationException("Código da agenda inválido.");
        }
    }

    public List<AgendaVo> filtrarAgendas(AgendaFilter filtro) {
    	List<AgendaVo> agendas = new ArrayList<>();

    	switch (filtro.getOpcoesCombo()) {
    		case ID:
    			try {
    				Integer codigo = Integer.parseInt(filtro.getValorBusca());
    				AgendaVo agenda = dao.findByCodigo(codigo);
    				if (agenda != null) {
    					agendas.add(agenda);
    				}
    			} catch (NumberFormatException e) {
    				throw new ValidationException("Foi informado um caracter no lugar de um número.");
    			}
    			break;
    		case NOME:
    			agendas.addAll(dao.findAllByNome(filtro.getValorBusca()));
    			break;
    		case PERIODO:
    			if (filtro.getValorBusca() == null || filtro.getValorBusca().trim().isEmpty()) {
    				throw new ValidationException("Período deve ser informado.");
    			}
    			try {
    				String valorNormalizado = removerAcentos(filtro.getValorBusca().trim().toUpperCase());
    				Periodo periodo = Periodo.valueOf(valorNormalizado);
    				agendas.addAll(dao.findAllByPeriodo(periodo));
    			} catch (IllegalArgumentException e) {
    				throw new ValidationException("Período informado é inválido.");
    			}
    			break;
    	}

    	return agendas;
    }

    private void validarAgenda(AgendaVo agendaVo) {
        if (agendaVo == null) {
            throw new ValidationException("Agenda deve ser informada.");
        }

        if (agendaVo.getNome() == null || agendaVo.getNome().trim().isEmpty()) {
            throw new ValidationException("Nome da agenda deve ser informado.");
        }

        if (agendaVo.getPeriodo() == null) {
            throw new ValidationException("Período disponível deve ser informado.");
        }
    }
    
    private String removerAcentos(String texto) {
    	String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
    	return textoNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }
}