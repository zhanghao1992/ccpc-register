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
//    private Integer gradeCode;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public PersonRatingRank setId(Integer id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "identityCardNumber", nullable = true)
    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public PersonRatingRank setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
        return this;
    }

    @Basic
    @Column(name = "playerName", nullable = true)
    public String getPlayerName() {
        return playerName;
    }

    public PersonRatingRank setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    @Basic
    @Column(name = "goldenPoint", nullable = true)
    public BigDecimal getGoldenPoint() {
        return goldenPoint;
    }

    public PersonRatingRank setGoldenPoint(BigDecimal goldenPoint) {
        this.goldenPoint = goldenPoint;
        return this;
    }

    @Basic
    @Column(name = "silverPoint", nullable = true)
    public BigDecimal getSilverPoint() {
        return silverPoint;
    }

    public PersonRatingRank setSilverPoint(BigDecimal silverPoint) {
        this.silverPoint = silverPoint;
        return this;
    }

    @Basic
    @Column(name = "heartPoint", nullable = true)
    public BigDecimal getHeartPoint() {
        return heartPoint;
    }

    public PersonRatingRank setHeartPoint(BigDecimal heartPoint) {
        this.heartPoint = heartPoint;
        return this;
    }

    @Basic
    @Column(name = "createDatetime", nullable = true)
    public Date getCreateDatetime() {
        return createDatetime;
    }

    public PersonRatingRank setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    @Basic
    @Column(name = "bindDateTime", nullable = true)
    public Date getBindDateTime() {
        return bindDateTime;
    }

    public PersonRatingRank setBindDateTime(Date bindDateTime) {
        this.bindDateTime = bindDateTime;
        return this;
    }

    @Basic
    @Column(name = "goldenRank", nullable = true)
    public Integer getGoldenRank() {
        return goldenRank;
    }

    public PersonRatingRank setGoldenRank(Integer goldenRank) {
        this.goldenRank = goldenRank;
        return this;
    }

    @Basic
    @Column(name = "silverRank", nullable = true)
    public Integer getSilverRank() {
        return silverRank;
    }

    public PersonRatingRank setSilverRank(Integer silverRank) {
        this.silverRank = silverRank;
        return this;
    }

    @Basic
    @Column(name = "heartRank", nullable = true)
    public Integer getHeartRank() {
        return heartRank;
    }

    public PersonRatingRank setHeartRank(Integer heartRank) {
        this.heartRank = heartRank;
        return this;
    }
//    @Basic
//    @Column(name = "GradeCode", nullable = true)
//    public Integer getGradeCode() {
//        return gradeCode;
//    }
//
//    public void setGradeCode(Integer gradeCode) {
//        this.gradeCode = gradeCode;
//    }
}