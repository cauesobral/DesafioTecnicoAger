package br.com.soc.sistema.vo;

import java.time.LocalDate;
import java.time.LocalTime;


public class CompromissoVo {
	private String rowid;
	private LocalTime horarioCompromisso;
	private LocalDate dataCompromisso;
	private FuncionarioVo funcionario;
	private AgendaVo agenda;
	
	public CompromissoVo () {}
	
	public CompromissoVo (String rowid, LocalTime horarioCompromisso, LocalDate dataCompromisso, 
			FuncionarioVo funcionario, AgendaVo agenda) {
		this.rowid = rowid;
		this.horarioCompromisso = horarioCompromisso;
		this.dataCompromisso = dataCompromisso;
		this.funcionario = funcionario;
		this.agenda = agenda;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompromissoVo that = (CompromissoVo) o;
        return rowid != null ? rowid.equals(that.rowid) : that.rowid == null;
    }

    @Override
    public int hashCode() {
        return rowid != null ? rowid.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "CompromissoVo {" + "rowid='" + rowid + '\'' + ", horarioCompromisso=" + horarioCompromisso + ", dataCompromisso=" + dataCompromisso +
                ", funcionario=" + (funcionario != null ? funcionario.toString() : "null") + ", agenda=" + (agenda != null ? agenda.toString() : "null") +
                '}';
    }
	
    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public LocalTime getHorarioCompromisso() {
        return horarioCompromisso;
    }

    public void setHorarioCompromisso(LocalTime horarioCompromisso) {
        this.horarioCompromisso = horarioCompromisso;
    }

    public LocalDate getDataCompromisso() {
        return dataCompromisso;
    }

    public void setDataCompromisso(LocalDate dataCompromisso) {
        this.dataCompromisso = dataCompromisso;
    }

    public FuncionarioVo getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioVo funcionario) {
        this.funcionario = funcionario;
    }

    public AgendaVo getAgenda() {
        return agenda;
    }

    public void setAgenda(AgendaVo agenda) {
        this.agenda = agenda;
    }
}
