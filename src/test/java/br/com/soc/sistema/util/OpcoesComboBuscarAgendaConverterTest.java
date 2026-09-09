package br.com.soc.sistema.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import br.com.soc.sistema.infra.OpcoesComboBuscarAgenda;

class OpcoesComboBuscarAgendaConverterTest {

	private final OpcoesComboBuscarAgendaConverter converter = new OpcoesComboBuscarAgendaConverter();
	private final Map<String, Object> context = new HashMap<>();

	@Test
	void deveConverterCodigo1ParaId() {
		assertEquals(OpcoesComboBuscarAgenda.ID,
				converter.convertFromString(context, new String[]{"1"}, OpcoesComboBuscarAgenda.class));
	}

	@Test
	void deveConverterCodigo3ParaPeriodo() {
		assertEquals(OpcoesComboBuscarAgenda.PERIODO,
				converter.convertFromString(context, new String[]{"3"}, OpcoesComboBuscarAgenda.class));
	}

	@Test
	void deveConverterParaStringUsandoCodigo() {
		assertEquals("2", converter.convertToString(context, OpcoesComboBuscarAgenda.NOME));
	}

	@Test
	void deveRetornarNuloParaValorVazio() {
		assertNull(converter.convertFromString(context, new String[]{""}, OpcoesComboBuscarAgenda.class));
	}

	@Test
	void deveRetornarNuloAoConverterObjetoNuloParaString() {
		assertNull(converter.convertToString(context, null));
	}
}