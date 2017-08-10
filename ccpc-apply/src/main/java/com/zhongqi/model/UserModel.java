package com.zhongqi.model;

import java.util.Date;

/**
 * Created by ningcs on 2017/7/5.
 */
public class UserModel {
    private Integer id;

    private  String userId;

    private String realName;

    private String idNumber;

    private String mobile;

    private Date queryTime;

    //等级
    private String levelCode;

    //等级名字
    private String levelName;

    //大师分排名
    private Integer MasterPointRank;

    //比赛时间Id
    private Integer MatchDayId;

    //比赛地点Id
    private Integer MatchPlaceId;

    //比赛时间
    private String dayInfo;

    //比赛时间详细信息
    private String dayInfoDetail;

    //比赛地点
    private String place;

    //比赛详细地点
    private String placeDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public Integer getMasterPointRank() {
        return MasterPointRank;
    }

    public void setMasterPointRank(Integer masterPointRank) {
        MasterPointRank = masterPointRank;
    }

    public Integer getMatchDayId() {
        return MatchDayId;
    }

    public void setMatchDayId(Integer matchDayId) {
        MatchDayId = matchDayId;
    }

    public Integer getMatchPlaceId() {
        return MatchPlaceId;
    }

    public void setMatchPlaceId(Integer matchPlaceId) {
        MatchPlaceId = matchPlaceId;
    }

    public String getDayInfo() {
        return dayInfo;
    }

    public void setDayInfo(String dayInfo) {
        this.dayInfo = dayInfo;
    }

    public String getDayInfoDetail() {
        return dayInfoDetail;
    }

    public void setDayInfoDetail(String dayInfoDetail) {
        this.dayInfoDetail = dayInfoDetail;
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

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
