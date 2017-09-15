package com.zhongqi.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ningcs on 2017/9/14.
 */
@Entity
@Table(name = "WhiteUser")
public class WhiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    private Integer id;
    @Column(name = "realName",nullable = true)
    private String realName;
    @Column(name = "mobile",nullable = true)
    private String mobile;
    @Column(name = "idNumber",unique = true,nullable = false)
    private String idNumber;
    @Column(name = "createDateTime",nullable = true)
    private Date createDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
}
