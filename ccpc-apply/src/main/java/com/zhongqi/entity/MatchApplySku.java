package com.zhongqi.entity;

import javax.persistence.*;

/**
 * Created by songrenfei on 2017/7/3.
 */
@Entity
@Table(name = "MatchApplySku")
public class MatchApplySku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name = "matchDayId")
    private Integer matchDayId;

    @Column(name = "matchPlaceId")
    private Integer matchPlaceId;

    @Column(name = "totalPlayers")
    private Integer totalPlayers;

    @Column(name = "currentPlayers")
    private Integer currentPlayers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatchDayId() {
        return matchDayId;
    }

    public void setMatchDayId(Integer matchDayId) {
        this.matchDayId = matchDayId;
    }

    public Integer getMatchPlaceId() {
        return matchPlaceId;
    }

    public void setMatchPlaceId(Integer matchPlaceId) {
        this.matchPlaceId = matchPlaceId;
    }

    public Integer getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(Integer totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public Integer getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(Integer currentPlayers) {
        this.currentPlayers = currentPlayers;
    }
}
