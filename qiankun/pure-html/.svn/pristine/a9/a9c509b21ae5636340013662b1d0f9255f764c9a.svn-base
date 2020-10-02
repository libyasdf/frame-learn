package com.sinosoft.sinoep.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 * @ClassName: PropOperate
 * @Description:加载XML中配置的需要过滤器过滤的URL地址
 * @author kjx
 * @date 2016-3-31 上午02:36:28
 * @version V1.0
 */
public class PropOperate {

	public PropOperate(String filePath) {
		this.filePath = filePath;
		this.filterUrlList = new ArrayList<>();
		this.noFilterUrlList = new ArrayList<>();
		this.authUrlList = new ArrayList<>();
        this.noXssFilterList = new ArrayList<>();
		writeCache();
	}

	private List<String> filterUrlList;
	private List<String> noFilterUrlList;
	private List<String> authUrlList;
    private List<String> noXssFilterList;

	private String filePath = "";

	public List<String> getFilterUrlList() {
		return this.filterUrlList;
	}

	public List<String> getNoFilterUrlList() {
		return this.noFilterUrlList;
	}

	public List<String> getAuthUrlList() {
		return this.authUrlList;
	}

    public List<String> getNoXssFilterList() {
        return noXssFilterList;
    }

    public void writeCache() {
		try {
			javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			javax.xml.parsers.DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(filePath));
			getPropUrl(doc, "filter", filterUrlList);
			getPropUrl(doc, "noFilter", noFilterUrlList);
			getPropUrl(doc, "auth", authUrlList);
            getPropUrl(doc, "noXssFilter", noXssFilterList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getPropUrl(Document doc, String strNodeName, List<String> url) {
		NodeList list = doc.getDocumentElement().getElementsByTagName(strNodeName);
		for (int i = 0; i < list.getLength(); i++) {
			Element node = (Element) list.item(i);
			NodeList propNodeList = node.getElementsByTagName("url");
			for (int t = 0; t < propNodeList.getLength(); t++) {
				node = (Element) propNodeList.item(t);
				url.add(node.getChildNodes().item(0).getNodeValue().trim());
			}
		}

	}

	public static void main(String[] args) {
		try {
			String fileUri = PropOperate.class.getClass().getResource("/").getPath();
			System.out.println(fileUri);
			PropOperate pro = new PropOperate(fileUri + "/filterUrl.xml");
			System.out.println("filter>>>>");
			List<String> url = pro.getFilterUrlList();
			for (String u : url) {
				System.out.println(u);
			}
			System.out.println("noFilter>>>>");
			List<String> nourl = pro.getNoFilterUrlList();
			for (String u : nourl) {
				System.out.println(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
