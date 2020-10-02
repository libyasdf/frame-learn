package com.sinosoft.sinoep.wordTransfer.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * 文件处理的工具类，缓存中文件的获取，存储；获取文件的MD5值
 */
public class FileUtils {

	//private static String CACHE_PATH ="/opt/CVICSE/cqqxcs/forLogin/index_templet/info/pdfAndswfFile/";
	//private static String CACHE_PATH ="D:/sjxm/web/forLogin/index_templet/info/pdfAndswfFile/";
//    private static String CACHE_PATH = "E:/tjxm/classes/artifacts/tjxm_war_exploded/upload/";
	public static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	 /**
     * 获取文件的MD5值
     * @param file 文件
     * @return 文件的MD5值ֵ
     */
    public static String getMd5(File file) {
        BufferedInputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file));
            return DigestUtils.md5Hex(input);
        } catch (IOException e) {
            logger.error("md5值获取失败", e);
            throw new RuntimeException("md5值获取失败", e);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * 根据type类型获取指定文件
     * @param sourceFileMD5 源文件MD5值
     * @param type 文件类型，如pdf,html
     * @return 返回要获取的文件，如果文件不存在，返回null
     */
    public static File findFileByCache(String sourceFileMD5, String type){
//        String path = CACHE_PATH + sourceFileMD5.substring(0,1) + "/" + sourceFileMD5.substring(1,3);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String path = request.getRealPath("/") + "upload" + sourceFileMD5.substring(0,1) + "/" + sourceFileMD5.substring(1,3);
        String fileName = sourceFileMD5 + "." + type.toLowerCase();
        File targetFile = new File(path, fileName);

        if (targetFile.exists() && targetFile.length() > 0) {
            return targetFile;
        }
        return null;
    }

    /**
     *  缓存文件
     * @param file 需要缓存的文件
     * @param sourceFileMD5 源文件doc或者docx的md5值
     * @return 返回已经缓存的文件
     * @throws IOException
     */
    public static File fileToCache(File file, String sourceFileMD5) throws IOException {
//        String path = CACHE_PATH + sourceFileMD5.substring(0,1) + "/" + sourceFileMD5.substring(1,3);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String path = request.getRealPath("/") + "upload" + sourceFileMD5.substring(0,1) + "/" + sourceFileMD5.substring(1,3);
        String fileName = sourceFileMD5 + file.getName().substring(file.getName().indexOf("."));
        File targetFile = new File(path, fileName);
        org.apache.commons.io.FileUtils.copyFile(file, targetFile);
        return targetFile;
    }
}
