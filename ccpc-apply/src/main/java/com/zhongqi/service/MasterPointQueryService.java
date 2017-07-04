package com.zhongqi.service;

import net.sf.json.JSONObject;

/**
 * Created by ningcs on 2017/7/4.
 */
public interface MasterPointQueryService {
//    public JSONObject getRefereeInfoList();
    public JSONObject findMasterPointsRank(String realName, String idNumber);
}
