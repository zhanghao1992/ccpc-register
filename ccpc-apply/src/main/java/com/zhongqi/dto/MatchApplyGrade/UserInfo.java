package com.zhongqi.dto.MatchApplyGrade;

/**
 * Created by blackyadong on 17/4/26.
 */
public class UserInfo {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 真实姓名
     */
    private String realnName;

    /**
     * 身份证号码
     */
    private String idNumber;

    /**
     *导入失败数量
     *
     */
    private Integer ErrorCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getErrorCount() {
        return ErrorCount;
    }

    public void setErrorCount(Integer errorCount) {
        ErrorCount = errorCount;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", realnName='" + realnName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", ErrorCount=" + ErrorCount +
                '}';
    }
}
