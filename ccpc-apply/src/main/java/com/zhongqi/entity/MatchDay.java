package com.zhongqi.entity;

import javax.persistence.*;

/**
 * Created by songrenfei on 2017/7/3.
 */
@Entity
@Table(name = "MatchDay")
public class MatchDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dayInfo")
    private String dayInfo;

    @Column(name = "dayInfoDetail")
    private String dayInfoDetail;

    @Column(name = "status")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDayInfo() {
        return dayInfo;
    }

    public void setDayInfo(String dayInfo) {
        this.dayInfo = dayInfo;
    }

    public String getDayInfoDetail() {
        return dayInfoDetail;
    }

    public void setDayInfoDetail(String dayInfoDetail) {
        this.dayInfoDetail = dayInfoDetail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
