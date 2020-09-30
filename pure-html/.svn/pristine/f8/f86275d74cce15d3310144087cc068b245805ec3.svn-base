package com.sinosoft.sinoep.sso.filter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpRequestUtil {
 public static final String POST = "POST";
 public static final String GET = "GET";
 private static Log log = LogFactory.getLog(HttpRequestUtil.class);

 public HttpRequestUtil() {
 }

 public static String sendGet(String url, String param) {
     String result = "";
     BufferedReader in = null;

     try {
         String urlNameString = url + "?" + param;
         URL realUrl = new URL(urlNameString);
         URLConnection connection = realUrl.openConnection();
         connection.setRequestProperty("accept", "*/*");
         connection.setRequestProperty("connection", "Keep-Alive");
         connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
         connection.connect();
         Map<String, List<String>> map = connection.getHeaderFields();
         Iterator var9 = map.keySet().iterator();

         String line;
         while(var9.hasNext()) {
             line = (String)var9.next();
             log.debug(line + "--->" + map.get(line));
         }

         for(in = new BufferedReader(new InputStreamReader(connection.getInputStream())); (line = in.readLine()) != null; result = result + line) {
             ;
         }
     } catch (Exception var18) {
         log.error("发送GET请求出现异常！" + var18);
         var18.printStackTrace();
     } finally {
         try {
             if (in != null) {
                 in.close();
             }
         } catch (Exception var17) {
             var17.printStackTrace();
         }

     }

     return result;
 }

 public static String sendPost(String url, String param) {
     PrintWriter out = null;
     BufferedReader in = null;
     String result = "";

     try {
         URL realUrl = new URL(url);
         URLConnection conn = realUrl.openConnection();
         conn.setRequestProperty("accept", "*/*");
         conn.setRequestProperty("connection", "Keep-Alive");
         conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
         conn.setRequestProperty("Accept-Charset", "utf-8");
         conn.setRequestProperty("contentType", "utf-8");
         conn.setDoOutput(true);
         conn.setDoInput(true);
         out = new PrintWriter(conn.getOutputStream());
         out.print(param);
         out.flush();

         String line;
         for(in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); (line = in.readLine()) != null; result = result + line) {
             ;
         }
     } catch (Exception var16) {
         var16.printStackTrace();
         log.error("发送 POST 请求出现异常！" + var16);
     } finally {
         try {
             if (out != null) {
                 out.close();
             }

             if (in != null) {
                 in.close();
             }
         } catch (IOException var15) {
             var15.printStackTrace();
         }

     }

     return result;
 }

 public static String sendPost(String url, Map<String, String> params, String charset) {
     StringBuffer response = new StringBuffer();
     HttpClient client = new HttpClient();
     HttpMethod method = new PostMethod(url);
     method.getParams().setCookiePolicy("ignoreCookies");
     method.setRequestHeader("Cookie", "special-cookie=value");
     if (params != null) {
         HttpMethodParams p = new HttpMethodParams();
         Iterator var8 = params.entrySet().iterator();

         while(var8.hasNext()) {
             Entry<String, String> entry = (Entry)var8.next();
             p.setParameter((String)entry.getKey(), entry.getValue());
         }

         method.setParams(p);
     }

     try {
         client.executeMethod(method);
         if (method.getStatusCode() == 200) {
             BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));

             String line;
             while((line = reader.readLine()) != null) {
                 response.append(line);
             }

             reader.close();
         }
     } catch (IOException var11) {
         log.error("执行HTTP Post请求" + url + "时，发生异常！", var11);
     } finally {
         method.releaseConnection();
     }

     return response.toString();
 }

 public static JSONObject httpsRequest(String requestUrl, String requestMethod) {
     return httpsRequest(requestUrl, requestMethod, (String)null);
 }

 public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
     return JSONObject.fromObject(httpsRequestString(requestUrl, requestMethod, outputStr));
 }

 public static String httpsRequestString(String requestUrl, String requestMethod) {
     return httpsRequestString(requestUrl, requestMethod, (String)null);
 }

 public static String httpsRequestString(String requestUrl, String requestMethod, String outputStr) {
     String result = "";

     try {
         TrustManager[] tm = new TrustManager[]{new JEEX509TrustManager()};
         SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
         sslContext.init((KeyManager[])null, tm, new SecureRandom());
         SSLSocketFactory ssf = sslContext.getSocketFactory();
         URL url = new URL(requestUrl);
         HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
         conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
         conn.setSSLSocketFactory(ssf);
         conn.setDoOutput(true);
         conn.setDoInput(true);
         conn.setUseCaches(false);
         conn.setRequestMethod(requestMethod);
         OutputStream outputStream;
         if (outputStr != null) {
        	 outputStream = conn.getOutputStream();
        	 outputStream.write(outputStr.getBytes("UTF-8"));
        	 outputStream.close();
         }

         InputStream inputStream = conn.getInputStream();
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         String str = null;
         StringBuffer buffer = new StringBuffer();

         while((str = bufferedReader.readLine()) != null) {
             buffer.append(str);
         }

         bufferedReader.close();
         inputStreamReader.close();
         inputStream.close();
         inputStream = null;
         conn.disconnect();
         result = buffer.toString();
     } catch (Exception var14) {
         var14.printStackTrace();
     }

     return result;
 }

 public static byte[] httpsRequestByte(String requestUrl, String requestMethod) {
     return httpsRequestByte(requestUrl, requestMethod, (String)null);
 }

 public static byte[] httpsRequestByte(String requestUrl, String requestMethod, String outputStr) {
     try {
         TrustManager[] tm = new TrustManager[]{new JEEX509TrustManager()};
         SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
         sslContext.init((KeyManager[])null, tm, new SecureRandom());
         SSLSocketFactory ssf = sslContext.getSocketFactory();
         URL url = new URL(requestUrl);
         HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
         conn.setSSLSocketFactory(ssf);
         conn.setDoOutput(true);
         conn.setDoInput(true);
         conn.setUseCaches(false);
         conn.setRequestMethod(requestMethod);
         if (outputStr != null) {
             OutputStream outputStream = conn.getOutputStream();
             outputStream.write(outputStr.getBytes("UTF-8"));
             outputStream.close();
         }

         InputStream inputStream = conn.getInputStream();
         ByteArrayOutputStream output = new ByteArrayOutputStream();
         byte[] buffer = new byte[4096];
         boolean var11 = false;

         int n;
         while(-1 != (n = inputStream.read(buffer))) {
             output.write(buffer, 0, n);
         }

         return output.toByteArray();
     } catch (Exception var12) {
         var12.printStackTrace();
         return null;
     }
 }

 public static String urlEnodeUTF8(String str) {
     String result = str;

     try {
         result = URLEncoder.encode(str, "UTF-8");
     } catch (Exception var3) {
         var3.printStackTrace();
     }

     return result;
 }

 public static void main(String[] args) {
     String url = "https://clearing.banquanmaoyi.com/funds/orderReturnpay";
     String sys_no = "1001";
     String order_no = "123456";
     String str = httpsRequestString(url, "POST", "sys_no=" + sys_no + "&order_no=" + order_no);
     System.out.println(str);
 }
}
