<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:url value="/estilos/global.css" var="cssGlobal"/>
<link rel='stylesheet' href='${cssGlobal}'>
<s:url value="/estilos/header.css" var="cssHeader"/>
<link rel='stylesheet' href='${cssHeader}'>

<header class="soc-header">
	<div class="stripes">
		<span></span>
		<span></span>
	</div>

	<div class="container-fluid">
		<div class="logo">
			<s:url value="/imagens/soc_logo.png" var="logoUrl"/>
			<img src="${logoUrl}" alt="SOC">
		</div>

		<ul class="nav-links">
			<li>
				<s:url action="todosFuncionarios" var="urlFuncionarios"/>
				<a href="${urlFuncionarios}"><s:text name="label.menu.funcionarios"/></a>
			</li>
			<li>
				<s:url action="todosCompromissos" var="urlCompromissos"/>
				<a href="${urlCompromissos}"><s:text name="label.menu.compromissos"/></a>
			</li>
			<li>
				<s:url action="todosAgendas" var="urlAgendas"/>
				<a href="${urlAgendas}"><s:text name="label.menu.agenda"/></a>
			</li>
			<li>
				<s:url action="formularioRelatorios" var="urlRelatorios"/>
				<a href="${urlRelatorios}"><s:text name="label.menu.relatorio"/></a>
			</li>
		</ul>

		<div class="brand-tag">Desafio Técnico Ager</div>
	</div>
</header>