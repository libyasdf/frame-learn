package com.sinosoft.sinoep.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpRequestUtil {
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String DEFAULT_CHARSET = "UTF-8";
    private static Log log = LogFactory.getLog(HttpRequestUtil.class);

    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        // 默认统一对参数进行encode编码
        return sendGetReq(url, param, true);
    }

    /**
     * 向指定URL发送GET方法的请求，不进行转码
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式,若请求参数不是规范格式请提前对参数进行转码处理
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGetNoEncode(String url, String param) {
        // 默认统一对参数进行encode编码
        return sendGetReq(url, param, false);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        // 默认统一对参数进行encode编码
        return sendPostReq(url, param, true);
    }

    /**
     * 向指定URL发送POST方法的请求，不进行转码
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式,若请求参数不是规范格式请提前对参数进行转码处理
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendPostNoEncode(String url, String param) {
        // 默认统一对参数进行encode编码
        return sendPostReq(url, param, false);
    }

    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param isEncode
     *            是否要对参数进行 Encode 处理， true 进行处理 false 不进行处理
     * @return URL 所代表远程资源的响应结果
     */
    private static String sendGetReq(String url, String param, Boolean isEncode) {
        String result = "";
        InputStreamReader reader = null;
        try {
            String urlNameString = url;
            if (StringUtils.isNotBlank(param) && isEncode) {
                urlNameString = urlNameString + "?" + settingPar(param);
            }
            else {
                urlNameString = urlNameString + "?" + param;
            }
            log.debug("接口请求路径：" + urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset", DEFAULT_CHARSET);
            connection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
            String requestTimeOut = ConfigProperties.getPropertyValue("REQUEST_TIME_OUT");
            connection.setReadTimeout(Integer.parseInt(requestTimeOut));//设置读取超时时间
            connection.setConnectTimeout(Integer.parseInt(requestTimeOut));//设置连接超时时间
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                log.debug(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            reader = new InputStreamReader(connection.getInputStream(), DEFAULT_CHARSET);
            result = reader2String(reader);
        }
        catch (Exception e) {
            log.error("发送GET请求出现异常！", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (Exception e2) {
                return "";
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param isEncode
     *            是否要对参数进行 Encode 处理， true 进行处理 false 不进行处理
     * 
     * @return 所代表远程资源的响应结果
     */
    private static String sendPostReq(String url, String param, Boolean isEncode) {
        log.debug("begin post service url:" + url);
        log.debug("begin post service param:" + param);
        PrintWriter out = null;
        BufferedReader in = null;
        InputStreamReader reader = null;
        String result = "";
        InputStream input = null;
        try {
            String urlNameString = url;
            if (StringUtils.isNotBlank(param) && isEncode) {
                // param = settingPar(param);
                urlNameString = urlNameString + "?" + settingPar(param);
            }
            else {
                urlNameString = urlNameString + "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", DEFAULT_CHARSET);
            conn.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
            String requestTimeOut = ConfigProperties.getPropertyValue("REQUEST_TIME_OUT");
            conn.setReadTimeout(Integer.parseInt(requestTimeOut));//设置读取超时时间
            conn.setConnectTimeout(Integer.parseInt(requestTimeOut));//设置连接超时时间
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), DEFAULT_CHARSET));
            // 发送请求参数
            //out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            input = conn.getInputStream();
            reader = new InputStreamReader(input, DEFAULT_CHARSET);
            return reader2String(reader);
        }
        catch (Exception e) {
            log.error("发送 POST 请求出现异常！", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex) {
                return "";
            }
        }
        return result;
    }

    /**
     * 流转字符串
     * 
     * @param reader
     * @return
     * @throws IOException
     */
    private static String reader2String(Reader reader) throws IOException {
        int len = 0;
        char[] buf = new char[1024];
        StringBuilder sb = new StringBuilder();
        while ((len = reader.read(buf)) != -1) {
            sb.append(new String(buf, 0, len));
        }
        return sb.toString();
    }

    /**
     * 
     * <B>方法名称：</B><BR>
     * <B>概要说明：对参数值进行转码</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月11日 上午11:00:08
     * @param par
     * @return
     * @throws UnsupportedEncodingException String
     */
    private static String settingPar(String par) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(par)) {
            String[] pars = par.split("&");
            StringBuffer sb = new StringBuffer();
            for (String pa : pars) {
                String[] tempPar = pa.split("=");
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(tempPar[0].trim());
                sb.append("=");
                if (tempPar.length > 1) {
                    sb.append(URLEncoder.encode(tempPar[1].trim(), DEFAULT_CHARSET));
                }
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * 
     * <B>方法名称：发起POST请求</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param url
     *            请求地址
     * @param params
     *            传递的参数
     * @param charset
     *            字符集
     * @return
     */
    public static String sendPost(String url, Map<String, String> params, String charset) {
        String result = "";
        HttpClient client = new HttpClient();
        HttpMethod method = new PostMethod(url);
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        method.setRequestHeader("Cookie", "special-cookie=value");
        // method.setRequestHeader("", "");
        // 设置Http Post数据
        if (params != null) {
            HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                p.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(p);
        }
        // List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        // nvps.add(new BasicNameValuePair("signature", ""));
        // method.setEntity(new UrlEncodedFormEntity(nvps));
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
                result = reader2String(reader);
                in.close();
                reader.close();
            }
        }
        catch (IOException e) {
            log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        }
        finally {
            method.releaseConnection();
        }
        return result;
    }

    /**
     * 
     * <B>方法名称：</B><BR>
     * <B>概要说明：发JSON报文用此方法</B><BR>
     * 
     * @param url
     * @param data
     * @return
     */
    public static String sendPostUrl(String url, String data) {
        String response = null;
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);
                StringEntity stringentity = new StringEntity(data, ContentType.create("text/json", DEFAULT_CHARSET));
                stringentity.setContentEncoding(DEFAULT_CHARSET);
                httppost.setEntity(stringentity);
                httpresponse = httpclient.execute(httppost);
                response = EntityUtils.toString(httpresponse.getEntity(), DEFAULT_CHARSET);
            }
            finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        }
        catch (Exception e) {
            log.error("发JSON报文错误", e);
        }
        return response;
    }

    /**
     * 
     * <B>方法名称：</B><BR>
     * <B>概要说明：发JSON报文用此方法</B><BR>
     * 
     * @param url
     * @param data
     * @return
     */
    public static String sendGetUrl(String url) {
        String response = null;
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault();
                HttpGet httpget = new HttpGet(url);
                httpresponse = httpclient.execute(httpget);
                response = EntityUtils.toString(httpresponse.getEntity(), DEFAULT_CHARSET);
            }
            finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        }
        catch (Exception e) {
            log.error("发JSON报文错误", e);
        }
        return response;
    }

    // 发送请求
    public static JSONObject httpsRequest(String requestUrl, String requestMethod) {
        return httpsRequest(requestUrl, requestMethod, null);
    }

    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        return JSONObject.parseObject(httpsRequestString(requestUrl, requestMethod, outputStr));
    }

    public static String httpsRequestString(String requestUrl, String requestMethod) {
        return httpsRequestString(requestUrl, requestMethod, null);
    }

    public static String httpsRequestString(String requestUrl, String requestMethod, String outputStr) {
        String result = "";
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            TrustManager[] tm = { new JEEX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes(DEFAULT_CHARSET));
                outputStream.close();
            }
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, DEFAULT_CHARSET);
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            conn.disconnect();
            result = buffer.toString();
        }
        catch (Exception e) {
            log.error(e);
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (IOException ex) {
                //ex.printStackTrace();
            }
        }

        return result;
    }

    public static byte[] httpsRequestByte(String requestUrl, String requestMethod) {
        return httpsRequestByte(requestUrl, requestMethod, null);
    }

    public static byte[] httpsRequestByte(String requestUrl, String requestMethod, String outputStr) {
        try {
            TrustManager[] tm = { new JEEX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes(DEFAULT_CHARSET));
                outputStream.close();
            }
            InputStream inputStream = conn.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            if (inputStream != null) {
                while (-1 != (n = inputStream.read(buffer))) {
                    output.write(buffer, 0, n);
                }
                inputStream.close();
            }
            byte[] res = new byte[output.toByteArray().length];
            res = output.toByteArray();
            output.close();
            return res;
        }
        catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public static String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, DEFAULT_CHARSET);
        }
        catch (Exception e) {
            log.error(e);
        }
        return result;
    }

}

class JEEX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

}

class TrustAnyHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        // 直接返回true
        return true;
    }
}
