/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/1/21 20:18
 * @version: V1.0
 */
package com.mckay.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 *@Description :TODO
 *@Author: 周林波
 *@Date :Created in 2017/1/21  20:18
 */
public class PersonEntityPK implements Serializable {
    private int number;
    private long salary;

    @Column(name = "number")
    @Id
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Column(name = "salary")
    @Id
    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonEntityPK that = (PersonEntityPK) o;

        if (number != that.number) return false;
        if (salary != that.salary) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (int) (salary ^ (salary >>> 32));
        return result;
    }
}
