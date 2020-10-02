package com.sinosoft.sinoep.common.util;


import com.alibaba.dubbo.common.logger.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class HttpRequestToolsNew {
    Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public static String doGet(String url,String parmstr,String methd) throws IOException{
        String result = "";
        URL restURL = null;
        restURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        conn.setRequestMethod(methd);
        conn.setDoOutput(true);
        conn.setAllowUserInteraction(false);
        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(parmstr);
        ps.close();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        while(null !=(line =bReader.readLine())){
            result +=line;
        }
        bReader.close();
        return result;
    }

}
