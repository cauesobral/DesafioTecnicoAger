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

			.card-soc {
				background: #ffffff;
				border: none;
				border-radius: 8px;
				box-shadow: 0 1px 4px rgba(0,0,0,0.08);
				overflow: hidden;
			}

			.card-soc .card-header {
				background: #1b9aa0;
				border-bottom: 3px solid #f2b705;
				color: #ffffff;
			}

			.card-soc .card-header .card-title {
				color: #ffffff;
				margin: 0;
			}

			.card-soc .card-footer {
				background: #ffffff;
				border-top: 1px solid #e1e5e8;
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
		</style>
	</head>
	<body>
		<jsp:include page="/layout/header.jsp"/>

		<div class="container">
			<s:form action="/novoFuncionarios.action">

				<div class="card card-soc mt-5">
					<div class="card-header">
						<div class="row align-items-center">
							<div class="col-sm-5">
								<s:url action="todosFuncionarios" var="todos"/>
								<a href="${todos}" class="btn btn-light">Funcionários</a>
							</div>

							<div class="col-sm">
								<h5 class="card-title">Novo Funcionário</h5>
							</div>
						</div>
					</div>

					<div class="card-body">
						<div class="row align-items-center">
							<label for="id" class="col-sm-1 col-form-label text-center">
								Código:
							</label>

							<div class="col-sm-2">
								<s:textfield cssClass="form-control" id="id" name="funcionarioVo.rowid" readonly="true"/>
							</div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="nome" class="col-sm-1 col-form-label text-center">
								Nome:
							</label>

							<div class="col-sm-5">
								<s:textfield cssClass="form-control" id="nome" name="funcionarioVo.nome"/>
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

		<jsp:include page="/layout/footer.jsp"/>

		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
		<jsp:include page="/layout/footer.jsp"/>
	</body>
</html>