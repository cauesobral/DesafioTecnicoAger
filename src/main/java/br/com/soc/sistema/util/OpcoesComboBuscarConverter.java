package br.com.soc.sistema.util;

import java.util.Map;
import org.apache.struts2.util.StrutsTypeConverter;
import br.com.soc.sistema.infra.OpcoesComboBuscar;

public class OpcoesComboBuscarConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0 || values[0] == null || values[0].isEmpty()) {
			return null;
		}
		return OpcoesComboBuscar.buscarPor(values[0]);
	}

	@Override
	public String convertToString(Map context, Object o) {
		if (o == null) {
			return null;
		}
		return ((OpcoesComboBuscar) o).getCodigo();
	}
}