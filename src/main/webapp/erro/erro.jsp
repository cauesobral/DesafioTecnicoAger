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

			.card-erro {
				background: #ffffff;
				border: none;
				border-radius: 8px;
				box-shadow: 0 1px 4px rgba(0,0,0,0.08);
				overflow: hidden;
			}

			.card-erro .card-header {
				background: #f2b705;
				color: #3a3a3a;
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
			<div class="row justify-content-center mt-5">
				<div class="col-sm-8">
					<div class="card card-erro">
						<div class="card-header">
							<h5 class="mb-0"><s:text name="label.erro.titulo"/></h5>
						</div>

						<div class="card-body">
							<p class="mb-0"><s:property value="exception.message"/></p>
						</div>

						<div class="card-footer text-end">
							<a href="javascript:history.back()" class="btn btn-secondary">
								<s:text name="label.erro.voltar"/>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	</body>
</html>