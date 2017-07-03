package com.zhongqi.entity;

import javax.persistence.*;

/**
 * Created by songrenfei on 2017/7/3.
 */
@Entity
@Table(name = "MatchPlace")
public class MatchPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "place")
    private String place;

    @Column(name = "placeDetail")
    private String placeDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
