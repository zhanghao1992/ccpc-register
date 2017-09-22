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
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cpId", nullable = true)
    private String cpId;

    @Column(name = "cpIdCode", nullable = true)
    private String cpIdCode;


    @Column(name = "createDate")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpIdCode() {
        return cpIdCode;
    }

    public void setCpIdCode(String cpIdCode) {
        this.cpIdCode = cpIdCode;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
