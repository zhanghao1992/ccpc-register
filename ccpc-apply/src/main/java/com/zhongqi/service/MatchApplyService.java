package com.zhongqi.service;

import com.zhongqi.entity.MatchDay;
import com.zhongqi.model.MatchApplySkuInfo;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by ningcs on 2017/7/4.
 */
public interface MatchApplyService {

    //获取截止日期时的固定快照排名
    public JSONObject findMasterPointsRank(String realName, String idNumber);

    //获取报名时间列表
    public MatchDay getMatchApplyDayList();

    //获取报名时间+地点的动态“库存”统计信息
    public List<MatchApplySkuInfo> findByMatchDayId(Integer matchDayId);

}
