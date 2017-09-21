package com.zhongqi.service.impl;

import com.google.gson.Gson;
import com.zhongqi.dto.ResponsePersonRatingRankCollection;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.service.MasterPointApiService;
import com.zhongqi.service.MasterPointQueryService;
import com.zhongqi.util.HttpRequestUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by ningcs on 2017/7/4.
 */
@Service
public class MasterPointQueryServiceImpl implements MasterPointQueryService {

    @Value("${get_master_point_ip}")
    private String url;

    @Autowired
    private MasterPointApiService masterPointApiService;


    @Override
    public ResponseRatingForQueryInfo findMasterPointsRank(String idNumber) {

        Long time = new Date().getTime();
        //String params = "";
        ResponseRatingForQueryInfo responseRatingForQueryInfo = null;
        //params = "?" + "player_id_number=" + idNumber + "&timestamp=" + time;
        //JSONObject result = HttpRequestUtils.httpGetJsonObject(url + "/rating/by_identity_number.do" + params);

        String timestamp = String.valueOf(time);

        String response = masterPointApiService.queryPersonRatingHandle(idNumber,timestamp);

        JSONObject result = JSONObject.fromObject(response);

        if (result != null && result.size() != 0) {
            String ranking = result.get("ranking").toString();
            String level_name = result.get("level_name").toString();
            String player_name = result.get("player_name").toString();
            responseRatingForQueryInfo = new ResponseRatingForQueryInfo();
            responseRatingForQueryInfo.setLevel_name(level_name);
            responseRatingForQueryInfo.setRanking(Integer.parseInt(ranking));
            responseRatingForQueryInfo.setPlayer_name(player_name);
        }

        return responseRatingForQueryInfo;
    }

}
