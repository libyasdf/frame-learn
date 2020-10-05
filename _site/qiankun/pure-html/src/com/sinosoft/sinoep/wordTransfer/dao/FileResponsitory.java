package com.sinosoft.sinoep.wordTransfer.dao;

import java.io.File;

public interface FileResponsitory {

	/**
	 * 获取到doc文件，将获取到数据写入到输出流中
	 * @param affixId 附件id
	 * @return File 返回文件
	 */
	File getAffixContent(String affixId);
}
