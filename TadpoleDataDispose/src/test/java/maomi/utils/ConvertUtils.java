package maomi.utils;

public class ConvertUtils
{
  static final char[] hexDigits = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  
  private ConvertUtils()
  {
    throw new UnsupportedOperationException("u can't instantiate me...");
  } 
  private static int hex2Dec(char paramChar)
  {
    if ((paramChar >= '0') && (paramChar <= '9')) {
      return paramChar - '0';
    }
    if ((paramChar >= 'A') && (paramChar <= 'F')) {
      return paramChar - 'A' + 10;
    }
    throw new IllegalArgumentException();
  }
  
  public static byte[] hexString2Bytes(String paramString)
  { 
    int j = paramString.length();
    int i = j;
    Object localObject = paramString;
    if (j % 2 != 0)
    {
      localObject = "0" + paramString;
      i = j + 1;
    }
    char[] arrayOfChar = ((String)localObject).toUpperCase().toCharArray();
    byte[] bytes = new byte[i >> 1];
    j = 0;
    for (;;)
    { 
      if (j >= i) {
        break;
      }
      bytes[(j >> 1)] = ((byte)(hex2Dec(arrayOfChar[j]) << 4 | hex2Dec(arrayOfChar[(j + 1)])));
      j += 2;
    }
    return bytes;
  }
  

  public static String bytes2HexString(byte[] paramArrayOfByte)
  { 
    int k =paramArrayOfByte.length; 
    char[] arrayOfChar = new char[k << 1];
    int i = 0;
    int j = 0;
    while (i < k)
    {
      int m = j + 1;
      arrayOfChar[j] = hexDigits[(paramArrayOfByte[i] >>> 4 & 0xF)];
      j = m + 1;
      arrayOfChar[m] = hexDigits[(paramArrayOfByte[i] & 0xF)];
      i += 1;
    }
    return new String(arrayOfChar);
  }
   
}
