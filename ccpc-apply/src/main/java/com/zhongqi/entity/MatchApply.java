package com.zhongqi.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by songrenfei on 2017/7/3.
 */
@Entity
@Table(name = "MatchApply")
public class MatchApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cpIdCode")
    private String  cpIdCode;

    @Column(name = "idNumber")
    private String idNumber;

    @Column(name = "matchDayId")
    private Integer matchDayId;

    @Column(name = "matchPlaceId")
    private Integer matchPlaceId;

    @Column(name = "applyTime")
    private Date applyTime;

    @Column(name = "status")
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

    public String getCpIdCode() {
        return cpIdCode;
    }

    public void setCpIdCode(String cpIdCode) {
        this.cpIdCode = cpIdCode;
    }
}
