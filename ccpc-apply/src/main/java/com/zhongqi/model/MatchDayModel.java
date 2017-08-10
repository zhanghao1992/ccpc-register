package com.zhongqi.model;

/**
 * Created by ningcs on 2017/7/31.
 */
public class MatchDayModel {

    private Integer id;

    private String dayInfo;

    private String dayInfDetail;

    private Boolean checkPeople;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDayInfo() {
        return dayInfo;
    }

    public void setDayInfo(String dayInfo) {
        this.dayInfo = dayInfo;
    }

    public String getDayInfDetail() {
        return dayInfDetail;
    }

    public void setDayInfDetail(String dayInfDetail) {
        this.dayInfDetail = dayInfDetail;
    }

    public Boolean getCheckPeople() {
        return checkPeople;
    }

    public void setCheckPeople(Boolean checkPeople) {
        this.checkPeople = checkPeople;
    }


}
