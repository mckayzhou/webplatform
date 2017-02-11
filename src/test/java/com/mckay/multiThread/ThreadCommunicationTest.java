/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/2/11 19:52
 * @version: V1.0
 */
package com.mckay.multiThread;

import com.mckay.BaseJUnit4Test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *@Description :1、多个线程进入wait状态时，当被notifyAll()之后，会从wait之后开始执行，所以也要考虑异常情况的处理
 *                        2、在线程内部不能调用set方法也是奇怪？？后续查找原因
 *@Author: 周林波
 *@Date :Created in 2017/2/11  19:52
 */
public class ThreadCommunicationTest extends BaseJUnit4Test{

    @Autowired
    private ThreadCommunication threadCommunication;

    @Test
    public  void testThreadCommunication() {

        final ThreadCommunication com=new ThreadCommunication();
        for (int i=0;i<5;i++){
            new Thread(){
                public void run(){
                    threadCommunication.getElement();
                }
            }.start();
        }
        try {
            Thread.sleep(2000);
        }catch (Exception e){

        }
        threadCommunication.setList(1);
//        new Thread(){
//           threadCommunication.setList(1);
//        }.start();
    }
}
