package com.zhongqi.task;

import com.zhongqi.dao.PersonRatingRankJpaDao;
import com.zhongqi.dto.ResponsePersonRatingRankCollection;
import com.zhongqi.dto.ResponsePersonRatingRankInfo;
import com.zhongqi.entity.PersonRatingRank;
import com.zhongqi.service.MasterPointQueryService;
import com.zhongqi.service.MatchApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jordan on 17/3/27.
 */

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Integer count0 = 1;
    private Integer count1 = 1;
    private Integer count2 = 1;

    @Autowired
    private MasterPointQueryService masterPointQueryService;

    @Autowired
    private PersonRatingRankJpaDao personRatingRankJpaDao;

    @Autowired
    private MatchApplyService  matchApplyService;

    @Scheduled(cron ="*/10 * * * * *")
    public void reportCurrentTime() throws InterruptedException {
        ResponsePersonRatingRankCollection ratingRankInfoList = masterPointQueryService.getPersonRatingRankInfo(1, 200);
        Integer currentCount =personRatingRankJpaDao.findByCountPersonRating();
        Integer total = ratingRankInfoList.getTotal();
        if (currentCount!=total && currentCount <total) {
            Integer page = 0;
            Integer count = total / 1000;
            if (count != 0) {
                count = count + 1;
            }
            Integer page_size = 1000;
            for (int i = 0; i < count; i++) {
                ResponsePersonRatingRankCollection ratingRankInfo = masterPointQueryService.getPersonRatingRankInfo(i, page_size);
                List<ResponsePersonRatingRankInfo> responsePersonRatingRankInfos = ratingRankInfo.getList();

                List<PersonRatingRank> personRatingRanks = new ArrayList<>();
                for (ResponsePersonRatingRankInfo responsePersonRatingRankInfo : responsePersonRatingRankInfos) {
                    PersonRatingRank ratingRank1 = null;
                    ratingRank1 = personRatingRankJpaDao.findByIdentityCardNumber(responsePersonRatingRankInfo.getIdentityCardNumber());
                    if (ratingRank1 == null) {
                        PersonRatingRank ratingRank = this.setResponsePersonRatingRankInfo(responsePersonRatingRankInfo);
                        personRatingRanks.add(ratingRank);
                    }

                }
                if (!responsePersonRatingRankInfos.isEmpty()) {
                    matchApplyService.addPersonRatingRankList(personRatingRanks);
                }
            }
            logger.info(String.format("---第%s次执行，当前时间为：%s", count0++, dateFormat.format(new Date())));
        }else {
            logger.info("大师分数据已经同步完成");
        }

    }

    private  PersonRatingRank setResponsePersonRatingRankInfo(ResponsePersonRatingRankInfo rankInfo){
        PersonRatingRank ratingRank = new PersonRatingRank();
        ratingRank.setId(rankInfo.getGoldenRank());
        ratingRank.setIdentityCardNumber(rankInfo.getIdentityCardNumber());
        ratingRank.setPlayerName(rankInfo.getPlayerName());
        ratingRank.setGoldenPoint(rankInfo.getGoldenPoint());
        ratingRank.setSilverPoint(rankInfo.getSilverPoint());
        ratingRank.setHeartPoint(rankInfo.getHeartPoint());
        ratingRank.setCreateDatetime(new Date(rankInfo.getCreateDatetime()));
        ratingRank.setBindDateTime(new Date(rankInfo.getBindDateTime()));
        ratingRank.setGoldenRank(rankInfo.getGoldenRank());
        ratingRank.setSilverRank(rankInfo.getSilverRank());
        ratingRank.setHeartRank(rankInfo.getHeartRank());
        ratingRank.setGradeCode(rankInfo.getGradeCode());
        return ratingRank;

    }



}