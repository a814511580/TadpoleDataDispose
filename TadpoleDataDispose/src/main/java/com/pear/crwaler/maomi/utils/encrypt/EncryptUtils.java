package com.pear.crwaler.maomi.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
	private static final String AES_Algorithm = "AES";
	public static String AES_Transformation = "AES/ECB/NoPadding";
	private static final String DES_Algorithm = "DES";
	public static String DES_Transformation = "DES/ECB/NoPadding";
	private static final String TripleDES_Algorithm = "DESede";
	public static String TripleDES_Transformation = "DESede/ECB/NoPadding";

	private EncryptUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	public static byte[] encryptMD2(byte[] paramArrayOfByte) {
		return hashTemplate(paramArrayOfByte, "MD2");
	}

	public static String encryptMD2ToString(String paramString) {
		return encryptMD2ToString(paramString.getBytes());
	}

	public static String encryptMD2ToString(byte[] paramArrayOfByte) {
		return ConvertUtils.bytes2HexString(encryptMD2(paramArrayOfByte));
	}

	public static byte[] encryptMD5(byte[] paramArrayOfByte) {
		return hashTemplate(paramArrayOfByte, "MD5");
	}
  
  
 
	public static String encryptMD5ToString(String paramString) {
		return encryptMD5ToString(paramString.getBytes());
	}

	public static String encryptMD5ToString(String paramString1, String paramString2) {
		return ConvertUtils.bytes2HexString(encryptMD5((paramString1 + paramString2).getBytes()));
	}

	public static String encryptMD5ToString(byte[] paramArrayOfByte) {
		return ConvertUtils.bytes2HexString(encryptMD5(paramArrayOfByte));
	}

	public static String encryptMD5ToString(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) {
		if ((paramArrayOfByte1 == null) || (paramArrayOfByte2 == null)) {
			return null;
		}
		byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
		System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
		System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, paramArrayOfByte1.length, paramArrayOfByte2.length);
		return ConvertUtils.bytes2HexString(encryptMD5(arrayOfByte));
	}

	public static byte[] encryptSHA1(byte[] paramArrayOfByte) {
		return hashTemplate(paramArrayOfByte, "SHA1");
	}

	public static String encryptSHA1ToString(String paramString) {
		return encryptSHA1ToString(paramString.getBytes());
	}

	public static String encryptSHA1ToString(byte[] paramArrayOfByte) {
		return ConvertUtils.bytes2HexString(encryptSHA1(paramArrayOfByte));
	}

	public static byte[] encryptSHA224(byte[] paramArrayOfByte) {
		return hashTemplate(paramArrayOfByte, "SHA224");
	}

	public static String encryptSHA224ToString(String paramString) {
		return encryptSHA224ToString(paramString.getBytes());
	}

	public static String encryptSHA224ToString(byte[] paramArrayOfByte) {
		return ConvertUtils.bytes2HexString(encryptSHA224(paramArrayOfByte));
	}

	public static byte[] encryptSHA256(byte[] paramArrayOfByte) {
		return hashTemplate(paramArrayOfByte, "SHA256");
	}

	public static String encryptSHA256ToString(String paramString) {
		return encryptSHA256ToString(paramString.getBytes());
	}

	public static String encryptSHA256ToString(byte[] paramArrayOfByte) {
		return ConvertUtils.bytes2HexString(encryptSHA256(paramArrayOfByte));
	}

	public static byte[] encryptSHA384(byte[] paramArrayOfByte) {
		return hashTemplate(paramArrayOfByte, "SHA384");
	}

	public static String encryptSHA384ToString(String paramString) {
		return encryptSHA384ToString(paramString.getBytes());
	}

	public static String encryptSHA384ToString(byte[] paramArrayOfByte) {
		return ConvertUtils.bytes2HexString(encryptSHA384(paramArrayOfByte));
	}

	public static byte[] encryptSHA512(byte[] paramArrayOfByte) {
		return hashTemplate(paramArrayOfByte, "SHA512");
	}

	public static String encryptSHA512ToString(String paramString) {
		return encryptSHA512ToString(paramString.getBytes());
	}

	public static String encryptSHA512ToString(byte[] paramArrayOfByte) {
		return ConvertUtils.bytes2HexString(encryptSHA512(paramArrayOfByte));
	}

	private static byte[] hashTemplate(byte[] paramArrayOfByte, String paramString) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(paramString);
			messageDigest.update(paramArrayOfByte);
			paramArrayOfByte = messageDigest.digest();
			return paramArrayOfByte;
		} catch (NoSuchAlgorithmException exp) {
			exp.printStackTrace();
		}
		return null;
	}
 
}
