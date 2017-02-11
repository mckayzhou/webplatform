/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/2/11 19:43
 * @version: V1.0
 */
package com.mckay.multiThread;

import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 *@Description :有些程序在通知时采用notifyAll,但是感觉条件设置不合理。
 * 如果有好几个get方法在等待，只是增加一个add方法时，notifyAll会出现什么情况？对此进行验证
 *@Author: 周林波
 *@Date :Created in 2017/2/11  19:43
 */
@Component
public class ThreadCommunication {

    private LinkedList<Integer> list=new LinkedList<>();

    public synchronized  void getElement(){
        while (list.size()==0){
            try {
                System.out.println("线程"+Thread.currentThread().getName()+"进入等待转态wait");
                wait();
                System.out.println("线程"+Thread.currentThread().getName()+"进入执行转态");
            }catch (Exception e){
                System.out.println("线程"+Thread.currentThread().getName()+"抛出异常信息："+e);
            }
        if (list.size()==0){
                System.out.println("线程"+Thread.currentThread().getName()+"没有获取到数据《《《《》》》》");
        }else {
            System.out.println("线程"+Thread.currentThread().getName()+"从List中取出的数为"+list.remove());
        }
        }
    }

    public synchronized  void setList(int element){
        list.add(element);
        notifyAll();
        System.out.println("已经插入数据");
    }
}
