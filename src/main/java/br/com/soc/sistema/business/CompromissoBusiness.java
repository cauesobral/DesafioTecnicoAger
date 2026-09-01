package br.com.soc.sistema.business;

import java.time.LocalTime;
import java.util.List;

import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.dao.CompromissoDao;
import br.com.soc.sistema.dao.FuncionarioDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.exception.ValidationException;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;
import br.com.soc.sistema.vo.Periodo;

public class CompromissoBusiness {

    private CompromissoDao compromissoDao;
    private FuncionarioDao funcionarioDao;
    private AgendaDao agendaDao;

    public CompromissoBusiness() {
        this.compromissoDao = new CompromissoDao();
        this.funcionarioDao = new FuncionarioDao();
        this.agendaDao = new AgendaDao();
    }

    public List<CompromissoVo> trazerTodosOsCompromissos() {
        return compromissoDao.findAllCompromissos();
    }

    public void salvarCompromisso(CompromissoVo compromissoVo) {
        validarCompromisso(compromissoVo);

        FuncionarioVo funcionario = buscarFuncionario(
                compromissoVo.getFuncionario().getRowid()
        );

        AgendaVo agenda = buscarAgenda(
                compromissoVo.getAgenda().getRowid()
        );

        compromissoVo.setFuncionario(funcionario);
        compromissoVo.setAgenda(agenda);

        validarDisponibilidade(agenda, compromissoVo.getHorarioCompromisso());

        compromissoDao.insertCompromisso(compromissoVo);
    }

    public void alterarCompromisso(CompromissoVo compromissoVo) {
        validarCompromisso(compromissoVo);

        if (compromissoVo.getRowid() == null
                || compromissoVo.getRowid().trim().isEmpty()) {

            throw new ValidationException(
                    "Código do compromisso deve ser informado."
            );
        }

        FuncionarioVo funcionario = buscarFuncionario(
                compromissoVo.getFuncionario().getRowid()
        );

        AgendaVo agenda = buscarAgenda(
                compromissoVo.getAgenda().getRowid()
        );

        compromissoVo.setFuncionario(funcionario);
        compromissoVo.setAgenda(agenda);

        validarDisponibilidade(
                agenda,
                compromissoVo.getHorarioCompromisso()
        );

        compromissoDao.updateCompromisso(compromissoVo);
    }

    public void excluirCompromisso(String codigo) {
        try {
            Integer codigoNumero = Integer.parseInt(codigo);

            CompromissoVo compromisso =
                    compromissoDao.findByCodigo(codigoNumero);

            if (compromisso == null) {
                throw new BusinessException(
                        "Compromisso não encontrado."
                );
            }

            compromissoDao.deleteCompromisso(codigoNumero);

        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "Código do compromisso inválido."
            );
        }
    }

    public CompromissoVo buscarCompromissoPor(String codigo) {
        try {
            Integer codigoNumero = Integer.parseInt(codigo);

            CompromissoVo compromisso =
                    compromissoDao.findByCodigo(codigoNumero);

            if (compromisso == null) {
                throw new BusinessException(
                        "Compromisso não encontrado."
                );
            }

            return compromisso;

        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "Código do compromisso inválido."
            );
        }
    }

    public List<CompromissoVo> buscarCompromissosPorPeriodo(
            java.time.LocalDate dataInicial,
            java.time.LocalDate dataFinal) {

        if (dataInicial == null) {
            throw new ValidationException(
                    "Data inicial deve ser informada."
            );
        }

        if (dataFinal == null) {
            throw new ValidationException(
                    "Data final deve ser informada."
            );
        }

        if (dataInicial.isAfter(dataFinal)) {
            throw new ValidationException(
                    "Data inicial não pode ser posterior à data final."
            );
        }

        return compromissoDao.findByPeriodo(
                dataInicial,
                dataFinal
        );
    }

    public void excluirCompromissosDoFuncionario(
            String codigoFuncionario) {

        try {
            Integer codigo = Integer.parseInt(codigoFuncionario);

            FuncionarioVo funcionario =
                    funcionarioDao.findByCodigo(codigo);

            if (funcionario == null) {
                throw new BusinessException(
                        "Funcionário não encontrado."
                );
            }

            compromissoDao.deleteByFuncionario(codigo);

        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "Código do funcionário inválido."
            );
        }
    }

    public boolean possuiCompromissosNaAgenda(
            String codigoAgenda) {

        try {
            Integer codigo = Integer.parseInt(codigoAgenda);

            AgendaVo agenda = agendaDao.findByCodigo(codigo);

            if (agenda == null) {
                throw new BusinessException(
                        "Agenda não encontrada."
                );
            }

            return compromissoDao.existsByAgenda(codigo);

        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "Código da agenda inválido."
            );
        }
    }

    private void validarCompromisso(
            CompromissoVo compromissoVo) {

        if (compromissoVo == null) {
            throw new ValidationException(
                    "Compromisso deve ser informado."
            );
        }

        if (compromissoVo.getFuncionario() == null) {
            throw new ValidationException(
                    "Funcionário deve ser informado."
            );
        }

        if (compromissoVo.getFuncionario().getRowid() == null
                || compromissoVo.getFuncionario().getRowid().trim().isEmpty()) {

            throw new ValidationException(
                    "Código do funcionário deve ser informado."
            );
        }

        if (compromissoVo.getAgenda() == null) {
            throw new ValidationException(
                    "Agenda deve ser informada."
            );
        }

        if (compromissoVo.getAgenda().getRowid() == null
                || compromissoVo.getAgenda().getRowid().trim().isEmpty()) {

            throw new ValidationException(
                    "Código da agenda deve ser informado."
            );
        }

        if (compromissoVo.getDataCompromisso() == null) {
            throw new ValidationException(
                    "Data do compromisso deve ser informada."
            );
        }

        if (compromissoVo.getHorarioCompromisso() == null) {
            throw new ValidationException(
                    "Horário do compromisso deve ser informado."
            );
        }
    }

    private FuncionarioVo buscarFuncionario(String codigo) {

        try {
            Integer codigoNumero = Integer.parseInt(codigo);

            FuncionarioVo funcionario =
                    funcionarioDao.findByCodigo(codigoNumero);

            if (funcionario == null) {
                throw new BusinessException(
                        "Funcionário não encontrado."
                );
            }

            return funcionario;

        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "Código do funcionário inválido."
            );
        }
    }

    private AgendaVo buscarAgenda(String codigo) {

        try {
            Integer codigoNumero = Integer.parseInt(codigo);

            AgendaVo agenda =
                    agendaDao.findByCodigo(codigoNumero);

            if (agenda == null) {
                throw new BusinessException(
                        "Agenda não encontrada."
                );
            }

            return agenda;

        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "Código da agenda inválido."
            );
        }
    }

    private void validarDisponibilidade(
            AgendaVo agenda,
            LocalTime horario) {

        Periodo periodo = agenda.getPeriodo();

        if (periodo == null) {
            throw new BusinessException(
                    "A agenda não possui período disponível informado."
            );
        }


        if (periodo == Periodo.AMBOS) {
            return;
        }

        if (periodo == Periodo.MANHA
                && horario.isBefore(LocalTime.NOON)) {
            return;
        }

        if (periodo == Periodo.TARDE
                && !horario.isBefore(LocalTime.NOON)) {
            return;
        }

        throw new BusinessException(
                "O horário informado está fora da disponibilidade da agenda."
        );
    }
}

