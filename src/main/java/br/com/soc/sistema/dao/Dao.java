package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.soc.sistema.exception.TechnicalException;

public abstract class Dao implements AutoCloseable {

	private static boolean primeiraInicializacao = true;
	private Connection con = null;

	public Dao() {
		conectar();
	}

	private void conectar() {
		StringBuilder urlBuilder = new StringBuilder("jdbc:h2:mem:avaliacao;")
										.append("DB_CLOSE_DELAY=-1;")
										.append("DATABASE_TO_UPPER=false;");

		if (primeiraInicializacao) {
			urlBuilder.append("INIT=runscript from 'classpath:CRIA_TABELAS_E_INSERE_REGISTROS_INICIAIS.sql';");
			primeiraInicializacao = false;
		}

		try {
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection(urlBuilder.toString());
		} catch (SQLException ex) {
			throw new TechnicalException("Ocorreu um problema na tentativa de conexao", ex);
		} catch (ClassNotFoundException e) {
			throw new TechnicalException("Driver do H2 nao encontrado", e);
		}
	}

	private void fechar() throws SQLException {
		if (con != null && !con.isClosed()) {
			con.close();
		}
	}

	@Override
	public void close() throws Exception {
		fechar();
	}

	/**
	 * Retorna a conexao compartilhada desta instancia de Dao, reconectando
	 * apenas se necessario. Quem chama este metodo NAO deve fecha-la
	 * diretamente (por exemplo, dentro de um try-with-resources) -- o
	 * ciclo de vida da conexao pertence a esta classe.
	 */
	protected Connection getConexao() throws SQLException {
		if (con == null || con.isClosed()) {
			conectar();
		}
		return con;
	}
}