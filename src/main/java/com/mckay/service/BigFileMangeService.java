/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Title: BigFileMangeService.java
 * @Prject: webplatform
 * @Package: com.service
 * @Description:
 * @author:
 * @date: 2017年1月13日 下午10:26:03
 * @version: V1.0
 */
package com.mckay.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;


/**
 * @ClassName: BigFileMangeService
 * @Description: TODO
 * @author: 周林波
 * @date: 2017年1月13日 下午10:26:03  
 */
@Service

public class BigFileMangeService {

    private Logger log = Logger.getLogger(BigFileMangeService.class);

    /**
     *@Description:使用多线程来处理文件
     *@Param:
     *@Return:
     */
    public boolean dealFileUseThread(String filePath) {

        File file = new File(filePath);
        return true;
    }

    /**
     *@Description:
     *@Param:
     *@Return:
     */
    private void dealThread() {

    }
}
