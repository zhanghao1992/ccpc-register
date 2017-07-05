package com.zhongqi.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ningcs on 2017/7/5.
 */
public class PersonRatingRankInfo {
    private Integer id;
    private String identityCardNumber;
    private String playerName;
    private BigDecimal goldenPoint;
    private BigDecimal silverPoint;
    private BigDecimal heartPoint;
    private Date createDatetime;
    private Date bindDateTime;
    private Integer goldenRank;
    private Integer silverRank;
    private Integer heartRank;
    private Integer gradeCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public BigDecimal getGoldenPoint() {
        return goldenPoint;
    }

    public void setGoldenPoint(BigDecimal goldenPoint) {
        this.goldenPoint = goldenPoint;
    }

    public BigDecimal getSilverPoint() {
        return silverPoint;
    }

    public void setSilverPoint(BigDecimal silverPoint) {
        this.silverPoint = silverPoint;
    }

    public BigDecimal getHeartPoint() {
        return heartPoint;
    }

    public void setHeartPoint(BigDecimal heartPoint) {
        this.heartPoint = heartPoint;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getBindDateTime() {
        return bindDateTime;
    }

    public void setBindDateTime(Date bindDateTime) {
        this.bindDateTime = bindDateTime;
    }

    public Integer getGoldenRank() {
        return goldenRank;
    }

    public void setGoldenRank(Integer goldenRank) {
        this.goldenRank = goldenRank;
    }

    public Integer getSilverRank() {
        return silverRank;
    }

    public void setSilverRank(Integer silverRank) {
        this.silverRank = silverRank;
    }

    public Integer getHeartRank() {
        return heartRank;
    }

    public void setHeartRank(Integer heartRank) {
        this.heartRank = heartRank;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }
}
