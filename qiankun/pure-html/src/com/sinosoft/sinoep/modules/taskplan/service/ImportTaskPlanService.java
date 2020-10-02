package com.sinosoft.sinoep.modules.taskplan.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImportTaskPlanService {

    String importInfo(String filePath, MultipartFile file) throws IOException;
}
