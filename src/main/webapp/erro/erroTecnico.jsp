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
	</head>
	<body>
		<jsp:include page="/layout/header.jsp"/>

		<div class="container">
			<div class="row justify-content-center mt-5">
				<div class="col-sm-8">
					<div class="card card-erro card-erro-tecnico">
						<div class="card-header">
							<h5 class="mb-0"><s:text name="label.erroTecnico.titulo"/></h5>
						</div>

						<div class="card-body">
							<p class="mb-0"><s:text name="label.erroTecnico.mensagem"/></p>
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
		<jsp:include page="/layout/footer.jsp"/>
	</body>
</html>