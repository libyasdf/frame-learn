package com.sinosoft.sinoep.common.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 公共工具类
 */
public class CommonUtils {

    /**
     * TODO 分割以','拼接的字符串，并重新组装成'123','234','345'，用于多选删除
     * @author 周俊林
     * @Date 2018年3月16日 上午10:17:37
     * @param originStr
     * @return 如果{originStr}为空或空字符串，则返回null
     */
    public static String commomSplit(String originStr){
        if (StringUtils.isBlank(originStr)) {
            return null;
        }
        String[] strArray = originStr.split(",");
        StringBuffer strb = new StringBuffer();
        for(int i = 0; i < strArray.length; i++){
            strb.append("'"+strArray[i]+"',");
        }
        strb.deleteCharAt(strb.length()-1);
        return strb.toString();
    }

    /**
     * TODO 将InputStream转换到OutputStream
     * @author 周俊林
     * @Date 2018年3月16日 上午10:16:12
     * @param input
     * @param output
     * @throws IOException
     */
    public static void copyStream (InputStream input, OutputStream output) throws IOException {
        try {
            try {
                org.apache.commons.io.IOUtils.copy(input, output);
            } finally {
                org.apache.commons.io.IOUtils.closeQuietly(output);
            }
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * 获取客户端ip地址
     * @return
     */
    public static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
    /**
     * TODO 将List集合转为字符串(以逗号分隔)
     * @author 李利广
     * @Date 2019年03月13日 12:08:52
     * @param list
     * @return java.lang.String
     */
    public static String arrayToString(List<String> list){
        return arrayToString(list,",");
    }


    /**
     * TODO 将List集合转为字符串(以指定分隔符分隔)
     * @author 李利广
     * @Date 2019年03月13日 12:08:52
     * @param list
     * @param separator 分隔符，默认为逗号分隔
     * @return java.lang.String
     */
    public static String arrayToString(List<String> list,String separator ){
        StringBuilder stringBuilder = new StringBuilder();
        String str = "";
        if (StringUtils.isBlank(separator)){
            separator = ",";
        }
        if(!list.isEmpty()){
            for (String content:list ) {
                stringBuilder.append(content).append(separator);
            }
        }
        if (stringBuilder.length() > 0){
            str = stringBuilder.substring(0,stringBuilder.length() - separator.length());
        }
        return str;
    }
    /**
     * @Author 苑高川
     * @Description: 解决sql注入in的方法
     * @param paraString “,”分割的参数
     * @param paraList 已有的参数list
     * @Date: 2019/4/13 16:24
     */
    public static String solveSqlInjectionOfIn (String paraString, List<Object> paraList) {
        String inString = "";
        if (paraString != null){
        	//防止从前端传过来的带着单引号：'1','2','3' 王磊 20190426
            String[] paraArray = paraString.replaceAll("'", "").split(",");
            for (int i = 0; i < paraArray.length; i++) {
                if (i == 0) {
                    inString += "?";
                } else {
                    inString += ",?";
                }
                paraList.add(paraArray[i]);
            }
        }
        return inString;
    }
}
