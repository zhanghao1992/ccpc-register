package com.zhongqi.model;

/**
 * Created by ningcs on 2017/7/4.
 */
public class MatchApplySkuInfo {

    private Integer matchDayId;

    private Integer matchPlaceId;

    private Integer  remainPlayers;

    private String place;

    private String placeDetail;


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

    public Integer getRemainPlayers() {
        return remainPlayers;
    }

    public void setRemainPlayers(Integer remainPlayers) {
        this.remainPlayers = remainPlayers;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlaceDetail() {
        return placeDetail;
    }

    public void setPlaceDetail(String placeDetail) {
        this.placeDetail = placeDetail;
    }
}
