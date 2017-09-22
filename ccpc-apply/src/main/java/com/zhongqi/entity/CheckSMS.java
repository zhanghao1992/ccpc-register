package com.zhongqi.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by songrenfei on 16/7/14.
 */
@Entity
@Table(name="CheckSMS")
public class CheckSMS {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name="userId",nullable=true)
    private Integer userId;

    /**
     * 手机
     */
    @Column(name="mobile",nullable=true)
    private String mobile;

    /**
     * 校验码
     */
    @Column(name="checkCode",nullable=true)
    private String checkCode;

    /**
     * 是否校验
     */
    @Column(name="checked",nullable=true)
    private boolean checked;

    /**
     * 发送时间
     */
    @Column(name="sendTime",nullable=true)
    private Date sendTime;

    /**
     * 失效时间
     */
    @Column(name="failureTime",nullable=true)
    private Date failureTime;

    /**
     * 校验时间
     */
    @Column(name="checkedTime",nullable=true)
    private Date checkedTime;


    public Integer getId() {
        return id;
    }

    public CheckSMS setId(Integer id) {
        this.id = id;
        return this;
    }


    public Integer getUserId() {
        return userId;
    }

    public CheckSMS setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }


    public String getMobile() {
        return mobile;
    }

    public CheckSMS setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }


    public String getCheckCode() {
        return checkCode;
    }

    public CheckSMS setCheckCode(String checkCode) {
        this.checkCode = checkCode;
        return this;
    }


    public boolean isChecked() {
        return checked;
    }

    public CheckSMS setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }


    public Date getSendTime() {
        return sendTime;
    }

    public CheckSMS setSendTime(Date sendTime) {
        this.sendTime = sendTime;
        return this;
    }


    public Date getFailureTime() {
        return failureTime;
    }

    public CheckSMS setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
        return this;
    }


    public Date getCheckedTime() {
        return checkedTime;
    }

    public CheckSMS setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
        return this;
    }

}
