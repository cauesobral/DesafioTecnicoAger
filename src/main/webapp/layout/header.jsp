<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<style>
	.soc-header {
		background: #ffffff;
		position: relative;
		overflow: hidden;
		border-bottom: 3px solid transparent;
		border-image: linear-gradient(to right, #1b9aa0, #1b9aa0 70%, #f2b705) 1;
	}

	.soc-header .container-fluid {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 1rem 2rem;
		padding-right: 220px;
		position: relative;
		z-index: 2;
	}

	.soc-header .logo img {
		height: 48px;
	}

	.soc-header .nav-links {
		display: flex;
		gap: 2.5rem;
		list-style: none;
		margin: 0;
		padding: 0;
	}

	.soc-header .nav-links a {
		color: #5f6b7a;
		font-weight: 600;
		text-decoration: none;
		font-size: 1.05rem;
	}

	.soc-header .nav-links a:hover {
		color: #1b9aa0;
	}

	.soc-header .brand-tag {
		color: #1b9aa0;
		font-weight: 700;
		font-size: 1.05rem;
		white-space: nowrap;
	}

	.soc-header .stripes {
		position: absolute;
		top: 0;
		right: 0;
		height: 100%;
		width: 260px;
		z-index: 1;
		pointer-events: none;
	}

	.soc-header .stripes span {
		position: absolute;
		top: -20%;
		height: 140%;
		width: 40px;
		transform: skewX(-20deg);
	}

	.soc-header .stripes span:nth-child(1) {
		right: 90px;
		background: #1b9aa0;
	}

	.soc-header .stripes span:nth-child(2) {
		right: 40px;
		background: #f2b705;
	}
</style>

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