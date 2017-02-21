/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/2/14 20:06
 * @version: V1.0
 */
package com.mckay;

import org.junit.Test;

/**
 *@Description :账深测试
 *@Author: 周林波
 *@Date :Created in 2017/2/14  20:06
 */
public class StackTest {

    private int count=0;

    public void recursion(){
        count++;
        recursion();
    }

    @Test
    public void test(){
        try {
            recursion();
        }catch (Throwable e){//Throwable和Exception为啥区别这么大？//FIXME
            System.out.println("栈深是:"+count);
        }
        System.out.println("最大内存是:"+Runtime.getRuntime().maxMemory()/1024/1024+"M");
    }
}
