/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/2/10 18:23
 * @version: V1.0
 */
package com.mckay;

import com.mckay.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *@Description :Spring测试基类
 *@Author: 周林波
 *@Date :Created in 2017/2/10  18:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:db.context.xml"})
public class BaseJUnit4Test {

    @Autowired
    private UserDao userDao;

    @Test
    public void testBaseJUnit4Test(){
        System.out.println(userDao.getAllUser().get(0).getName());
    }
}
