package br.com.soc.sistema.filter;

import java.time.LocalDate;

import br.com.soc.sistema.vo.Periodo;

public class CompromissoFilter {
	private String codigoCompromisso;
	private String codigoFuncionario;
	private String nomeFuncionario;
	private String codigoAgenda;
	private LocalDate data;
	private Periodo periodo;

	public String getCodigoCompromisso() {
		return codigoCompromisso;
	}
	public void setCodigoCompromisso(String codigoCompromisso) {
		this.codigoCompromisso = codigoCompromisso;
	}
	public String getCodigoFuncionario() {
		return codigoFuncionario;
	}
	public void setCodigoFuncionario(String codigoFuncionario) {
		this.codigoFuncionario = codigoFuncionario;
	}
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}
	public String getCodigoAgenda() {
		return codigoAgenda;
	}
	public void setCodigoAgenda(String codigoAgenda) {
		this.codigoAgenda = codigoAgenda;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public Periodo getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
}