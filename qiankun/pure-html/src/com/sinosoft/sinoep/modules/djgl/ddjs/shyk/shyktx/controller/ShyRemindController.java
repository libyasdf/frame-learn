package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.shyktx.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dkgl.entity.DkglEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dkgl.service.DkglService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.shyktx.service.ShykRemindService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * TODO 三会一课 党课Controller
 * @Author: 李帅
 * @Date: 2018/8/28 19:35
 */
@Controller
@RequestMapping("djgl/ddjs/shyk/shyktx/")
public class ShyRemindController {
//    private Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private ShykRemindService shykRemindService;
//
//    @RequestMapping("sendRemind")
//    @ResponseBody
//    public void sendRemind( DkglEntity dkglEntity) {
//        shykRemindService.sendRemind();
//    }
}
