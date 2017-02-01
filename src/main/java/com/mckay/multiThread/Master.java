/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/1/29 21:38
 * @version: V1.0
 */
package com.mckay.multiThread;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *@Description :多线程master-worker模式中的master
 *@Author: 周林波
 *@Date :Created in 2017/1/29  21:38
 */
public class Master {

        private Queue<Object> workQueue=new ConcurrentLinkedQueue<Object>();
        private Map<String,Thread> threadMap=new HashMap<String,Thread>();
        private Map<String,Object> result=new ConcurrentHashMap<String,Object>();

        //是否所有子任务都结束
        public boolean isComplete(){
            for (Map.Entry<String,Thread> entry :threadMap.entrySet()) {
                if (entry.getValue().getState()!=Thread.State.TERMINATED){
                    return  false;
                }
            }
            return  true;
        }

        //Master的构造，需要一个worker进程逻辑和需要的worker数量
        public Master(Worker worker, int workerNm){
            worker.setWorkQueue(workQueue);
            worker.setResult(result);
            for (int i=0; i<workerNm;i++) {
                threadMap.put(Integer.toString(i),new Thread(worker,Integer.toString(i)));
            }
        }

        //提交一个任务
        public void submit(Object job){
            workQueue.add(job);
        }

        //返回子任务结果集
        public Map<String, Object> getResult() {
            return result;
        }

        //运行worker线程处理任务
        public void execute(){
            for (Map.Entry<String,Thread> entry :threadMap.entrySet()){
                entry.getValue().start();
            }
        }

}
