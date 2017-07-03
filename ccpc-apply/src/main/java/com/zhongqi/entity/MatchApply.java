package com.zhongqi.entity;

import java.util.Date;

/**
 * Created by songrenfei on 2017/7/3.
 */
public class MatchApply {

    private Integer id;

    private String idNumber;

    private Integer matchDayId;

    private Integer matchPlaceId;

    private Date applyTime;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getMatchDayId() {
        return matchDayId;
    }

    public void setMatchDayId(Integer matchDayId) {
        this.matchDayId = matchDayId;
    }

    public Integer getMatchPlaceId() {
        return matchPlaceId;
    }

    public void setMatchPlaceId(Integer matchPlaceId) {
        this.matchPlaceId = matchPlaceId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
