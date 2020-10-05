package com.sinosoft.sinoep.wordTransfer.controller;

import org.apache.commons.io.FileUtils;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


public class WebUtils {

	public static void responsePdf(HttpServletResponse response, File file)throws IOException {
		response.setContentType("application/pdf");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Length", String.valueOf(file.length()));
		try {
			ServletOutputStream out = response.getOutputStream();
			FileUtils.copyFile(file,out);
			out.flush();
			out.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
}
