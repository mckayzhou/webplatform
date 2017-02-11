/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/2/11 12:24
 * @version: V1.0
 */
package com.mckay.multiThread;

import com.mckay.service.RedPacketService;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *@Description :模拟微信多人抢红包的场景
 *@Author: 周林波
 *@Date :Created in 2017/2/11  12:24
 */
public class multiRedPacketTest {

    private DecimalFormat df=new DecimalFormat("####0.00");

    @Test
    public void  test(){

        int money=10;
        int number=5;
        final RedPacketService redPacketService=new RedPacketService(money,number);
        final Collection<String> coll=new CopyOnWriteArrayList<String>();
        for (int i=0;i<number;i++){
            new Thread(){
                public void run(){
                    String money=redPacketService.getRedPacket();
                    System.out.println("恭喜你！抢到红包"+money+"元");
                    coll.add(money);
                }
            }.start();
        }
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }
        Iterator<String> iter=coll.iterator();
        double count=0;
        while (iter.hasNext()){
            String mon=iter.next();
            count=count+Double.valueOf(mon);
        }
        System.out.println("合计"+df.format(count)+"元");
    }
}
