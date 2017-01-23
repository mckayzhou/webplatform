/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/1/21 12:14
 * @version: V1.0
 */
package com.mckay.util;

/**
 * @Description :事件Id生成类，采用单例模式实现
 * @Author: 周林波
 * @Date :Created in 2017/1/21  12:14
 */
public class EventIdGenerator {

    /**
     * @param :
     * @return :
     * @description :采用double check设计
     */
    public EventIdGenerator getInstance() {
        EventIdGenerator generator = new EventIdGenerator();
        //TODO
        return generator;
    }
}
