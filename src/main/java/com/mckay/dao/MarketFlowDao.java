/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/1/21 20:34
 * @version: V1.0
 */
package com.mckay.dao;

import com.mckay.entity.TblMarketFlowEntity;
import com.mckay.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@Description :交易市场流水表dao层，采用共享锁，排他锁和不加锁的方式实现
 *@Author: 周林波
 *@Date :Created in 2017/1/21  20:34
 */
@Repository
public class MarketFlowDao extends HibernateBaseDao<TblMarketFlowEntity, String> {

    /**
    *@description :使用共享锁
    *@param :
    *@return :
    */
    public TblMarketFlowEntity getFlowByShareLock(String transIdx){
        String hql="from TblMarketFlowEntity where trans_idx=?";
        List<TblMarketFlowEntity> list=  find(hql, transIdx);
        return  list.get(0);
    }


    /**
    *@description :使用排他锁
    *@param :
    *@return :
    */
    public TblMarketFlowEntity getFlowByXLock(String transIdx){
        String hql="from TblMarketFlowEntity where trans_idx=?";
        List<TblMarketFlowEntity> list=  find(hql, transIdx);
        return  list.get(0);
    }
}
