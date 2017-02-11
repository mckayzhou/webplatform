/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/1/21 18:04
 * @version: V1.0
 */
package com.mckay.service;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Random;

/**
 *@Description :抢红包服务类
 *@Author: 周林波
 *@Date :Created in 2017/1/21  18:04
 */
@Component
public class RedPacketService {

    private double total;
    private int number;
    private DecimalFormat df;

    public RedPacketService(int money,int number ){
        this.total=money;
        this.number=number;
        df=new DecimalFormat("####0.00");
    }

    /**
    *@description :同步抢红包类，简单模拟抢红包。
    *@param :
    *@return :
    */
    public  synchronized String getRedPacket(){
        Random random=new Random();
        if (number==1){
            return df.format(total);
        }else{
            number--;
            int max=(int)(total*100)-number;//注意强制转换类型的位置，如果不是(int)(total*100)而是(int)total*100则会损失数据
            int min=1;
            int count= random.nextInt(max)%(max-min+1) + min;//获取位于min和max之间的随机数
            total=total-count/100.00;
            return  df.format(count/100.00);
        }
    }
}
