package com.zhongqi.service.impl;

import com.zhongqi.dao.*;
import com.zhongqi.dto.PersonRatingRankInfo;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.entity.*;
import com.zhongqi.model.MatchApplySkuInfo;
import com.zhongqi.service.MatchApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private MatchApplyJpaDao matchApplyJpaDao;

    @Autowired
    private PersonRatingRankJpaDao personRatingRankJpaDao;

    @Autowired
    private RatingPersonLeverDetailJpaDao ratingPersonLeverDetailJpaDao;



    @Autowired
    private PersonRatingRankDao personRatingRankDao;


    @Override
    public ResponseRatingForQueryInfo findMasterPointsRank( String idNumber) {
        PersonRatingRank personRatingRank =personRatingRankJpaDao.findByIdentityCardNumber(idNumber);
        ResponseRatingForQueryInfo responseRatingForQueryInfo =new ResponseRatingForQueryInfo();
        if (personRatingRank!=null){
            responseRatingForQueryInfo.setRanking(personRatingRank.getGoldenRank());
        }
        return responseRatingForQueryInfo;
    }

    @Override
    public List<MatchDay> getMatchApplyDayList() {

        return matchDayJpaDao.findByStatus(MATCH_DAY_NORMAL);
    }

    @Override
    public List<MatchApplySkuInfo> findByMatchDayId(Integer matchDayId) {
        List<MatchApplySkuInfo> list =new ArrayList<>();
        List<MatchApplySku> matchApplySkuList = matchApplySkuJpaDao.findByMatchDayId(matchDayId);
        if (!matchApplySkuList.isEmpty()){
            for (MatchApplySku matchApplySku :matchApplySkuList){
                MatchApplySkuInfo matchApplySkuInfo =new MatchApplySkuInfo();
                matchApplySkuInfo.setMatchDayId(matchDayId);
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

    @Override
    public void applyMatch(Integer matchDayId, Integer  matchPlaceId, String idNumber) {
        MatchApply matchApply =new MatchApply();
        matchApply.setMatchPlaceId(matchPlaceId);
        matchApply.setMatchDayId(matchDayId);
        matchApply.setIdNumber(idNumber);
        matchApply.setApplyTime(new Date());
        matchApply.setStatus(MATCH_Apply_NORMAL);
        matchApplyJpaDao.save(matchApply);
    }

    @Override
    public String getStandardName(BigDecimal g, BigDecimal s, BigDecimal r) {
        List<RatingPersonLeverDetail> personLeverCallList = ratingPersonLeverDetailJpaDao.findRatingPersonLeverDetailList();
        Optional<RatingPersonLeverDetail> first = personLeverCallList.stream().filter(p -> (g.compareTo(p.getGolden()) != -1)
                && (s.compareTo(p.getSilver()) != -1) && (r.compareTo(p.getHeart()) != -1)).findFirst();
        RatingPersonLeverDetail info = null;
        if (first.isPresent()) {
            info = first.get();
            return info.getLevelName();
        }
        return null;
    }
    @Override
    public void addPersonRatingRankList(List<PersonRatingRank> list) {

        List<PersonRatingRank> newlist = null;
        Integer TotalPersonRatingRank = list.size();
        Integer count = TotalPersonRatingRank / 100;
        Integer remain = TotalPersonRatingRank % 100;
        Integer j = 0;
        long startTime = System.currentTimeMillis();    // 获取开始时间 毫秒级
        for (int i = 0; i < count; i++) {
            newlist = list.subList(j, j + 100);
            j = j + 100;

            personRatingRankDao.addPersonRatingRankList(newlist);
        }
        long endTime = System.currentTimeMillis();    // 获取结束时间 毫秒级
        System.out.println("批量添加数据运行时间： " + (endTime - startTime) + "ms");
        newlist = list.subList(list.size() - remain, list.size());
        personRatingRankDao.addPersonRatingRankList(newlist);


    }


    //    String levelName="";
//    levelName=this.getStandardName(personRatingRank.getGoldenPoint(),personRatingRank.getSilverPoint(),personRatingRank.getHeartPoint());
//            if (levelName!=null && !"".equals(levelName.trim())){
//        responseRatingForQueryInfo.setLevel_name(levelName);
//    }

    private static Integer MATCH_DAY_NORMAL=1;
    private static Integer MATCH_Apply_NORMAL=1;
}
