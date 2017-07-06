package com.zhongqi.dto;

import java.math.BigDecimal;

/**
 * Created by DELL on 2017/7/6.
 */
public class ResponsePersonRatingRankInfo {

    private String identityCardNumber;
    private String playerName;
    private BigDecimal goldenPoint;
    private BigDecimal silverPoint;
    private BigDecimal heartPoint;
    private Long createDatetime;
    private Long bindDateTime;
    private Integer goldenRank;
    private Integer silverRank;
    private Integer heartRank;
    private Integer gradeCode;

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public ResponsePersonRatingRankInfo setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
        return this;
    }

    public String getPlayerName() {
        return playerName;
    }

    public ResponsePersonRatingRankInfo setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public BigDecimal getGoldenPoint() {
        return goldenPoint;
    }

    public ResponsePersonRatingRankInfo setGoldenPoint(BigDecimal goldenPoint) {
        this.goldenPoint = goldenPoint;
        return this;
    }

    public BigDecimal getSilverPoint() {
        return silverPoint;
    }

    public ResponsePersonRatingRankInfo setSilverPoint(BigDecimal silverPoint) {
        this.silverPoint = silverPoint;
        return this;
    }

    public BigDecimal getHeartPoint() {
        return heartPoint;
    }

    public ResponsePersonRatingRankInfo setHeartPoint(BigDecimal heartPoint) {
        this.heartPoint = heartPoint;
        return this;
    }

    public Long getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Long createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getBindDateTime() {
        return bindDateTime;
    }

    public void setBindDateTime(Long bindDateTime) {
        this.bindDateTime = bindDateTime;
    }

    public Integer getGoldenRank() {
        return goldenRank;
    }

    public ResponsePersonRatingRankInfo setGoldenRank(Integer goldenRank) {
        this.goldenRank = goldenRank;
        return this;
    }

    public Integer getSilverRank() {
        return silverRank;
    }

    public ResponsePersonRatingRankInfo setSilverRank(Integer silverRank) {
        this.silverRank = silverRank;
        return this;
    }

    public Integer getHeartRank() {
        return heartRank;
    }

    public ResponsePersonRatingRankInfo setHeartRank(Integer heartRank) {
        this.heartRank = heartRank;
        return this;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public ResponsePersonRatingRankInfo setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
        return this;
    }


}
