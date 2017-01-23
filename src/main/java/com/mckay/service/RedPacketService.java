/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/1/21 18:04
 * @version: V1.0
 */
package com.mckay.service;

import java.util.Random;

/**
 *@Description :抢红包服务类
 *@Author: 周林波
 *@Date :Created in 2017/1/21  18:04
 */
public class RedPacketService {

    private static  int total=1000;

    public  synchronized int getRedPacket(){
        Random random=new Random();
        int count=random.nextInt();
        total=total-count;
        return  count;
    }
}
