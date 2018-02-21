package maomi.utils;
 /*
import java.io.IOException;
import java.io.PrintStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import okhttp3.Dns;

public class HttpDNS
{
  private static long m_defaultTTL = 600000L;
  private static HashMap<String, SoftReference<CacheEntity>> m_dnscache;
  private static String m_httpdns_url = "http://119.29.29.29/d?dn=";
  
  static
  {
    m_dnscache = new HashMap();
  }
  
  public static String getAddressByName(String paramString)
  {
    Object localObject2 = null;
    Object localObject3 = (SoftReference)m_dnscache.get(paramString);
    Object localObject1 = localObject2;
    if (localObject3 != null)
    {
      localObject3 = (CacheEntity)((SoftReference)localObject3).get();
      if (localObject3 == null) {
        break label197;
      }
      localObject1 = localObject2;
      if (System.currentTimeMillis() - ((CacheEntity)localObject3).timestamp < m_defaultTTL)
      {
        localObject1 = ((CacheEntity)localObject3).getIps();
        System.out.println("cachedns get ips from cache:" + paramString);
      }
    }
    for (;;)
    {
      if (localObject1 != null)
      {
        localObject2 = localObject1;
        if (localObject1.length != 0) {}
      }
      else
      {
        localObject2 = getips(paramString);
        localObject1 = new CacheEntity(System.currentTimeMillis(), (String[])localObject2);
        m_dnscache.put(paramString, new SoftReference(localObject1));
        Log.i("cachedns", "add ips to cache:" + paramString);
      }
      Log.i("cachedns", "get ips:" + paramString + " ");
      if ((localObject2 == null) || (localObject2.length <= 0)) {
        break;
      }
      return localObject2[((int)(Math.random() * localObject2.length))];
      label197:
      Log.i("cachedns", "soft reference miss");
      localObject1 = localObject2;
    }
    return "";
  }
  
  private static String[] getips(String paramString)
  {
    Object localObject4 = null;
    Object localObject2 = null;
    Object localObject1 = null;
    for (;;)
    {
      try
      {
        localHttpURLConnection = (HttpURLConnection)new URL(m_httpdns_url + paramString.trim()).openConnection();
        localObject1 = localHttpURLConnection;
        localObject2 = localHttpURLConnection;
        localHttpURLConnection.setConnectTimeout(10000);
        localObject1 = localHttpURLConnection;
        localObject2 = localHttpURLConnection;
        localHttpURLConnection.setReadTimeout(10000);
        localObject1 = localHttpURLConnection;
        localObject2 = localHttpURLConnection;
        localHttpURLConnection.setDoInput(true);
        localObject1 = localHttpURLConnection;
        localObject2 = localHttpURLConnection;
        localHttpURLConnection.connect();
        localObject1 = localHttpURLConnection;
        localObject2 = localHttpURLConnection;
        localObject3 = new Scanner(localHttpURLConnection.getInputStream()).useDelimiter("\\A");
        localObject1 = localHttpURLConnection;
        localObject2 = localHttpURLConnection;
        if (!((Scanner)localObject3).hasNext()) {
          continue;
        }
        localObject1 = localHttpURLConnection;
        localObject2 = localHttpURLConnection;
        localObject3 = ((Scanner)localObject3).next();
        localObject1 = localHttpURLConnection;
        localObject2 = localHttpURLConnection;
        Log.i("getips", "getips: " + paramString + " " + (String)localObject3);
        localObject1 = localHttpURLConnection;
        localObject2 = localHttpURLConnection;
        if (((String)localObject3).indexOf(";") <= -1) {
          continue;
        }
        localObject1 = localHttpURLConnection;
        localObject2 = localHttpURLConnection;
        localObject3 = ((String)localObject3).split(";");
        localObject1 = localObject3;
        localObject2 = localObject1;
        if (localHttpURLConnection == null) {
          break label363;
        }
        localHttpURLConnection.disconnect();
      }
      catch (IOException localIOException)
      {
        HttpURLConnection localHttpURLConnection;
        Object localObject3;
        localObject2 = localObject1;
        localIOException.printStackTrace();
        localObject2 = localObject4;
        if (localObject1 == null) {
          break label363;
        }
        ((HttpURLConnection)localObject1).disconnect();
        localObject1 = null;
        continue;
      }
      finally
      {
        if (localObject2 == null) {
          continue;
        }
        ((HttpURLConnection)localObject2).disconnect();
      }
      if (localObject1 == null)
      {
        try
        {
          System.out.println("NNNNN:" + ((InetAddress)Dns.SYSTEM.lookup(paramString).get(0)).getHostAddress());
          paramString = ((InetAddress)Dns.SYSTEM.lookup(paramString).get(0)).getHostAddress().toString();
          return new String[] { paramString };
        }
        catch (UnknownHostException paramString)
        {
          paramString.printStackTrace();
        }
        localObject3 = "";
        continue;
        localObject1 = localHttpURLConnection;
        localObject2 = localHttpURLConnection;
        localObject3 = ((String)localObject3).trim();
        localObject1 = new String[] { localObject3 };
        continue;
      }
      return (String[])localObject1;
      label363:
      localObject1 = localObject2;
    }
  }
  
  static class CacheEntity
  {
    private String[] ips;
    private long timestamp;
    
    public CacheEntity(long paramLong, String[] paramArrayOfString)
    {
      this.timestamp = paramLong;
      this.ips = paramArrayOfString;
    }
    
    public String[] getIps()
    {
      return this.ips;
    }
    
    public long getTimestamp()
    {
      return this.timestamp;
    }
  }
}*/
