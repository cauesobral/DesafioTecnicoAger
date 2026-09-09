package br.com.soc.sistema.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class LocalTimeConverterTest {

	private final LocalTimeConverter converter = new LocalTimeConverter();
	private final Map<String, Object> context = new HashMap<>();

	@Test
	void deveConverterStringValidaParaLocalTime() {
		Object resultado = converter.convertFromString(context, new String[]{"08:30"}, LocalTime.class);

		assertEquals(LocalTime.of(8, 30), resultado);
	}

	@Test
	void deveConverterLocalTimeParaString() {
		String resultado = converter.convertToString(context, LocalTime.of(8, 30));

		assertEquals("08:30", resultado);
	}

	@Test
	void deveRetornarNuloParaValorVazio() {
		assertNull(converter.convertFromString(context, new String[]{""}, LocalTime.class));
	}

	@Test
	void deveRetornarNuloParaArrayNulo() {
		assertNull(converter.convertFromString(context, null, LocalTime.class));
	}
}