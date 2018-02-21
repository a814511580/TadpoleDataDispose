package maomi.utils;

import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesEncryptionUtil {
	private static final String CipherMode = "AES/CBC/PKCS5Padding";

	public static String byte2hex(byte[] paramArrayOfByte) {
		StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
		int i = 0;
		while (i < paramArrayOfByte.length) {
			String str = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
			if (str.length() == 1) {
				localStringBuffer.append("0");
			}
			localStringBuffer.append(str);
			i += 1;
		}
		return localStringBuffer.toString().toUpperCase();
	}

	private static IvParameterSpec createIV(StringBuffer paramString) {
		String str = paramString.toString(); 
		paramString = new StringBuffer(16);
		paramString.append(str);
		while (paramString.length() < 16) {
			paramString.append("0");
		}
		if (paramString.length() > 16) {
			paramString.setLength(16);
		}
		try {
			byte[] bytes = paramString.toString().getBytes("UTF-8");
			return new IvParameterSpec(bytes);
		} catch (UnsupportedEncodingException exp) {
			exp.printStackTrace();
		}
		return null;
	}

	private static SecretKeySpec createKey(StringBuffer paramString) {
		String str = paramString.toString();
		paramString = new StringBuffer(16);
		paramString.append(str);
		while (paramString.length() < 16) {
			paramString.append("0");
		}
		if (paramString.length() > 16) {
			paramString.setLength(16);
		}
		try {
			byte[] bytes = paramString.toString().getBytes("UTF-8");
			return new SecretKeySpec(bytes, "AES");
		} catch (UnsupportedEncodingException exp) {
			exp.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String paramString1, String paramString2, String paramString3) {
		try {
			byte[] bytes = hex2byte(paramString1);
			return new String(decrypt(bytes, paramString2, paramString3));
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] paramArrayOfByte, String paramString1, String paramString2) {
		try {
			SecretKeySpec keySpec = createKey(new StringBuffer(paramString1));
			Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			localCipher.init(2, keySpec, createIV(new StringBuffer(paramString2)));
			paramArrayOfByte = localCipher.doFinal(paramArrayOfByte);
			return paramArrayOfByte;
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return null;
	}

	public static String encrypt(String paramString1, String paramString2, String paramString3) {
		try {
			return byte2hex(encrypt(paramString1.getBytes("UTF-8"), paramString2, paramString3));
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return null;
	}

	public static byte[] encrypt(byte[] paramArrayOfByte, String paramString1, String paramString2) {
		try {
			SecretKeySpec keySpec = createKey(new StringBuffer(paramString1));
			Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			localCipher.init(1, keySpec, createIV(new StringBuffer(paramString2)));
			paramArrayOfByte = localCipher.doFinal(paramArrayOfByte);
			return paramArrayOfByte;
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return null;
	}

	private static byte[] hex2byte(String paramString) {
		if ((paramString == null) || (paramString.length() < 2)) {
			return new byte[0];
		}
		String str = paramString.toLowerCase();
		int j = str.length() / 2;
		byte[] arrayOfByte = new byte[j];
		int i = 0;
		for (;;) {
			if (i >= j) {
				break;
			}
			arrayOfByte[i] = ((byte) (Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16) & 0xFF));
			i += 1;
		}
		return arrayOfByte;
	}
}
