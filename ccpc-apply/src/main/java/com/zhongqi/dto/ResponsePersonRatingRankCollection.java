package com.zhongqi.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/7/6.
 */
public class ResponsePersonRatingRankCollection implements Serializable {

    private int total;

    private List<ResponsePersonRatingRankInfo> list;

    public int getTotal() {
        return total;
    }

    public ResponsePersonRatingRankCollection setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<ResponsePersonRatingRankInfo> getList() {
        return list;
    }

    public ResponsePersonRatingRankCollection setList(List<ResponsePersonRatingRankInfo> list) {
        this.list = list;
        return this;
    }
}
