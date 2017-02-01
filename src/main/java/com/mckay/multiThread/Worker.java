/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/1/29 20:30
 * @version: V1.0
 */
package com.mckay.multiThread;

import java.util.Map;
import java.util.Queue;

/**
 *@Description :master-worker模式中的worker
 *@Author: 周林波
 *@Date :Created in 2017/1/29  20:30
 */
public class Worker  implements  Runnable{

    private Queue<Object> workQueue;//

    private Map<String ,Object> result;//结果集

    public void setWorkQueue(Queue<Object> workQueue){
            this.workQueue=workQueue;
    }

    public  void setResult(Map<String ,Object> result){
            this.result=result;
    }

    public Object handle(Object  work){
        return  work;
    }
    @Override
    public void run() {
            while (true){
                Object work=workQueue.poll();
                if (work==null)
                    break;
                Object res=handle(work);
                result.put(Integer.toString(work.hashCode()),res);
            }
    }
}
