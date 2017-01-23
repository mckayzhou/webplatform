/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/1/21 18:03
 * @version: V1.0
 */
package com.mckay.multiThread;

import com.mckay.service.RedPacketService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *@Description :模拟高并发抢红包的场景
 *@Author: 周林波
 *@Date :Created in 2017/1/21  18:03
 */
public class ConcurrentSimulate implements  Runnable{

    @Autowired
    private RedPacketService redPacketService;
    @Override
    public void run() {
        int money=redPacketService.getRedPacket();
        System.out.println("<<<<<<<<<you have get "+money+">>>>>>>>>>>>");
    }
}
