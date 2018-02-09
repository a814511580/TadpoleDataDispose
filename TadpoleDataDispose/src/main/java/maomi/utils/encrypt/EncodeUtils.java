package maomi.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EncodeUtils {
	private EncodeUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	public static String urlEncode(String paramString1, String paramString2) {
		try {
			paramString2 = URLEncoder.encode(paramString1, paramString2);
			return paramString2;
		} catch (UnsupportedEncodingException exp) {
			exp.printStackTrace();
		}
		return paramString1;
	}
}
