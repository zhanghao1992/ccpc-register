package com.zhongqi.service.impl;

import com.zhongqi.service.MasterPointQueryService;
import com.zhongqi.util.HttpRequestUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by ningcs on 2017/7/4.
 */
@Service
public class MasterPointQueryServiceImpl implements MasterPointQueryService {

    @Value("${get_master_point_ip}")
    private String url;

//    @Override
//    public JSONObject getRefereeInfoList() {
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.element("page",1);
//        jsonObject.element("page_size",10);
//        JSONObject result = HttpRequestUtils.httpPost(url+"/Referee/getRefereeInfoList.do?&page=1&page_size=10",jsonObject,false);
//        return result;
//    }


    @Override
    public JSONObject findMasterPointsRank(String realName, String idNumber) {
        JSONObject jsonObject=new JSONObject();
        String params="";
        params="?"+"realName="+realName+"&&idNumber="+idNumber;
        JSONObject result = HttpRequestUtils.httpPost(url+"/Referee/getRefereeInfoList.do"+params,jsonObject,false);
        return result;
    }
}
