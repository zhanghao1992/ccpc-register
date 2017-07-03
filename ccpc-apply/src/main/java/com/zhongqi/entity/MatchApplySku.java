package com.zhongqi.entity;

/**
 * Created by songrenfei on 2017/7/3.
 */
public class MatchApplySku {

    private Integer id;

    private Integer matchDayId;

    private Integer matchPlaceId;

    private Integer totalPlayers;

    private Integer currentPlayers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(Integer totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public Integer getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(Integer currentPlayers) {
        this.currentPlayers = currentPlayers;
    }
}
