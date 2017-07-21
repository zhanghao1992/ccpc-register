package com.zhongqi.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by songrenfei on 2017/7/3.
 */
@Entity
@Table(name = "CpSource")
public class CpSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name = "code",nullable = false)
    private String code;

    @Column(name = "createDate")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
