package com.zhongqi.dto;


/**
 * Created by dell on 2016/4/28.
 */
public class ResponseRatingCollection extends ApiResponseResult {

    private int ranking;
    private String player_id;
    private String player_name;
    private String level_name;
    private String g;
    private String s;
    private String r;

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return "ResponseRatingCollection{" +
                "ranking=" + ranking +
                ", player_id='" + player_id + '\'' +
                ", player_name='" + player_name + '\'' +
                ", level_name='" + level_name + '\'' +
                ", g='" + g + '\'' +
                ", s='" + s + '\'' +
                ", r='" + r + '\'' +
                '}';
    }
}
