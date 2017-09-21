package com.zhongqi.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ningcs on 2017/7/27.
 */
@Entity
@Table(name = "CpHotCount")
public class CpHotCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cpId", nullable = false)
    private String cpId;

    @Column(name = "createDate", nullable = true)
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
