<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<s:url value="/imagens/soc_aba_logo.png" var="faviconUrl"/>
		<link rel="icon" href="${faviconUrl}" type="image/png">
		<title>Desafio Técnico</title>
		<link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>

		<style>
			body {
				background: #f4f6f7;
			}
			
			.filtro-bar {
				background: #ffffff;
				border-radius: 8px;
				box-shadow: 0 1px 4px rgba(0,0,0,0.08);
				padding: 1rem;
			}
			
			.filtro-card {
				background: #ffffff;
				border-radius: 8px;
				box-shadow: 0 1px 4px rgba(0,0,0,0.08);
				padding: 1.25rem 1.5rem;
			}

			.filtro-card .form-label {
				font-size: 0.8rem;
				font-weight: 700;
				color: #1b9aa0;
				margin-bottom: 0.3rem;
			}

			.filtro-card .form-control,
			.filtro-card .form-select {
				border: 1px solid #e1e5e8;
			}

			.filtro-card .form-control:focus,
			.filtro-card .form-select:focus {
				border-color: #1b9aa0;
				box-shadow: 0 0 0 0.2rem rgba(27, 154, 160, 0.15);
			}

			.btn-teal {
				background-color: #1b9aa0;
				border-color: #1b9aa0;
				color: #fff;
			}

			.btn-teal:hover {
				background-color: #157a80;
				border-color: #157a80;
				color: #fff;
			}

			.btn-gold {
				background-color: #f2b705;
				border-color: #f2b705;
				color: #3a3a3a;
			}

			.btn-gold:hover {
				background-color: #d9a400;
				border-color: #d9a400;
				color: #3a3a3a;
			}

			.tabela-compromissos {
				background: #ffffff;
				border-radius: 8px;
				overflow: hidden;
				box-shadow: 0 1px 4px rgba(0,0,0,0.08);
			}

			.tabela-compromissos thead {
				background: #1b9aa0;
			}

			.tabela-compromissos thead th {
				color: #ffffff;
				border-bottom: 3px solid #f2b705;
			}

			.tabela-compromissos tbody tr:hover {
				background-color: #eef7f7;
			}

			.tabela-compromissos tfoot {
				background: #ffffff;
			}
		</style>
	</head>
	<body>
		<jsp:include page="/layout/header.jsp"/>

		<div class="container">
			<div class="row mt-5 mb-3">
				<div class="col-sm">
					<div class="filtro-card">
						<s:form action="/filtrarCompromissos.action">
							<div class="row g-3">
								<div class="col-sm-3">
									<label class="form-label">Cód. Compromisso</label>
									<input type="text" class="form-control" name="filtro.codigoCompromisso">
								</div>

								<div class="col-sm-3">
									<label class="form-label">Cód. Funcionário</label>
									<input type="text" class="form-control" name="filtro.codigoFuncionario">
								</div>

								<div class="col-sm-6">
									<label class="form-label">Nome Funcionário</label>
									<input type="text" class="form-control" name="filtro.nomeFuncionario">
								</div>
							</div>

							<div class="row g-3 mt-1 align-items-end">
								<div class="col-sm-2">
									<label class="form-label">Período</label>
									<s:select
										cssClass="form-select"
										name="filtro.periodo"
										list="@br.com.soc.sistema.vo.Periodo@values()"
										headerKey=""
										headerValue="Todos"
										listKey="name()"
										listValueKey="name()"
									/>
								</div>

								<div class="col-sm-3">
									<label class="form-label">Agenda</label>
									<s:select
										cssClass="form-select"
										name="filtro.codigoAgenda"
										list="listaAgendas"
										headerKey=""
										headerValue="Todas"
										listKey="rowid"
										listValueKey="nome"
									/>
								</div>

								<div class="col-sm-2">
									<label class="form-label">Data</label>
									<input type="date" class="form-control" name="filtro.data">
								</div>

								<div class="col-sm-5 text-end">
									<button class="btn btn-teal px-4" type="submit">Filtrar</button>
								</div>
							</div>
						</s:form>
					</div>
				</div>
			</div>

			<div class="row mt-5">

				<table class="table table-striped align-middle tabela-compromissos mb-0">
					<thead>
						<tr>
							<th><s:text name="label.id"/></th>
							<th><s:text name="label.funcionario"/></th>
							<th><s:text name="label.agenda"/></th>
							<th><s:text name="label.data"/></th>
							<th><s:text name="label.horario"/></th>
							<th class="text-end mt-5"><s:text name="label.acao"/></th>
						</tr>
					</thead>

					<tbody>
						<s:iterator value="compromissos" >
							<tr>
								<td>${rowid}</td>
								<td>${funcionario.nome}</td>
								<td>${agenda.nome}</td>
								<td>${dataFormatada}</td>
								<td>${horarioCompromisso}</td>
								<td class="text-end">
									<s:url action="editarCompromissos" var="editar">
										<s:param name="compromissoVo.rowid" value="rowid"></s:param>
									</s:url>

									<a href="${editar}" class="btn btn-gold">
										<s:text name="label.editar"/>
									</a>

									<s:url action="excluirCompromissos" var="excluir">
									    <s:param name="compromissoVo.rowid" value="rowid"/>
									</s:url>

									<a href="#"
									   class="btn btn-danger"
									   data-bs-toggle="modal"
									   data-bs-target="#confirmarExclusao"
									   data-excluir-url="${excluir}">
									    <s:text name="label.excluir"/>
									</a>
								</td>
							</tr>
						</s:iterator>
					</tbody>

					<tfoot>
						<tr>
							<td colspan="6" class="p-3">
								<s:url action="editarCompromissos" var="novo"/>

								<a href="${novo}" class="btn btn-teal">
									<s:text name="label.novo"/>
								</a>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>

			<div class="row mb-5">

			</div>
		</div>

		<div  class="modal fade" id="confirmarExclusao"
			data-bs-backdrop="static" data-bs-keyboard="false"
			tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title"><s:text name="label.modal.titulo"/></h5>

		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>

		      <div class="modal-body">
		      	<span><s:text name="label.modal.corpo"/></span>
		      </div>

		      <div class="modal-footer">
	        	<a class="btn btn-secondary" data-bs-dismiss="modal" aria-label="Close">
					<s:text name="label.nao"/>
				</a>

				<a id="excluir" class="btn btn-teal" style="width: 75px;">
				    <s:text name="label.sim"/>
				</a>
		      </div>
		    </div>
		  </div>
		</div>

		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>

		<script>
		    document.addEventListener('DOMContentLoaded', function() {
		        const botoesExcluir = document.querySelectorAll('[data-excluir-url]');
		        const botaoConfirmar = document.getElementById('excluir');

		        botoesExcluir.forEach(function(botao) {
		            botao.addEventListener('click', function() {
		                botaoConfirmar.href = this.dataset.excluirUrl;
		            });
		        });
		    });
		</script>
		<jsp:include page="/layout/footer.jsp"/>
	</body>
</html>