package br.com.soc.sistema.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class LocalDateConverterTest {

	private final LocalDateConverter converter = new LocalDateConverter();
	private final Map<String, Object> context = new HashMap<>();

	@Test
	void deveConverterStringValidaParaLocalDate() {
		Object resultado = converter.convertFromString(context, new String[]{"2026-09-10"}, LocalDate.class);

		assertEquals(LocalDate.of(2026, 9, 10), resultado);
	}

	@Test
	void deveConverterLocalDateParaString() {
		String resultado = converter.convertToString(context, LocalDate.of(2026, 9, 10));

		assertEquals("2026-09-10", resultado);
	}

	@Test
	void deveRetornarNuloParaValorVazio() {
		assertNull(converter.convertFromString(context, new String[]{""}, LocalDate.class));
	}

	@Test
	void deveRetornarNuloParaArrayNulo() {
		assertNull(converter.convertFromString(context, null, LocalDate.class));
	}

	@Test
	void deveRetornarNuloAoConverterObjetoNuloParaString() {
		assertNull(converter.convertToString(context, null));
	}
}