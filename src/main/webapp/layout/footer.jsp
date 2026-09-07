<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<style>
	.soc-footer {
		background: #424444ff;
		padding: 1.5rem 2rem;
	}

	.soc-footer .container-fluid {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.soc-footer .logo img {
		height: 48px;
	}

	.soc-footer .creditos {
		color: #f1f1f1;
		font-size: 1.05rem;
		text-align: center;
		flex: 1;
	}

	.soc-footer .redes {
		display: flex;
		gap: 0.75rem;
	}

	.soc-footer .redes a {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 40px;
		height: 40px;
		border-radius: 50%;
		background: #55555b;
		color: #ffffff;
		text-decoration: none;
		transition: background 0.2s;
	}

	.soc-footer .redes a:hover {
		background: #1b9aa0;
	}

	.soc-footer .redes svg {
		width: 18px;
		height: 18px;
		fill: #ffffff;
	}
</style>

<footer class="soc-footer">
	<div class="container-fluid">
		<div class="logo">
			<s:url value="/imagens/soc_logo_branca.png" var="logoBrancaUrl"/>
			<img src="${logoBrancaUrl}" alt="SOC">
		</div>

		<div class="creditos">
			Desafio Técnico Ager 2026 - Cauê Sobral
		</div>

		<div class="redes">
			<a href="https://www.facebook.com/socgestaosst/" target="_blank" rel="noopener" aria-label="Facebook">
				<svg viewBox="0 0 24 24"><path d="M22 12a10 10 0 1 0-11.6 9.9v-7H7.9V12h2.5V9.8c0-2.5 1.5-3.9 3.8-3.9 1.1 0 2.2.2 2.2.2v2.5h-1.3c-1.2 0-1.6.8-1.6 1.6V12h2.8l-.4 2.9h-2.4v7A10 10 0 0 0 22 12z"/></svg>
			</a>
			<a href="https://www.instagram.com/socgestaosst/" target="_blank" rel="noopener" aria-label="Instagram">
				<svg viewBox="0 0 24 24"><path d="M12 2c2.7 0 3.1 0 4.1.1 1.1 0 1.8.2 2.5.5.7.3 1.2.6 1.8 1.2.6.6.9 1.1 1.2 1.8.3.7.5 1.4.5 2.5.1 1 .1 1.4.1 4.1s0 3.1-.1 4.1c0 1.1-.2 1.8-.5 2.5-.3.7-.6 1.2-1.2 1.8-.6.6-1.1.9-1.8 1.2-.7.3-1.4.5-2.5.5-1 .1-1.4.1-4.1.1s-3.1 0-4.1-.1c-1.1 0-1.8-.2-2.5-.5-.7-.3-1.2-.6-1.8-1.2-.6-.6-.9-1.1-1.2-1.8-.3-.7-.5-1.4-.5-2.5C2 15.1 2 14.7 2 12s0-3.1.1-4.1c0-1.1.2-1.8.5-2.5.3-.7.6-1.2 1.2-1.8.6-.6 1.1-.9 1.8-1.2.7-.3 1.4-.5 2.5-.5C8.9 2 9.3 2 12 2zm0 1.8c-2.6 0-3 0-4 .1-.9 0-1.4.2-1.7.3-.4.2-.7.4-1 .7-.3.3-.5.6-.7 1-.1.3-.3.8-.3 1.7-.1 1-.1 1.4-.1 4s0 3 .1 4c0 .9.2 1.4.3 1.7.2.4.4.7.7 1 .3.3.6.5 1 .7.3.1.8.3 1.7.3 1 .1 1.4.1 4 .1s3 0 4-.1c.9 0 1.4-.2 1.7-.3.4-.2.7-.4 1-.7.3-.3.5-.6.7-1 .1-.3.3-.8.3-1.7.1-1 .1-1.4.1-4s0-3-.1-4c0-.9-.2-1.4-.3-1.7-.2-.4-.4-.7-.7-1-.3-.3-.6-.5-1-.7-.3-.1-.8-.3-1.7-.3-1-.1-1.4-.1-4-.1zm0 3.5a4.7 4.7 0 1 1 0 9.4 4.7 4.7 0 0 1 0-9.4zm0 1.8a2.9 2.9 0 1 0 0 5.8 2.9 2.9 0 0 0 0-5.8zm5-2a1.1 1.1 0 1 1-2.2 0 1.1 1.1 0 0 1 2.2 0z"/></svg>
			</a>
			<a href="https://x.com/socgestaosst/" target="_blank" rel="noopener" aria-label="X">
				<svg viewBox="0 0 24 24"><path d="M18.9 2h3.3l-7.2 8.2L23.5 22h-6.6l-5.2-6.8L5.8 22H2.5l7.7-8.8L1 2h6.8l4.7 6.2zm-1.2 18h1.8L7 4h-2z"/></svg>
			</a>
			<a href="https://www.youtube.com/SOCgestaosst" target="_blank" rel="noopener" aria-label="YouTube">
				<svg viewBox="0 0 24 24"><path d="M23 12s0-3.6-.4-5.3c-.3-.9-1-1.7-2-2C18.9 4.3 12 4.3 12 4.3s-6.9 0-8.6.4c-.9.3-1.7 1-2 2C1 8.4 1 12 1 12s0 3.6.4 5.3c.3.9 1 1.7 2 2 1.7.4 8.6.4 8.6.4s6.9 0 8.6-.4c.9-.3 1.7-1 2-2 .4-1.7.4-5.3.4-5.3zM9.8 15.4V8.6l6 3.4-6 3.4z"/></svg>
			</a>
			<a href="https://www.linkedin.com/company/socgestaosst/" target="_blank" rel="noopener" aria-label="LinkedIn">
				<svg viewBox="0 0 24 24"><path d="M20.4 20.4h-3.5v-5.5c0-1.3 0-3-1.8-3s-2.1 1.4-2.1 2.9v5.6H9.5V9h3.4v1.6h.1c.5-.9 1.6-1.8 3.4-1.8 3.6 0 4.3 2.4 4.3 5.5v6.1zM5.3 7.4a2 2 0 1 1 0-4 2 2 0 0 1 0 4zM7 20.4H3.6V9H7v11.4z"/></svg>
			</a>
		</div>
	</div>
</footer>