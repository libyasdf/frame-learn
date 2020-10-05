package com.sinosoft.sinoep.wordTransfer.services;

import java.io.File;
import java.io.IOException;

import com.sinosoft.sinoep.wordTransfer.dao.FileResponsitory;
import com.sinosoft.sinoep.wordTransfer.entity.WordTransferType;
import com.sinosoft.sinoep.wordTransfer.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordTransferServiceImpl implements WordTransferService {

	@Autowired
	private FileResponsitory fileResponsitory;
	@Autowired
	private WordTransferClient wordTransferClient;
	
	@Override
	public File wordTransAffixFilePdf(String affixId) throws IOException {
		return getTargetFilePdf(fileResponsitory.getAffixContent(affixId));
	}



	public String wordTransAffixFilePathPdf(String affixId) throws IOException {
		File file = getTargetFilePdf(fileResponsitory.getAffixContent(affixId));
		return file.getPath().replaceAll("\\\\" , "/");
	}

	/**
	 * 根据doc或者docx源文件获取到转换后的文件（PDF文件）
	 * @param sourceFile 源文件
	 * @return pdf文件
	 * @throws IOException
	 */
	public File getTargetFilePdf(File sourceFile) throws IOException{
		File targetFile = FileUtils.findFileByCache(FileUtils.getMd5(sourceFile), String.valueOf(WordTransferType.PDF));
		if(targetFile == null){
			String fileName= sourceFile.getName();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if("pdf".equals(suffix.toLowerCase())){
				return FileUtils.fileToCache(sourceFile, FileUtils.getMd5(sourceFile));
			}else{
				File pdfFile = wordTransferClient.getFilePdf(sourceFile);
				return FileUtils.fileToCache(pdfFile, FileUtils.getMd5(sourceFile));
			}
		}else{
			return targetFile;
		}
	}
}
