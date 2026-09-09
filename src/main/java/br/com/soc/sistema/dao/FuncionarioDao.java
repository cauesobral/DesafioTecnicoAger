package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.exception.PersistenceException;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioDao extends Dao {

	public void insertFuncionario(FuncionarioVo funcionarioVo) {
		StringBuilder query = new StringBuilder("INSERT INTO funcionario (nm_funcionario) values (?)");

		try {
			Connection con = getConexao();

			try (PreparedStatement ps = con.prepareStatement(query.toString())) {
				int i = 1;
				ps.setString(i++, funcionarioVo.getNome());
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao inserir funcionário.", e);
		}
	}

	public void deleteFuncionario(Integer codigo) {
		StringBuilder query = new StringBuilder("DELETE FROM funcionario WHERE rowid = ?");

		try {
			Connection connection = getConexao();

			try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
				preparedStatement.setInt(1, codigo);
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao excluir funcionário.", e);
		}
	}

	public void updateFuncionario(FuncionarioVo funcionario) {
		StringBuilder queryUpdate = new StringBuilder("UPDATE funcionario SET nm_funcionario = ? WHERE rowid = ?");

		try {
			Connection connection = getConexao();

			try (PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate.toString())) {
				int i = 1;
				preparedStatement.setString(i++, funcionario.getNome());
				preparedStatement.setInt(i++, Integer.parseInt(funcionario.getRowid()));
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao alterar funcionário.", e);
		}
	}

	public List<FuncionarioVo> findAllFuncionarios() {
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario");

		try {
			Connection con = getConexao();

			try (
					PreparedStatement ps = con.prepareStatement(query.toString());
					ResultSet rs = ps.executeQuery()
			) {

				List<FuncionarioVo> funcionarios = new ArrayList<>();

				while (rs.next()) {
					FuncionarioVo vo = new FuncionarioVo();
					vo.setRowid(rs.getString("id"));
					vo.setNome(rs.getString("nome"));

					funcionarios.add(vo);
				}

				return funcionarios;
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao consultar funcionários.", e);
		}
	}

	public List<FuncionarioVo> findAllByNome(String nome) {
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario ")
				.append("WHERE lower(nm_funcionario) like lower(?)");

		try {
			Connection con = getConexao();

			try (PreparedStatement ps = con.prepareStatement(query.toString())) {
				ps.setString(1, "%" + nome + "%");

				try (ResultSet rs = ps.executeQuery()) {

					List<FuncionarioVo> funcionarios = new ArrayList<>();

					while (rs.next()) {
						FuncionarioVo vo = new FuncionarioVo();
						vo.setRowid(rs.getString("id"));
						vo.setNome(rs.getString("nome"));

						funcionarios.add(vo);
					}

					return funcionarios;
				}
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao consultar funcionários por nome.", e);
		}
	}

	public FuncionarioVo findByCodigo(Integer codigo) {
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario ")
				.append("WHERE rowid = ?");

		try {
			Connection con = getConexao();

			try (PreparedStatement ps = con.prepareStatement(query.toString())) {
				ps.setInt(1, codigo);

				try (ResultSet rs = ps.executeQuery()) {

					FuncionarioVo vo = null;

					if (rs.next()) {
						vo = new FuncionarioVo();
						vo.setRowid(rs.getString("id"));
						vo.setNome(rs.getString("nome"));
					}

					return vo;
				}
			}

		} catch (SQLException e) {
			throw new PersistenceException("Erro ao consultar funcionário por código.", e);
		}
	}
}