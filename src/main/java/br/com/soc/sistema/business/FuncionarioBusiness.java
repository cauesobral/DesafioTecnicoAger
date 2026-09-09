package br.com.soc.sistema.business;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.FuncionarioDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.FuncionarioFilter;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioBusiness {

	private static final String FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO = "Foi informado um caracter no lugar de um numero";
	private FuncionarioDao dao;
	private CompromissoBusiness compromissoBusiness;

	public FuncionarioBusiness() {
		this.dao = new FuncionarioDao();
	}

	FuncionarioBusiness(FuncionarioDao dao, CompromissoBusiness compromissoBusiness) {
		this.dao = dao;
		this.compromissoBusiness = compromissoBusiness;
	}

	private CompromissoBusiness getCompromissoBusiness() {
		if (compromissoBusiness == null) {
			compromissoBusiness = new CompromissoBusiness();
		}
		return compromissoBusiness;
	}

	public List<FuncionarioVo> trazerTodosOsFuncionarios(){
		return dao.findAllFuncionarios();
	}

	public void salvarFuncionario(FuncionarioVo funcionarioVo) {
		if (funcionarioVo.getNome() == null || funcionarioVo.getNome().isEmpty()) {
			throw new BusinessException("Nome não pode ser em branco.");
		}

		dao.insertFuncionario(funcionarioVo);
	}

	public void alterarFuncionario(FuncionarioVo funcionario) {
		if (funcionario.getNome() == null || funcionario.getNome().isEmpty()) {
			throw new BusinessException("O nome digitado não pode ser vazio.");
		}
		if (funcionario.getRowid() == null || funcionario.getRowid().isEmpty()) {
			throw new BusinessException("Código do funcionário deve ser informado.");
		}

		dao.updateFuncionario(funcionario);
	}

	public void excluirFuncionario(String codigo) {
		try {
			Integer codigoNumero = Integer.parseInt(codigo);
			getCompromissoBusiness().excluirCompromissosDoFuncionario(codigoNumero);
			dao.deleteFuncionario(codigoNumero);
		} catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
		}
	}

	public List<FuncionarioVo> filtrarFuncionarios(FuncionarioFilter filter){
		List<FuncionarioVo> funcionarios = new ArrayList<>();

		switch (filter.getOpcoesCombo()) {
			case ID:
				try {
					Integer codigo = Integer.parseInt(filter.getValorBusca());
					funcionarios.add(dao.findByCodigo(codigo));
				} catch (NumberFormatException e) {
					throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
				}
			break;

			case NOME:
				funcionarios.addAll(dao.findAllByNome(filter.getValorBusca()));
			break;
		}

		return funcionarios;
	}

	public FuncionarioVo buscarFuncionarioPor(String codigo) {
		try {
			Integer cod = Integer.parseInt(codigo);
			return dao.findByCodigo(cod);
		} catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
		}
	}
}