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
		<s:url value="/estilos/compromisso.css" var="cssCompromisso"/>
		<link rel='stylesheet' href='${cssCompromisso}'>
	</head>
	<body>
		<jsp:include page="/layout/header.jsp"/>

		<div class="container">
			<s:form action="/novoCompromissos.action">
				<s:hidden name="compromissoVo.rowid"/>

				<div class="card card-soc mt-5">
					<div class="card-header">
						<div class="row align-items-center">
							<div class="col-sm-5">
								<s:url action="todosCompromissos" var="todos"/>
								<a href="${todos}" class="btn btn-light" >Compromissos</a>
							</div>

							<div class="col-sm">
								<h5 class="card-title">
									<s:if test="compromissoVo.rowid != null && compromissoVo.rowid != ''">
										Editar Compromisso
									</s:if>
									<s:else>
										Novo Compromisso
									</s:else>
								</h5>
							</div>
						</div>
					</div>

					<div class="card-body">
						<div class="row align-items-center">
							<label for="funcionario" class="col-sm-2 col-form-label text-center">
								Funcionário:
							</label>

							<div class="col-sm-5">
								<s:select
									cssClass="form-select"
									id="funcionario"
									name="compromissoVo.funcionario.rowid"
									list="listaFuncionarios"
									headerKey=""
									headerValue="Escolha..."
									listKey="rowid"
									listValueKey="nome"
								/>
							</div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="agenda" class="col-sm-2 col-form-label text-center">
								Agenda:
							</label>

							<div class="col-sm-5">
								<s:select
									cssClass="form-select"
									id="agenda"
									name="compromissoVo.agenda.rowid"
									list="listaAgendas"
									headerKey=""
									headerValue="Escolha..."
									listKey="rowid"
									listValueKey="nome"
								/>
							</div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="data" class="col-sm-2 col-form-label text-center">
								Data:
							</label>

							<div class="col-sm-3">
								<input type="date" class="form-control" id="data" name="compromissoVo.dataCompromisso" value="<s:property value="dataCompromisso"/>">
							</div>

							<label for="horario" class="col-sm-2 col-form-label text-center">
								Horário:
							</label>

							<div class="col-sm-3">
								<input type="time" class="form-control" id="horario" name="compromissoVo.horarioCompromisso" value="<s:property value="horarioCompromisso"/>">
							</div>
						</div>
					</div>

					<div class="card-footer">
						<div class="form-row">
							<button class="btn btn-teal col-sm-4 offset-sm-1">Salvar</button>
							<button type="reset" class="btn btn-secondary col-sm-4 offset-sm-2">Limpar Formulario</button>
						</div>
					</div>
				</div>
			</s:form>
		</div>

		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
		<jsp:include page="/layout/footer.jsp"/>
	</body>
</html>