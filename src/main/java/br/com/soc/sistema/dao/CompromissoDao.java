package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.soc.sistema.exception.PersistenceException;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class CompromissoDao extends Dao {

    public void insertCompromisso(CompromissoVo compromissoVo) {

        StringBuilder query = new StringBuilder(
                "INSERT INTO compromisso "
                + "(cd_funcionario, cd_agenda, dt_compromisso, hr_compromisso) "
                + "VALUES (?, ?, ?, ?)"
        );

        try (
                Connection connection = getConexao();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query.toString())
        ) {

            int i = 1;

            preparedStatement.setInt(
                    i++,
                    Integer.parseInt(compromissoVo.getFuncionario().getRowid())
            );

            preparedStatement.setInt(
                    i++,
                    Integer.parseInt(compromissoVo.getAgenda().getRowid())
            );

            preparedStatement.setObject(
                    i++,
                    compromissoVo.getDataCompromisso()
            );

            preparedStatement.setObject(
                    i++,
                    compromissoVo.getHorarioCompromisso()
            );

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Erro ao inserir compromisso.",
                    e
            );
        }
    }

    public List<CompromissoVo> findAllCompromissos() {

        StringBuilder query = new StringBuilder(
                "SELECT "
                + "c.rowid id, "
                + "c.dt_compromisso data_compromisso, "
                + "c.hr_compromisso horario_compromisso, "
                + "f.rowid funcionario_id, "
                + "f.nm_funcionario funcionario_nome, "
                + "a.rowid agenda_id, "
                + "a.nm_agenda agenda_nome, "
                + "a.periodo_disponivel agenda_periodo "
                + "FROM compromisso c "
                + "INNER JOIN funcionario f ON f.rowid = c.cd_funcionario "
                + "INNER JOIN agenda a ON a.rowid = c.cd_agenda "
                + "ORDER BY c.dt_compromisso, c.hr_compromisso"
        );

        try (
                Connection connection = getConexao();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query.toString());
                ResultSet resultSet =
                        preparedStatement.executeQuery()
        ) {

            List<CompromissoVo> compromissos = new ArrayList<>();

            while (resultSet.next()) {
                compromissos.add(mapearCompromisso(resultSet));
            }

            return compromissos;

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Erro ao consultar compromissos.",
                    e
            );
        }
    }

    public CompromissoVo findByCodigo(Integer codigo) {

        StringBuilder query = new StringBuilder(
                "SELECT "
                + "c.rowid id, "
                + "c.dt_compromisso data_compromisso, "
                + "c.hr_compromisso horario_compromisso, "
                + "f.rowid funcionario_id, "
                + "f.nm_funcionario funcionario_nome, "
                + "a.rowid agenda_id, "
                + "a.nm_agenda agenda_nome, "
                + "a.periodo_disponivel agenda_periodo "
                + "FROM compromisso c "
                + "INNER JOIN funcionario f ON f.rowid = c.cd_funcionario "
                + "INNER JOIN agenda a ON a.rowid = c.cd_agenda "
                + "WHERE c.rowid = ?"
        );

        try (
                Connection connection = getConexao();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query.toString())
        ) {

            preparedStatement.setInt(1, codigo);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearCompromisso(resultSet);
                }

                return null;
            }

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Erro ao consultar compromisso por código.",
                    e
            );
        }
    }

    public void updateCompromisso(CompromissoVo compromissoVo) {

        StringBuilder query = new StringBuilder(
                "UPDATE compromisso SET "
                + "cd_funcionario = ?, "
                + "cd_agenda = ?, "
                + "dt_compromisso = ?, "
                + "hr_compromisso = ? "
                + "WHERE rowid = ?"
        );

        try (
                Connection connection = getConexao();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query.toString())
        ) {

            int i = 1;

            preparedStatement.setInt(
                    i++,
                    Integer.parseInt(
                            compromissoVo.getFuncionario().getRowid()
                    )
            );

            preparedStatement.setInt(
                    i++,
                    Integer.parseInt(
                            compromissoVo.getAgenda().getRowid()
                    )
            );

            preparedStatement.setObject(
                    i++,
                    compromissoVo.getDataCompromisso()
            );

            preparedStatement.setObject(
                    i++,
                    compromissoVo.getHorarioCompromisso()
            );

            preparedStatement.setInt(
                    i++,
                    Integer.parseInt(compromissoVo.getRowid())
            );

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Erro ao alterar compromisso.",
                    e
            );
        }
    }

    public void deleteCompromisso(Integer codigo) {

        StringBuilder query = new StringBuilder(
                "DELETE FROM compromisso WHERE rowid = ?"
        );

        try (
                Connection connection = getConexao();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query.toString())
        ) {

            preparedStatement.setInt(1, codigo);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Erro ao excluir compromisso.",
                    e
            );
        }
    }

    public void deleteByFuncionario(Integer codigoFuncionario) {

        StringBuilder query = new StringBuilder(
                "DELETE FROM compromisso WHERE cd_funcionario = ?"
        );

        try (
                Connection connection = getConexao();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query.toString())
        ) {

            preparedStatement.setInt(1, codigoFuncionario);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Erro ao excluir compromissos do funcionário.",
                    e
            );
        }
    }

    public boolean existsByAgenda(Integer codigoAgenda) {

        StringBuilder query = new StringBuilder(
                "SELECT 1 FROM compromisso "
                + "WHERE cd_agenda = ? "
                + "LIMIT 1"
        );

        try (
                Connection connection = getConexao();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query.toString())
        ) {

            preparedStatement.setInt(1, codigoAgenda);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Erro ao verificar compromissos da agenda.",
                    e
            );
        }
    }

    public List<CompromissoVo> findByPeriodo(
            java.time.LocalDate dataInicial,
            java.time.LocalDate dataFinal) {

        StringBuilder query = new StringBuilder(
                "SELECT "
                + "c.rowid id, "
                + "c.dt_compromisso data_compromisso, "
                + "c.hr_compromisso horario_compromisso, "
                + "f.rowid funcionario_id, "
                + "f.nm_funcionario funcionario_nome, "
                + "a.rowid agenda_id, "
                + "a.nm_agenda agenda_nome, "
                + "a.periodo_disponivel agenda_periodo "
                + "FROM compromisso c "
                + "INNER JOIN funcionario f ON f.rowid = c.cd_funcionario "
                + "INNER JOIN agenda a ON a.rowid = c.cd_agenda "
                + "WHERE c.dt_compromisso BETWEEN ? AND ? "
                + "ORDER BY c.dt_compromisso, c.hr_compromisso"
        );

        try (
                Connection connection = getConexao();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query.toString())
        ) {

            preparedStatement.setObject(1, dataInicial);
            preparedStatement.setObject(2, dataFinal);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                List<CompromissoVo> compromissos = new ArrayList<>();

                while (resultSet.next()) {
                    compromissos.add(mapearCompromisso(resultSet));
                }

                return compromissos;
            }

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Erro ao consultar compromissos por período.",
                    e
            );
        }
    }

    private CompromissoVo mapearCompromisso(ResultSet resultSet)
            throws SQLException {

        FuncionarioVo funcionario = new FuncionarioVo();

        funcionario.setRowid(
                resultSet.getString("funcionario_id")
        );

        funcionario.setNome(
                resultSet.getString("funcionario_nome")
        );

        AgendaVo agenda = new AgendaVo();

        agenda.setRowid(
                resultSet.getString("agenda_id")
        );

        agenda.setNome(
                resultSet.getString("agenda_nome")
        );

        CompromissoVo compromisso = new CompromissoVo();

        compromisso.setRowid(
                resultSet.getString("id")
        );

        compromisso.setDataCompromisso(
                resultSet.getObject(
                        "data_compromisso",
                        java.time.LocalDate.class
                )
        );

        compromisso.setHorarioCompromisso(
                resultSet.getObject(
                        "horario_compromisso",
                        java.time.LocalTime.class
                )
        );

        compromisso.setFuncionario(funcionario);
        compromisso.setAgenda(agenda);

        return compromisso;
    }
}
