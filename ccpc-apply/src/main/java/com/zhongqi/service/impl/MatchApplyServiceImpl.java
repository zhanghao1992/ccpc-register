package com.zhongqi.service.impl;

import com.zhongqi.dao.MatchApplySkuJpaDao;
import com.zhongqi.dao.MatchDayJpaDao;
import com.zhongqi.dao.MatchPlaceJpaDao;
import com.zhongqi.entity.MatchApplySku;
import com.zhongqi.entity.MatchDay;
import com.zhongqi.entity.MatchPlace;
import com.zhongqi.model.MatchApplySkuInfo;
import com.zhongqi.service.MatchApplyService;
import com.zhongqi.util.HttpRequestUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ningcs on 2017/7/4.
 */
@Service
public class MatchApplyServiceImpl implements MatchApplyService {

    @Value("${get_master_point_ip}")
    private String url;
    @Autowired
    private MatchDayJpaDao matchDayJpaDao;

    @Autowired
    private MatchApplySkuJpaDao matchApplySkuJpaDao;

    @Autowired
    private MatchPlaceJpaDao matchPlaceJpaDao;

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

    @Override
    public MatchDay getMatchApplyDayList() {

        return matchDayJpaDao.findByStatus(MATCH_DAY_NORMAL);
    }

    @Override
    public List<MatchApplySkuInfo> findByMatchDayId(Integer matchDayId) {
        List<MatchApplySkuInfo> list =new ArrayList<>();
        List<MatchApplySku> matchApplySkuList = matchApplySkuJpaDao.findByMatchDayId(matchDayId);
        if (!matchApplySkuList.isEmpty()){
            for (MatchApplySku matchApplySku :matchApplySkuList){
                MatchApplySkuInfo matchApplySkuInfo =new MatchApplySkuInfo();
                matchApplySkuInfo.setMatchPlaceId(matchApplySku.getMatchPlaceId());
                matchApplySkuInfo.setRemainPlayers(matchApplySku.getTotalPlayers()-matchApplySku.getCurrentPlayers());
                MatchPlace matchPlace =matchPlaceJpaDao.findById(matchApplySku.getMatchPlaceId());
                if (matchPlace!=null){
                    matchApplySkuInfo.setPlace(matchPlace.getPlace());
                    matchApplySkuInfo.setPlaceDetail(matchPlace.getPlaceDetail());
                }
                list.add(matchApplySkuInfo);
            }
        }

        return list;
    }

    private static Integer MATCH_DAY_NORMAL=1;
}
