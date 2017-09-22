package com.zhongqi.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ningcs on 2017/7/5.
 */
@Entity
@Table(name = "PersonRatingRank")
public class PersonRatingRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "identityCardNumber", nullable = true)
    private String identityCardNumber;

    @Column(name = "playerName", nullable = true)
    private String playerName;

    @Column(name = "goldenPoint", nullable = true)
    private BigDecimal goldenPoint;

    @Column(name = "silverPoint", nullable = true)
    private BigDecimal silverPoint;

    @Column(name = "heartPoint", nullable = true)
    private BigDecimal heartPoint;

    @Column(name = "createDatetime", nullable = true)
    private Date createDatetime;

    @Column(name = "bindDateTime", nullable = true)
    private Date bindDateTime;

    @Column(name = "goldenRank", nullable = true)
    private Integer goldenRank;

    @Column(name = "silverRank", nullable = true)
    private Integer silverRank;

    @Column(name = "heartRank", nullable = true)
    private Integer heartRank;


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
}