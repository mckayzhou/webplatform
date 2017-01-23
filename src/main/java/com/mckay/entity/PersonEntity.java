/**
 * Copyright © 2017 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Author: 周林波
 * @date: 2017/1/21 20:18
 * @version: V1.0
 */
package com.mckay.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 *@Description :TODO
 *@Author: 周林波
 *@Date :Created in 2017/1/21  20:18
 */
@Entity
@Table(name = "person", schema = "mckay", catalog = "")
@IdClass(PersonEntityPK.class)
public class PersonEntity {
    private int number;
    private String name;
    private Date birthday;
    private long salary;

    @Id
    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Id
    @Column(name = "salary")
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

        PersonEntity that = (PersonEntity) o;

        if (number != that.number) return false;
        if (salary != that.salary) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (int) (salary ^ (salary >>> 32));
        return result;
    }
}
