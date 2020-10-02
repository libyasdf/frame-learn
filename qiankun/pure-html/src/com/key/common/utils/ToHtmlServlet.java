package com.key.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * jsp转换html服务
 * TODO 
 * @author 李利广
 * @Date 2018年9月27日 下午4:36:50
 */
public class ToHtmlServlet extends HttpServlet {
	
	private static final long serialVersionUID = -9118659583515649615L;
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = getServletContext();
		String url=request.getParameter("url");
		String filePath=request.getParameter("filePath");
		String fileName=request.getParameter("fileName");
		//url = "/design/my-survey-design!previewDev.action?surveyId=402880ea4675ac62014675ac7b3a0000";
		// 这是生成的html文件名,如index.htm
		filePath = filePath.replace("/", File.separator);
		filePath = filePath.replace("\\", File.separator);
		String fileRealPath = sc.getRealPath("/") +File.separator+ filePath;

		RequestDispatcher rd = sc.getRequestDispatcher(url);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final ServletOutputStream stream = new ServletOutputStream() {
			public void write(byte[] data, int offset, int length) {
				os.write(data, offset, length);
			}

			public void write(int b) throws IOException {
				os.write(b);
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setWriteListener(WriteListener arg0) {
				// TODO Auto-generated method stub
				
			}

		};

		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"UTF-8"));
		HttpServletResponse rep = new HttpServletResponseWrapper(response) {
			public ServletOutputStream getOutputStream() {
				return stream;
			}

			public PrintWriter getWriter() {
				return pw;
			}

		};

		//rd.include(request, rep);
		rd.forward(request, rep);
		pw.flush();
		try {
			flushDo(fileRealPath,fileName,os);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<p align=center><font size=3 color=red>首页已经成功生成！Andrew</font></p>");
	}

	private void flushDo(String fileRealPath,String fileName,final ByteArrayOutputStream os) throws Exception{
		Document document = Jsoup.parse(os.toString("UTF-8"),"UTF-8");
		String contentCopyrightStr = "";// 自定义问卷内容的版权，可以在设置中设置名称，然后就自动显示
		Elements isElements = document.getElementsByAttributeValue("href",new StringBuffer("ten.newoaid.www//:ptth").reverse().toString());
		if (isElements==null){
			if(fileName.startsWith("m_")){
				Elements elements = document.getElementsByAttributeValue("data-role","footer");
				if(elements==null){
					elements = document.getElementsByAttributeValue("data-role","page");
					if(elements!=null){
						elements.last().append(new StringBuffer(">vid/<>3h/<>a/<yevruSwD>\"lanretxe\"=ler \";enon :noitaroced-txet\"=elyts \"psj.m-xedni/ten.newoaid//:ptth\"=ferh a< yb derewoP>3h<>\"egap\"=elor-atad vid<\n").reverse().toString());
					}
				}else{
					elements.html(new StringBuffer(">3h/<>a/<yevruSwD>\"lanretxe\"=ler \";enon :noitaroced-txet\"=elyts \"psj.m-xedni/ten.newoaid//:ptth\"=ferh a< yb derewoP>3h<").reverse().toString());
				}
			}else{
				Elements elements = document.getElementsByClass("footer-pb");
				if(elements!=null) elements.remove();
				document.body().append(new StringBuffer(";psbn&>a/<yevruSwD>\";yarg :roloc;enon :noitaroced-txet\"=elyts \"ten.newoaid.www//:ptth\"=ferh a<  yb derewoP  >\";xp5 :mottob-gniddap;yarg :roloc\"=elyts \"retoof\"=elor-atad \"thgirypoc-retoof\"=ssalc vid<").reverse().toString() + contentCopyrightStr + " </div>");
			}
		}
		printStream(fileRealPath,fileName,document.html());
	}

	public void printStream(String savePath,String fileName,String content) throws IOException{
		createFile(savePath);
		FileOutputStream out=null;
		OutputStreamWriter osw = null;
		try {
			out=new FileOutputStream(savePath+File.separator+fileName);
			osw = new OutputStreamWriter(out,"UTF-8");
			osw.write(content);
			osw.close();
		}catch (Exception e){
			e.printStackTrace();;
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void createFile(String rootPath) {
		File file=new File(rootPath);
		if(!file.exists()){
			file.mkdirs();
		}
	}


}