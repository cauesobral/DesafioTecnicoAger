package br.com.soc.sistema.util;

import java.time.LocalDate;
import java.util.Map;
import org.apache.struts2.util.StrutsTypeConverter;

public class LocalDateConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		return LocalDate.parse(values[0]);
	}

	@Override
	public String convertToString(Map context, Object o) {
		return o.toString();
	}
}