package com.sinosoft.sinoep.wordTransfer.services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WordTransferClient {

	private static Logger log = LoggerFactory.getLogger(WordTransferClient.class);

	public File getFilePdf (File file){
		CloseableHttpResponse httpResponse = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			/*HttpPost post = new HttpPost("http://172.21.88.2:8100/spring/mobileoa/word-transfer/pdf");*/
			HttpPost post = new HttpPost(ConfigConsts.TRANSFER_DEVICE_URL + "/pdf");
			post.setEntity(MultipartEntityBuilder.create().addBinaryBody("file", file,
					ContentType.create("application/msword",Charset.forName("utf-8")),
					file.getName().substring(file.getName().indexOf(".")).equals(".doc") ? "word.doc" : "word.docx").build());
			httpResponse = httpclient.execute(post);
			HttpEntity entity = httpResponse.getEntity();
			if(entity != null) {
				File targetFile = File.createTempFile(UUID.randomUUID().toString(), ".pdf");
				FileUtils.copyInputStreamToFile(entity.getContent(), targetFile);
				return targetFile;
			}
		} catch (IOException e) {
			 log.error("拷贝正文到文件失败", e);
	         throw new RuntimeException("拷贝正文到文件失败", e);
		} finally {
            IOUtils.closeQuietly(httpResponse);
        }
		return null;
	}
	
	public static void main(String[] args) {
			CloseableHttpResponse httpResponse = null;
			try {
				File file = new File("F:\\11.docx");
				CloseableHttpClient httpclient = HttpClients.createDefault();
				/*HttpPost post = new HttpPost("http://172.21.88.2:8100/spring/mobileoa/word-transfer/pdf");*/
				HttpPost post = new HttpPost("http://localhost:8100/mobileoa/word-transfer/pdf");
				System.out.println("待转换文件格式:"+file.getName().substring(file.getName().indexOf(".")));
				post.setEntity(MultipartEntityBuilder.create().addBinaryBody("file", file,
						ContentType.create("application/msword",Charset.forName("utf-8")),
						file.getName().substring(file.getName().indexOf(".")).equals(".doc") ? "word.doc" : "word.docx").build());
				httpResponse = httpclient.execute(post);
				HttpEntity entity = httpResponse.getEntity();
				if(entity != null) {
					File targetFile = new File("F:\\11.pdf");
					FileUtils.copyInputStreamToFile(entity.getContent(), targetFile);
				}
			} catch (IOException e) {
				 log.error("转换文件内容为pdf失败", e);
		         throw new RuntimeException("转换文件内容为pdf失败", e);
			} finally {
	            IOUtils.closeQuietly(httpResponse);
	        }
	}
}