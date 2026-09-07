package br.com.soc.sistema.vo;

public class AgendaVo {
	private String rowid;
	private String nome;
	private Periodo periodo;
	
	public AgendaVo() {};
	
	public AgendaVo(String rowid, String nome, Periodo periodo) {
		this.rowid = rowid;
		this.nome = nome;
		this.periodo = periodo;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AgendaVo that = (AgendaVo) o;
		return rowid != null ? rowid.equals(that.rowid) : that.rowid == null;
	}

	@Override
	public int hashCode() {
		return rowid != null ? rowid.hashCode() : 0;
	}
	
	@Override
	public String toString() {
		return "AgendaVo [rowid=" + rowid + ", nome=" + nome + ", periodo=" + periodo+ "]";
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
	
	public Periodo getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
}
