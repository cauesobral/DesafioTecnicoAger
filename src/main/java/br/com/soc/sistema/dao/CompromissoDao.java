package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.exception.PersistenceException;
import br.com.soc.sistema.filter.CompromissoFilter;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;
import br.com.soc.sistema.vo.Periodo;

public class CompromissoDao extends Dao {

	public void insertCompromisso(CompromissoVo compromissoVo) {
		StringBuilder query = new StringBuilder(
				"INSERT INTO compromisso (cod_funcionario, cod_agenda, dt_compromisso, hr_compromisso) VALUES (?, ?, ?, ?)");

		try {
			Connection connection = getConexao();

			try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
				int i = 1;
				preparedStatement.setInt(i++, Integer.parseInt(compromissoVo.getFuncionario().getRowid()));
				preparedStatement.setInt(i++, Integer.parseInt(compromissoVo.getAgenda().getRowid()));
				preparedStatement.setDate(i++, Date.valueOf(compromissoVo.getDataCompromisso()));
				preparedStatement.setTime(i++, Time.valueOf(compromissoVo.getHorarioCompromisso()));

				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao inserir compromisso.", e);
		}
	}

	public List<CompromissoVo> findAllCompromissos() {
		StringBuilder query = new StringBuilder(
				"SELECT c.id id, c.dt_compromisso dt_compromisso, c.hr_compromisso hr_compromisso, "
				+ "f.rowid cod_funcionario, f.nm_funcionario nome_funcionario, "
				+ "a.id cod_agenda, a.nm_agenda nome_agenda, a.periodo_disponivel periodo_agenda "
				+ "FROM compromisso c "
				+ "JOIN funcionario f ON f.rowid = c.cod_funcionario "
				+ "JOIN agenda a ON a.id = c.cod_agenda");

		try {
			Connection connection = getConexao();

			try (
					PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
					ResultSet resultSet = preparedStatement.executeQuery()
			) {

				List<CompromissoVo> compromissos = new ArrayList<>();

				while (resultSet.next()) {
					compromissos.add(mapearCompromisso(resultSet));
				}

				return compromissos;
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao consultar compromissos.", e);
		}
	}

	public CompromissoVo findByCodigo(Integer codigo) {
		StringBuilder query = new StringBuilder(
				"SELECT c.id id, c.dt_compromisso dt_compromisso, c.hr_compromisso hr_compromisso, "
				+ "f.rowid cod_funcionario, f.nm_funcionario nome_funcionario, "
				+ "a.id cod_agenda, a.nm_agenda nome_agenda, a.periodo_disponivel periodo_agenda "
				+ "FROM compromisso c "
				+ "JOIN funcionario f ON f.rowid = c.cod_funcionario "
				+ "JOIN agenda a ON a.id = c.cod_agenda "
				+ "WHERE c.id = ?");

		try {
			Connection connection = getConexao();

			try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
				preparedStatement.setInt(1, codigo);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					if (resultSet.next()) {
						return mapearCompromisso(resultSet);
					}

					return null;
				}
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao consultar compromisso por código.", e);
		}
	}

	public List<CompromissoVo> findByPeriodo(java.time.LocalDate dataInicial, java.time.LocalDate dataFinal) {
		StringBuilder query = new StringBuilder(
				"SELECT c.id id, c.dt_compromisso dt_compromisso, c.hr_compromisso hr_compromisso, "
				+ "f.rowid cod_funcionario, f.nm_funcionario nome_funcionario, "
				+ "a.id cod_agenda, a.nm_agenda nome_agenda, a.periodo_disponivel periodo_agenda "
				+ "FROM compromisso c "
				+ "JOIN funcionario f ON f.rowid = c.cod_funcionario "
				+ "JOIN agenda a ON a.id = c.cod_agenda "
				+ "WHERE c.dt_compromisso BETWEEN ? AND ? "
				+ "ORDER BY c.dt_compromisso, c.hr_compromisso");

		try {
			Connection connection = getConexao();

			try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
				preparedStatement.setDate(1, Date.valueOf(dataInicial));
				preparedStatement.setDate(2, Date.valueOf(dataFinal));

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					List<CompromissoVo> compromissos = new ArrayList<>();

					while (resultSet.next()) {
						compromissos.add(mapearCompromisso(resultSet));
					}

					return compromissos;
				}
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao consultar compromissos por período.", e);
		}
	}

	public List<CompromissoVo> findByFiltro(CompromissoFilter filtro) {
		StringBuilder query = new StringBuilder(
				"SELECT c.id id, c.dt_compromisso dt_compromisso, c.hr_compromisso hr_compromisso, "
				+ "f.rowid cod_funcionario, f.nm_funcionario nome_funcionario, "
				+ "a.id cod_agenda, a.nm_agenda nome_agenda, a.periodo_disponivel periodo_agenda "
				+ "FROM compromisso c "
				+ "JOIN funcionario f ON f.rowid = c.cod_funcionario "
				+ "JOIN agenda a ON a.id = c.cod_agenda "
				+ "WHERE 1=1");

		List<Object> parametros = new ArrayList<>();

		if (filtro.getCodigoCompromisso() != null && !filtro.getCodigoCompromisso().trim().isEmpty()) {
			query.append(" AND c.id = ?");
			parametros.add(Integer.parseInt(filtro.getCodigoCompromisso().trim()));
		}

		if (filtro.getCodigoFuncionario() != null && !filtro.getCodigoFuncionario().trim().isEmpty()) {
			query.append(" AND c.cod_funcionario = ?");
			parametros.add(Integer.parseInt(filtro.getCodigoFuncionario().trim()));
		}

		if (filtro.getNomeFuncionario() != null && !filtro.getNomeFuncionario().trim().isEmpty()) {
			query.append(" AND lower(f.nm_funcionario) like lower(?)");
			parametros.add("%" + filtro.getNomeFuncionario().trim() + "%");
		}

		if (filtro.getCodigoAgenda() != null && !filtro.getCodigoAgenda().trim().isEmpty()) {
			query.append(" AND c.cod_agenda = ?");
			parametros.add(Integer.parseInt(filtro.getCodigoAgenda().trim()));
		}

		if (filtro.getData() != null) {
			query.append(" AND c.dt_compromisso = ?");
			parametros.add(Date.valueOf(filtro.getData()));
		}

		if (filtro.getPeriodo() != null) {
			query.append(" AND c.hr_compromisso BETWEEN ? AND ?");
			parametros.add(Time.valueOf(inicioDoPeriodo(filtro.getPeriodo())));
			parametros.add(Time.valueOf(fimDoPeriodo(filtro.getPeriodo())));
		}

		query.append(" ORDER BY c.dt_compromisso, c.hr_compromisso");

		try {
			Connection connection = getConexao();

			try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
				for (int i = 0; i < parametros.size(); i++) {
					preparedStatement.setObject(i + 1, parametros.get(i));
				}

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					List<CompromissoVo> compromissos = new ArrayList<>();

					while (resultSet.next()) {
						compromissos.add(mapearCompromisso(resultSet));
					}

					return compromissos;
				}
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao filtrar compromissos.", e);
		}
	}

	public void updateCompromisso(CompromissoVo compromissoVo) {
		StringBuilder query = new StringBuilder(
				"UPDATE compromisso SET cod_funcionario = ?, cod_agenda = ?, dt_compromisso = ?, hr_compromisso = ? "
				+ "WHERE id = ?");

		try {
			Connection connection = getConexao();

			try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
				int i = 1;
				preparedStatement.setInt(i++, Integer.parseInt(compromissoVo.getFuncionario().getRowid()));
				preparedStatement.setInt(i++, Integer.parseInt(compromissoVo.getAgenda().getRowid()));
				preparedStatement.setDate(i++, Date.valueOf(compromissoVo.getDataCompromisso()));
				preparedStatement.setTime(i++, Time.valueOf(compromissoVo.getHorarioCompromisso()));
				preparedStatement.setInt(i++, Integer.parseInt(compromissoVo.getRowid()));

				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao alterar compromisso.", e);
		}
	}

	public void deleteCompromisso(Integer codigo) {
		StringBuilder query = new StringBuilder("DELETE FROM compromisso WHERE id = ?");

		try {
			Connection connection = getConexao();

			try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
				preparedStatement.setInt(1, codigo);
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao excluir compromisso.", e);
		}
	}

	public void deleteByFuncionario(Integer codigoFuncionario) {
		StringBuilder query = new StringBuilder("DELETE FROM compromisso WHERE cod_funcionario = ?");

		try {
			Connection connection = getConexao();

			try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
				preparedStatement.setInt(1, codigoFuncionario);
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao excluir compromissos do funcionário.", e);
		}
	}

	public boolean existsByAgenda(Integer codigoAgenda) {
		StringBuilder query = new StringBuilder("SELECT COUNT(*) total FROM compromisso WHERE cod_agenda = ?");

		try {
			Connection connection = getConexao();

			try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
				preparedStatement.setInt(1, codigoAgenda);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					resultSet.next();
					return resultSet.getInt("total") > 0;
				}
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao verificar compromissos da agenda.", e);
		}
	}

	private CompromissoVo mapearCompromisso(ResultSet resultSet) throws SQLException {
		FuncionarioVo funcionario = new FuncionarioVo(
				resultSet.getString("cod_funcionario"),
				resultSet.getString("nome_funcionario"));

		AgendaVo agenda = new AgendaVo(
				resultSet.getString("cod_agenda"),
				resultSet.getString("nome_agenda"),
				Periodo.valueOf(resultSet.getString("periodo_agenda")));

		CompromissoVo vo = new CompromissoVo();
		vo.setRowid(resultSet.getString("id"));
		vo.setDataCompromisso(resultSet.getDate("dt_compromisso").toLocalDate());
		vo.setHorarioCompromisso(resultSet.getTime("hr_compromisso").toLocalTime());
		vo.setFuncionario(funcionario);
		vo.setAgenda(agenda);

		return vo;
	}

	private java.time.LocalTime inicioDoPeriodo(Periodo periodo) {
		switch (periodo) {
			case MANHA: return java.time.LocalTime.of(6, 0);
			case TARDE: return java.time.LocalTime.of(12, 0);
			default: return java.time.LocalTime.MIN;
		}
	}

	private java.time.LocalTime fimDoPeriodo(Periodo periodo) {
		switch (periodo) {
			case MANHA: return java.time.LocalTime.of(11, 59);
			case TARDE: return java.time.LocalTime.of(18, 59);
			default: return java.time.LocalTime.MAX;
		}
	}
}