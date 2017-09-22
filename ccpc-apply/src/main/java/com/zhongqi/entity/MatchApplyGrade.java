package com.zhongqi.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ningcs on 2017/9/13.
 * 比赛成绩
 * 数据来源：导入
 */
public class MatchApplyGrade {
    private Integer id;
    private String identityCardNumber;
    private String playerName;
    private BigDecimal goldenPoint;
    private BigDecimal silverPoint;
    private BigDecimal heartPoint;
    private Date createDatetime;
    private Integer goldenRank;
    private Integer gradeCode;
    private String matchTime;
    private Integer matchType;
    private double bonus;

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

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

    public Integer getGoldenRank() {
        return goldenRank;
    }

    public void setGoldenRank(Integer goldenRank) {
        this.goldenRank = goldenRank;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public Integer getMatchType() {
        return matchType;
    }

    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
    }
}
