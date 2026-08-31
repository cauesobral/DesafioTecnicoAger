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
import br.com.soc.sistema.vo.Periodo;

public class AgendaDao extends Dao {

	public void insertAgenda(AgendaVo agendaVo) {
		StringBuilder query = new StringBuilder(
				"INSERT INTO agenda (nm_agenda, periodo_disponivel) VALUES (?, ?)");

		try (
				Connection connection = getConexao();
				PreparedStatement preparedStatement = connection.prepareStatement(query.toString())
		) {

			int i = 1;
			preparedStatement.setString(i++, agendaVo.getNome());
			preparedStatement.setString(i++, agendaVo.getPeriodo().name());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao inserir agenda.", e);
		}
	}

	public List<AgendaVo> findAllAgendas() {
		StringBuilder query = new StringBuilder(
				"SELECT rowid id, nm_agenda nome, periodo_disponivel periodo "
				+ "FROM agenda");

		try (
				Connection connection = getConexao();
				PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
				ResultSet resultSet = preparedStatement.executeQuery()
		) {

			List<AgendaVo> agendas = new ArrayList<>();

			while (resultSet.next()) {
				AgendaVo vo = new AgendaVo();

				vo.setRowid(resultSet.getString("id"));
				vo.setNome(resultSet.getString("nome"));
				vo.setPeriodo(Periodo.valueOf(resultSet.getString("periodo")));

				agendas.add(vo);
			}

			return agendas;

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao consultar agendas.", e);
		}
	}

	public AgendaVo findByCodigo(Integer codigo) {
		StringBuilder query = new StringBuilder(
				"SELECT rowid id, nm_agenda nome, periodo_disponivel periodo "
				+ "FROM agenda WHERE rowid = ?");

		try (
				Connection connection = getConexao();
				PreparedStatement preparedStatement = connection.prepareStatement(query.toString())
		) {

			preparedStatement.setInt(1, codigo);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				AgendaVo vo = null;

				if (resultSet.next()) {
					vo = new AgendaVo();

					vo.setRowid(resultSet.getString("id"));
					vo.setNome(resultSet.getString("nome"));
					vo.setPeriodo(Periodo.valueOf(resultSet.getString("periodo")));
				}

				return vo;
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao consultar agenda por código.", e);
		}
	}

	public List<AgendaVo> findAllByNome(String nome) {
		StringBuilder query = new StringBuilder(
				"SELECT rowid id, nm_agenda nome, periodo_disponivel periodo "
				+ "FROM agenda "
				+ "WHERE lower(nm_agenda) like lower(?)");

		try (
				Connection connection = getConexao();
				PreparedStatement preparedStatement = connection.prepareStatement(query.toString())
		) {

			preparedStatement.setString(1, "%" + nome + "%");

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				List<AgendaVo> agendas = new ArrayList<>();

				while (resultSet.next()) {
					AgendaVo vo = new AgendaVo();

					vo.setRowid(resultSet.getString("id"));
					vo.setNome(resultSet.getString("nome"));
					vo.setPeriodo(Periodo.valueOf(resultSet.getString("periodo")));

					agendas.add(vo);
				}

				return agendas;
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao consultar agendas por nome.", e);
		}
	}

	public void updateAgenda(AgendaVo agendaVo) {
		StringBuilder query = new StringBuilder(
				"UPDATE agenda SET nm_agenda = ?, periodo_disponivel = ? "
				+ "WHERE rowid = ?");

		try (
				Connection connection = getConexao();
				PreparedStatement preparedStatement = connection.prepareStatement(query.toString())
		) {

			int i = 1;
			preparedStatement.setString(i++, agendaVo.getNome());
			preparedStatement.setString(i++, agendaVo.getPeriodo().name());
			preparedStatement.setInt(i++, Integer.parseInt(agendaVo.getRowid()));

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao alterar agenda.", e);
		}
	}

	public void deleteAgenda(Integer codigo) {
		StringBuilder query = new StringBuilder(
				"DELETE FROM agenda WHERE rowid = ?");

		try (
				Connection connection = getConexao();
				PreparedStatement preparedStatement = connection.prepareStatement(query.toString())
		) {

			preparedStatement.setInt(1, codigo);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao excluir agenda.", e);
		}
	}
}