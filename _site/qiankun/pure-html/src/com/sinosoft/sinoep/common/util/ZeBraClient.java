package com.sinosoft.sinoep.common.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

public class ZeBraClient {

    private static Logger log = LoggerFactory.getLogger(ZeBraClient.class);

    /**
     *调用打印机
     * @param printerName 打印机name,
     * @param printerIp 打印机关联ip
     * @param code  条形码
     * @param daCode 档案号
     * @return
     */
    public static JSONObject zeBraPrint(String printerName, String printerIp, String daCode, String code){
        JSONObject json = new JSONObject();
        json.put("flag",false);
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = null;
        Boolean flag = false;
        if(StringUtils.isNotBlank(printerName) && StringUtils.isNotBlank(printerIp)){
            try {
                printerName = URLEncoder.encode(printerName,"UTF-8");
                code = URLEncoder.encode(code,"UTF-8");
                daCode = URLEncoder.encode(daCode,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = "http://"+printerIp+":8081/ZeBraPrinting/ZeBraPrinting?printerName="+printerName+"&code="+code+"&daCode="+daCode;
            try{
                httpClient = HttpClients.createDefault();
                HttpGet get = new HttpGet(url);
                httpResponse = httpClient.execute(get);
                if(httpResponse !=null && httpResponse.getStatusLine().getStatusCode() ==200){
                    flag = true;
                }
                json.put("flag",flag);
                if(flag){
                    json.put("msg","调用打印机服务成功！");
                }else {
                    json.put("msg","调用打印机服务失败！");
                }
                return json;
            }catch (Exception e){
                json.put("msg","调用打印机服务失败！");
                log.error("调用打印机服务失败！",e);
                e.printStackTrace();
            }finally {
                try {
                    httpClient.close();
                    if(httpResponse !=null){
                        httpResponse.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            json.put("msg","打印机名称和打印机ip不能为空");
        }
        return json;
    }

    /**
     * 生成唯一条码：uuid生成的机制：uuid包括了网卡MAC地址、时间戳、名字控件、随机数等元素
     * @return
     */
    public static String getOrderIdByUUId(){
        int machineId= 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV <0 ){//有可能是负数
            hashCodeV = -  hashCodeV;
        }
        // 0 代表前面补充0
        // 15 代表长度为15
        // d 代表参数为正数型
        return machineId + String.format("%015d",hashCodeV);
    }
}
