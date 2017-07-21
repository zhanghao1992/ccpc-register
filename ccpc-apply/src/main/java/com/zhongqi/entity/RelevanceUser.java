package com.zhongqi.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by songrenfei on 2017/7/3.
 */
@Entity
@Table(name = "RelevanceUser")
public class RelevanceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name = "userId",nullable = false)
    private Integer userId;


    @Column(name = "userIdCode",nullable = false)
    private String userIdCode;

    @Column(name = "createDate")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserIdCode() {
        return userIdCode;
    }

    public void setUserIdCode(String userIdCode) {
        this.userIdCode = userIdCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
