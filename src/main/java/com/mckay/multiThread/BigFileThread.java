/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author：周林波
 * @date: 2017/1/21 11:34
 * @version: V1.0
 */
package com.mckay.multiThread;

import java.io.OutputStream;

/**
 * @Description:TODO
 * @Date Created in 2017/1/21  11:34
 */
public class BigFileThread implements Runnable {

    private int start ;
    private int end;
    private OutputStream out;
    public BigFileThread(int start , int end , OutputStream out) {
        this.start=start;
        this.end=end;
        this.out=out;
    }

    @Override
    public void run() {

    }
}
