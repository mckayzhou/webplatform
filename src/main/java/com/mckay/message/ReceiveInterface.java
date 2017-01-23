/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author：周林波
 * @date: 2017/1/21 10:46
 * @version: V1.0
 */
package com.mckay.message;

import java.util.HashMap;

/**
 * @Description:TODO
 * @Date Created in 2017/1/21  10:46
 */
public class ReceiveInterface {

    /**
     * @Description:接收消息，进行异步处理，根据消息不同，调用不同的服务
     * @Param:
     * @Return:
     */
    public String msgHandler() {
        String msg = "msg had received! ";
        msgDeal();
        return msg;
    }

    /**
     * @Description:用于处理消息的线程
     * @Param:
     * @Return:
     */
    private void msgDeal() {

    }

}
