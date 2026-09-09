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
		<s:url value="/estilos/global.css" var="cssGlobal"/>
		<link rel='stylesheet' href='${cssGlobal}'>
		<s:url value="/estilos/relatorio.css" var="cssRelatorio"/>
		<link rel='stylesheet' href='${cssRelatorio}'>
	</head>
	<body>
		<jsp:include page="/layout/header.jsp"/>

		<div class="container">
			<div class="row mt-5 mb-3">
				<div class="col-sm p-0">
					<s:url action="exportarRelatorios" var="urlExportar"/>

					<s:form action="/gerarRelatorios.action">
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
					<table class="table table-striped align-middle tabela-listagem mb-0">
						<thead>
							<tr>
								<th>Cód. Compromisso</th>
								<th>Cód. Funcionário</th>
								<th><s:text name="label.funcionario"/></th>
								<th>Cód. Agenda</th>
								<th><s:text name="label.agenda"/></th>
								<th><s:text name="label.data"/></th>
								<th><s:text name="label.horario"/></th>
							</tr>
						</thead>

						<tbody>
							<s:iterator value="compromissos" >
								<tr>
									<td><s:property value="rowid"/></td>
									<td><s:property value="funcionario.rowid"/></td>
									<td><s:property value="funcionario.nome"/></td>
									<td><s:property value="agenda.rowid"/></td>
									<td><s:property value="agenda.nome"/></td>
									<td>${dataFormatada}</td>
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
		<jsp:include page="/layout/footer.jsp"/>
	</body>
</html>