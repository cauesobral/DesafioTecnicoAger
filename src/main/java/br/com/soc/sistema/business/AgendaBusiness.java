package br.com.soc.sistema.business;

import java.util.List;

import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.exception.ValidationException;
import br.com.soc.sistema.vo.AgendaVo;

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

    public List<AgendaVo> filtrarAgendasPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidationException("Nome da agenda deve ser informado.");
        }

        return dao.findAllByNome(nome);
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
}