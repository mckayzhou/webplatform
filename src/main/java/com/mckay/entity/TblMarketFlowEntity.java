/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/2/7 22:05
 * @version: V1.0
 */
package com.mckay.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 *@Description :TODO
 *@Author: 周林波
 *@Date :Created in 2017/2/7  22:05
 */
@Entity
@Table(name = "tbl_market_flow", schema = "mckay", catalog = "")
public class TblMarketFlowEntity {
    private String transIdx;
    private String transTp;
    private String marketCd;
    private String sessionNo;
    private String transDt;
    private Timestamp transTs;
    private String operIn;
    private String eventId;
    private String remark;
    private Timestamp recCrtTs;
    private String recCrtUsrId;

    @Id
    @Column(name = "trans_idx")
    public String getTransIdx() {
        return transIdx;
    }

    public void setTransIdx(String transIdx) {
        this.transIdx = transIdx;
    }

    @Basic
    @Column(name = "trans_tp")
    public String getTransTp() {
        return transTp;
    }

    public void setTransTp(String transTp) {
        this.transTp = transTp;
    }

    @Basic
    @Column(name = "market_cd")
    public String getMarketCd() {
        return marketCd;
    }

    public void setMarketCd(String marketCd) {
        this.marketCd = marketCd;
    }

    @Basic
    @Column(name = "session_no")
    public String getSessionNo() {
        return sessionNo;
    }

    public void setSessionNo(String sessionNo) {
        this.sessionNo = sessionNo;
    }

    @Basic
    @Column(name = "trans_dt")
    public String getTransDt() {
        return transDt;
    }

    public void setTransDt(String transDt) {
        this.transDt = transDt;
    }

    @Basic
    @Column(name = "trans_ts")
    public Timestamp getTransTs() {
        return transTs;
    }

    public void setTransTs(Timestamp transTs) {
        this.transTs = transTs;
    }

    @Basic
    @Column(name = "oper_in")
    public String getOperIn() {
        return operIn;
    }

    public void setOperIn(String operIn) {
        this.operIn = operIn;
    }

    @Basic
    @Column(name = "event_id")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "rec_crt_ts")
    public Timestamp getRecCrtTs() {
        return recCrtTs;
    }

    public void setRecCrtTs(Timestamp recCrtTs) {
        this.recCrtTs = recCrtTs;
    }

    @Basic
    @Column(name = "rec_crt_usr_id")
    public String getRecCrtUsrId() {
        return recCrtUsrId;
    }

    public void setRecCrtUsrId(String recCrtUsrId) {
        this.recCrtUsrId = recCrtUsrId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblMarketFlowEntity that = (TblMarketFlowEntity) o;

        if (transIdx != null ? !transIdx.equals(that.transIdx) : that.transIdx != null) return false;
        if (transTp != null ? !transTp.equals(that.transTp) : that.transTp != null) return false;
        if (marketCd != null ? !marketCd.equals(that.marketCd) : that.marketCd != null) return false;
        if (sessionNo != null ? !sessionNo.equals(that.sessionNo) : that.sessionNo != null) return false;
        if (transDt != null ? !transDt.equals(that.transDt) : that.transDt != null) return false;
        if (transTs != null ? !transTs.equals(that.transTs) : that.transTs != null) return false;
        if (operIn != null ? !operIn.equals(that.operIn) : that.operIn != null) return false;
        if (eventId != null ? !eventId.equals(that.eventId) : that.eventId != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (recCrtTs != null ? !recCrtTs.equals(that.recCrtTs) : that.recCrtTs != null) return false;
        if (recCrtUsrId != null ? !recCrtUsrId.equals(that.recCrtUsrId) : that.recCrtUsrId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = transIdx != null ? transIdx.hashCode() : 0;
        result = 31 * result + (transTp != null ? transTp.hashCode() : 0);
        result = 31 * result + (marketCd != null ? marketCd.hashCode() : 0);
        result = 31 * result + (sessionNo != null ? sessionNo.hashCode() : 0);
        result = 31 * result + (transDt != null ? transDt.hashCode() : 0);
        result = 31 * result + (transTs != null ? transTs.hashCode() : 0);
        result = 31 * result + (operIn != null ? operIn.hashCode() : 0);
        result = 31 * result + (eventId != null ? eventId.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (recCrtTs != null ? recCrtTs.hashCode() : 0);
        result = 31 * result + (recCrtUsrId != null ? recCrtUsrId.hashCode() : 0);
        return result;
    }
}
