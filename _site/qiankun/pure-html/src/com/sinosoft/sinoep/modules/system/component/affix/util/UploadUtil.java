package com.sinosoft.sinoep.modules.system.component.affix.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class UploadUtil {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static final Map<String, String> mFileTypes = new HashMap<>();

	static {
		//第一次Helper. 的时候就已经执行了;下添加需重启
		mFileTypes.put("jpg","ffd8ff");
		mFileTypes.put("png","89504e");
		mFileTypes.put("gif","47494638");
		mFileTypes.put("bmp","424d");
		mFileTypes.put("psd", "384250");
		mFileTypes.put("rtf", "7b5c72");
		mFileTypes.put("xml", "3c3f78,fffe3c005200");
		mFileTypes.put("html", "68746d6c3e");
		mFileTypes.put("eml", "44656c69766572792d646174653a");
		mFileTypes.put("pdf", "255044");
		mFileTypes.put("docx", "504b3414,504b0304");
		mFileTypes.put("doc", "0d444f43,1234567890ff,31be000000ab0000,7ffe340a,d0cf11e0a1b11ae1");
		mFileTypes.put("xlsx", "504b3414,504b0304");
		mFileTypes.put("xls", "d0cf11,0904060000001000,0902060000001000");
		mFileTypes.put("rar", "526172");
		mFileTypes.put("avi", "41564920");
		mFileTypes.put("mp4", "000000186674");
	}

	/**
	 * 判断是否存在文件后缀篡改
	 * @return false 表都符合
	 */
	public static boolean isFileFalsify(List<MultipartFile> fileList)throws IOException {
		boolean bol=false;
		for(int i=0;i<fileList.size();i++){
			boolean retbol= isFileFalsify(fileList.get(i));
			if(retbol){
				bol=true;
				break;
			}
		}
		return bol;
	}
	/**
	 * 判断是否存在文件后缀篡改
	 * 二进制头与后缀对应关系 参考如下
	 *  https://blog.csdn.net/cosmoslife/article/details/43114323
	 *  https://blog.csdn.net/ccj2020/article/details/87603903
	 * @return true 存在篡改现象(mFileTypes集合里没有的也返回true);txt格式不进行比对返回true
	 * @return false 不是篡改,正常文件
	 */
	public static boolean isFileFalsify(MultipartFile file)throws IOException {
		boolean reaBol=true;
		//文件名称
		String fileNa=file.getOriginalFilename();
		//后缀
		String fileType=fileNa.substring(fileNa.lastIndexOf(".")+1);
		//后缀对应的流值
		if("txt".equals(fileType)){//txt文件不允许上传,使校验失败
			return true;
		}
		if(file.getSize()==0){//文件大小为0,放行
			return false;
		}
		String fileByte=mFileTypes.get(fileType);
		if (StringUtils.isBlank(fileByte)){
			return false;
		}
        String[] userIdArray = fileByte.split(",");
		List<String> fileByteList = Arrays.asList(userIdArray);
		byte[] digest=file.getBytes();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < digest.length&&i<50; i++) {
			sb.append(Integer.toHexString(((int) digest[i]) & 0xFF));
		}
		String fileCode =sb.toString();//获取到流字符
		for(String fileByteStr: fileByteList){
			int fileTypeLength =fileByteStr.length();//后缀流的长度
			String 	fileCodeStr=fileCode.substring(0,fileTypeLength).toLowerCase();//截取实际内容
			if(fileByteStr.equalsIgnoreCase(fileCodeStr)){
				reaBol=false;
				return reaBol;
			}
		}
		return reaBol;
	}

	
	public String uploadImage(HttpServletRequest request,String path_deposit,MultipartFile file,boolean isRandomName) {
        //上传
        try {
            if(file!=null){
				String origName=file.getOriginalFilename();// 文件原名称
	            //存放图片文件的路径
	            String path=path_deposit;
	            log.info("存放图片文件的路径"+path);
	            //组合名称
	            String fileSrc=""; 
	            //是否随机名称
	            if(isRandomName){
	                origName=UUID.randomUUID().toString()+origName.substring(origName.lastIndexOf("."));
	            }
	            //判断是否存在目录
	            File targetFile=new File(path,origName);
	            if(!targetFile.exists()){
	                targetFile.mkdirs();//创建目录
	            }
	            //上传
	            file.transferTo(targetFile);
	            //完整路径
	            //fileSrc=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+path_deposit+origName;
	            fileSrc = path+origName;
	            log.info("路径"+fileSrc);
	            return fileSrc;
            }
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            return null;
        }
    }

}
