package br.com.soc.sistema.vo;

public class FuncionarioVo {
	private String rowid;
	private String nome;
	
	public FuncionarioVo() {}
		
	public FuncionarioVo(String rowid, String nome) {
		this.rowid = rowid;
		this.nome = nome;
	}

	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "FuncionarioVo [rowid=" + rowid + ", nome=" + nome + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FuncionarioVo that = (FuncionarioVo) o;
		return rowid != null && rowid.equals(that.rowid);
	}
}