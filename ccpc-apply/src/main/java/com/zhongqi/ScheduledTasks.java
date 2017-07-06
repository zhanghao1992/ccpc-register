package com.zhongqi;

import com.zhongqi.entity.PersonRatingRank;
import com.zhongqi.service.MasterPointQueryService;
import com.zhongqi.service.MatchApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
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
    private MatchApplyService  matchApplyService;

    @Scheduled(cron ="* 1 * * * *")
    public void reportCurrentTime() throws InterruptedException {
        List<PersonRatingRank> personRatingRankInfo =masterPointQueryService.getPersonRatingRankInfo();
        if (!personRatingRankInfo.isEmpty()){
            matchApplyService.addPersonRatingRankList(personRatingRankInfo);
        }
        System.out.println(String.format("---第%s次执行，当前时间为：%s", count0++, dateFormat.format(new Date())));
    }



}