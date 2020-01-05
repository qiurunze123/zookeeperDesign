package com.zkdesign.zookeeper;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qiurunze
 **/
public class UserVo implements Serializable {

    Integer id;
    String name;
    Date birthDay;
    int port;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDay=" + birthDay +
                ", port=" + port +
                '}';
    }
}
