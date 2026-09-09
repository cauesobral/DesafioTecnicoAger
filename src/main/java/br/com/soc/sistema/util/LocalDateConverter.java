package br.com.soc.sistema.util;

import java.time.LocalDate;
import java.util.Map;
import org.apache.struts2.util.StrutsTypeConverter;

public class LocalDateConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0 || values[0] == null || values[0].isEmpty()) {
			return null;
		}
		return LocalDate.parse(values[0]);
	}

	@Override
	public String convertToString(Map context, Object o) {
		return o == null ? null : o.toString();
	}
}