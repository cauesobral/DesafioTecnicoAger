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
				padding: 0.4rem;
			}

			.filtro-bar .input-group-text {
				background: #ffffff;
				border: none;
				color: #1b9aa0;
				font-weight: 700;
			}

			.filtro-bar .form-control {
				border: 1px solid #e1e5e8;
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

			.tabela-agendas {
				background: #ffffff;
				border-radius: 8px;
				overflow: hidden;
				box-shadow: 0 1px 4px rgba(0,0,0,0.08);
			}

			.tabela-agendas thead {
				background: #1b9aa0;
			}

			.tabela-agendas thead th {
				color: #ffffff;
				border-bottom: 3px solid #f2b705;
			}

			.tabela-agendas tbody tr:hover {
				background-color: #eef7f7;
			}

			.tabela-agendas tfoot {
				background: #ffffff;
			}
		</style>
	</head>
	<body>
		<jsp:include page="/layout/header.jsp"/>

		<div class="container">
			<div class="row mt-5 mb-3">
				<div class="col-sm p-0">
					<s:form action="/filtrarAgendas.action">
						<div class="input-group filtro-bar">
							<span class="input-group-text">
								<s:text name="label.nome"/>
							</span>	
								<s:textfield cssClass="form-control" id="nome" name="agendaVo.nome"/>
								<button class="btn btn-teal" type="submit"><s:text name="label.pesquisar"/></button>
						</div>
					</s:form>			
				</div>				
			</div>

			<div class="row">
				<table class="table table-striped align-middle tabela-agendas mb-0">
					<thead>
						<tr>
							<th><s:text name="label.id"/></th>
							<th><s:text name="label.nome"/></th>
							<th><s:text name="label.periodo"/></th>
							<th class="text-end mt-5"><s:text name="label.acao"/></th>
						</tr>
					</thead>
					
					<tbody>
						<s:iterator value="agendas" >
							<tr>
								<td>${rowid}</td>
								<td>${nome}</td>
								<td><s:text name="label.periodo.%{periodo}"/></td>
								<td class="text-end">
									<s:url action="editarAgendas" var="editar">
										<s:param name="agendaVo.rowid" value="rowid"></s:param>
									</s:url>

									<a href="${editar}" class="btn btn-gold">
										<s:text name="label.editar"/>
									</a>
									
									<s:url action="excluirAgendas" var="excluir">
									    <s:param name="agendaVo.rowid" value="rowid"/>
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
							<td colspan="4" class="p-3">
								<s:url action="editarAgendas" var="novo"/>
								
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
	</body>
</html>