package com.zhongqi.dto;

import java.math.BigDecimal;

/**
 * Created by ningcs on 2017/7/5.
 */
public class ResponseRatingForQueryInfo {
    private String player_name;
    private Integer ranking;
    private String level_name;
    private BigDecimal g;
    private BigDecimal s;
    private BigDecimal r;
    private BigDecimal g_r;
    private BigDecimal s_r;
    private BigDecimal r_r;

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public BigDecimal getG() {
        return g;
    }

    public void setG(BigDecimal g) {
        this.g = g;
    }

    public BigDecimal getS() {
        return s;
    }

    public void setS(BigDecimal s) {
        this.s = s;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }

    public BigDecimal getG_r() {
        return g_r;
    }

    public void setG_r(BigDecimal g_r) {
        this.g_r = g_r;
    }

    public BigDecimal getS_r() {
        return s_r;
    }

    public void setS_r(BigDecimal s_r) {
        this.s_r = s_r;
    }

    public BigDecimal getR_r() {
        return r_r;
    }

    public void setR_r(BigDecimal r_r) {
        this.r_r = r_r;
    }
}
