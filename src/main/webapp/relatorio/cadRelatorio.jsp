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

			.tabela-relatorio {
				background: #ffffff;
				border-radius: 8px;
				overflow: hidden;
				box-shadow: 0 1px 4px rgba(0,0,0,0.08);
			}

			.tabela-relatorio thead {
				background: #1b9aa0;
			}

			.tabela-relatorio thead th {
				color: #ffffff;
				border-bottom: 3px solid #f2b705;
			}
		</style>
	</head>
	<body>
		<jsp:include page="/layout/header.jsp"/>

		<div class="container">
			<div class="row mt-5 mb-3">
				<div class="col-sm p-0">
					<s:url action="gerarRelatorios" var="urlGerar"/>
					<s:url action="exportarRelatorios" var="urlExportar"/>

					<s:form action="%{urlGerar}">
						<div class="filtro-bar row g-3 align-items-end">
							<div class="col-sm-3">
								<label class="form-label"><s:text name="label.data.inicial"/></label>
								<input type="date" class="form-control" name="filtro.dataInicial" value="<s:property value="filtro.dataInicial"/>">
							</div>

							<div class="col-sm-3">
								<label class="form-label"><s:text name="label.data.final"/></label>
								<input type="date" class="form-control" name="filtro.dataFinal" value="<s:property value="filtro.dataFinal"/>">
							</div>

							<div class="col-sm-auto">
								<button class="btn btn-teal" type="submit"><s:text name="label.gerar"/></button>
							</div>

							<div class="col-sm-auto">
								<button class="btn btn-gold" type="submit" formaction="${urlExportar}"><s:text name="label.exportar.excel"/></button>
							</div>
						</div>
					</s:form>
				</div>
			</div>

			<div class="row">
				<s:if test="pesquisado">
					<table class="table table-striped align-middle tabela-relatorio mb-0">
						<thead>
							<tr>
								<th><s:text name="label.id"/></th>
								<th><s:text name="label.funcionario"/></th>
								<th><s:text name="label.agenda"/></th>
								<th><s:text name="label.data"/></th>
								<th><s:text name="label.horario"/></th>
							</tr>
						</thead>

						<tbody>
							<s:iterator value="compromissos" >
								<tr>
									<td>${rowid}</td>
									<td>${funcionario.nome}</td>
									<td>${agenda.nome}</td>
									<td>${dataCompromisso}</td>
									<td>${horarioCompromisso}</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</s:if>
				<s:else>
					<div class="text-center text-muted py-4">
						<s:text name="label.relatorio.semPesquisa"/>
					</div>
				</s:else>
			</div>

			<div class="row mb-5">

			</div>
		</div>

		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	</body>
</html>