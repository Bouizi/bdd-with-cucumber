package br.custom.bookstore.helper;

import java.text.Normalizer;

public class Utils {
	public static String removeAcentos(final String str) {
		String strSemAcentos = Normalizer.normalize(str, Normalizer.Form.NFD);
		strSemAcentos = strSemAcentos.replaceAll("[^\\p{ASCII}]", "");
		return strSemAcentos;
	}
}
