package br.com.soc.sistema.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import br.com.soc.sistema.infra.OpcoesComboBuscar;

class OpcoesComboBuscarConverterTest {

	private final OpcoesComboBuscarConverter converter = new OpcoesComboBuscarConverter();
	private final Map<String, Object> context = new HashMap<>();

	@Test
	void deveConverterCodigo1ParaId() {
		assertEquals(OpcoesComboBuscar.ID,
				converter.convertFromString(context, new String[]{"1"}, OpcoesComboBuscar.class));
	}

	@Test
	void deveConverterCodigo2ParaNome() {
		assertEquals(OpcoesComboBuscar.NOME,
				converter.convertFromString(context, new String[]{"2"}, OpcoesComboBuscar.class));
	}

	@Test
	void deveConverterParaStringUsandoCodigo() {
		assertEquals("1", converter.convertToString(context, OpcoesComboBuscar.ID));
	}

	@Test
	void deveRetornarNuloParaValorVazio() {
		assertNull(converter.convertFromString(context, new String[]{""}, OpcoesComboBuscar.class));
	}
}