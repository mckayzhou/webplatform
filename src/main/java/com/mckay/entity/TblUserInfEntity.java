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
@Table(name = "tbl_user_inf", schema = "mckay", catalog = "")
public class TblUserInfEntity {
    private int id;
    private String name;
    private String password;
    private String status;
    private Timestamp recCrtTs;
    private Timestamp recUpdTs;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    @Column(name = "rec_upd_ts")
    public Timestamp getRecUpdTs() {
        return recUpdTs;
    }

    public void setRecUpdTs(Timestamp recUpdTs) {
        this.recUpdTs = recUpdTs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TblUserInfEntity that = (TblUserInfEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (recCrtTs != null ? !recCrtTs.equals(that.recCrtTs) : that.recCrtTs != null) return false;
        if (recUpdTs != null ? !recUpdTs.equals(that.recUpdTs) : that.recUpdTs != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (recCrtTs != null ? recCrtTs.hashCode() : 0);
        result = 31 * result + (recUpdTs != null ? recUpdTs.hashCode() : 0);
        return result;
    }
}
