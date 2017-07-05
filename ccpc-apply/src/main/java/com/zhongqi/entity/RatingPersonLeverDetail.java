package com.zhongqi.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by ningcs on 2017/7/5.
 * 棋手等级
 */
@Entity
@Table(name = "RatingPersonLeverDetail")
public class RatingPersonLeverDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "levelName", nullable = true)
    private String levelName;

    @Column(name = "golden", nullable = true)
    private BigDecimal golden;

    @Column(name = "silver", nullable = true)
    private BigDecimal silver;

    @Column(name = "heart", nullable = true)
    private BigDecimal heart;

    @Column(name = "gradeCode", nullable = true)
    private Integer gradeCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public BigDecimal getGolden() {
        return golden;
    }

    public void setGolden(BigDecimal golden) {
        this.golden = golden;
    }

    public BigDecimal getSilver() {
        return silver;
    }

    public void setSilver(BigDecimal silver) {
        this.silver = silver;
    }

    public BigDecimal getHeart() {
        return heart;
    }

    public void setHeart(BigDecimal heart) {
        this.heart = heart;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }
}
