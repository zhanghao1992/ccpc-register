package com.zhongqi.dto.MatchApplyGrade;

/**
 * Created by blackyadong on 17/4/26.
 */
public class UserInfo {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 真实姓名
     */
    private String realnName;

    /**
     * 身份证号码
     */
    private String idNumber;

    /**
     * 金分
     */
    private String goldenPoint;

    /**
     * 银分
     */
    private String silverPoint;

    /**
     * 红分
     */
    private String heartPoint;

    /**
     * 比赛时间
     */
    private String matchTime;

    /**
     * 比赛类型
     */
    private String  matchTypeName;

    /**
     * 排名
     */
    private String goldenRank;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealnName() {
        return realnName;
    }

    public void setRealnName(String realnName) {
        this.realnName = realnName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getGoldenPoint() {
        return goldenPoint;
    }

    public void setGoldenPoint(String goldenPoint) {
        this.goldenPoint = goldenPoint;
    }

    public String getSilverPoint() {
        return silverPoint;
    }

    public void setSilverPoint(String silverPoint) {
        this.silverPoint = silverPoint;
    }

    public String getHeartPoint() {
        return heartPoint;
    }

    public void setHeartPoint(String heartPoint) {
        this.heartPoint = heartPoint;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getMatchTypeName() {
        return matchTypeName;
    }

    public void setMatchTypeName(String matchTypeName) {
        this.matchTypeName = matchTypeName;
    }

    public String getGoldenRank() {
        return goldenRank;
    }

    public void setGoldenRank(String goldenRank) {
        this.goldenRank = goldenRank;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", realnName='" + realnName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", goldenPoint='" + goldenPoint + '\'' +
                ", silverPoint='" + silverPoint + '\'' +
                ", heartPoint='" + heartPoint + '\'' +
                ", matchTime='" + matchTime + '\'' +
                ", matchTypeName='" + matchTypeName + '\'' +
                ", goldenRank='" + goldenRank + '\'' +
                '}';
    }
}
