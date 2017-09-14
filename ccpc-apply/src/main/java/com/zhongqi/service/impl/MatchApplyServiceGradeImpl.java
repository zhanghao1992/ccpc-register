package com.zhongqi.service.impl;

import com.zhongqi.dao.MatchApplyGradeDao;
import com.zhongqi.dao.PersonRatingRankDao;
import com.zhongqi.dao.WhiteUserJpaDao;
import com.zhongqi.dto.MatchApplyGrade.MatchApplyGradeInfo;
import com.zhongqi.dto.MatchApplyGrade.UserInfo;
import com.zhongqi.dto.PersonRatingRankInfo;
import com.zhongqi.entity.MatchApplyGrade;
import com.zhongqi.entity.PersonRatingRank;
import com.zhongqi.entity.User;
import com.zhongqi.entity.constant.MatchApplyGradeConstant;
import com.zhongqi.service.MatchApplyGradeService;
import com.zhongqi.service.MatchApplyService;
import com.zhongqi.util.BaseUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ningcs on 2017/7/4.
 */
@Service
public class MatchApplyServiceGradeImpl implements MatchApplyGradeService {

    @Value("${get_master_point_ip}")
    private String url;

    @Autowired
    private PersonRatingRankDao personRatingRankDao;

    @Autowired
    private MatchApplyGradeDao matchApplyGradeDao;

    @Autowired
    private MatchApplyService matchApplyService;

    @Autowired
    private WhiteUserJpaDao userJpaDao;




    @Override
    public Map<String, Object> personRatingRankList(Integer page, Integer page_size,String idNumber) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        List<PersonRatingRank>personRatingRanks = personRatingRankDao.personRatingRankList(page,page_size,idNumber);
        Integer total =personRatingRankDao.personRatingRankListCount(idNumber);
        List<PersonRatingRankInfo> personRatingRankInfoList =new ArrayList<>();
        String ratingLevel="";
        if (!personRatingRanks.isEmpty() && total!=0){
            for (int i = 0; i < personRatingRanks.size(); i++) {
                PersonRatingRank personRatingRank =new PersonRatingRank();
                PersonRatingRankInfo personRatingRankInfo =new PersonRatingRankInfo();
                personRatingRank=personRatingRanks.get(i);
                BeanUtils.copyProperties(personRatingRankInfo,personRatingRank);
                ratingLevel =matchApplyService.getStandardName(personRatingRank.getGoldenPoint(),personRatingRank.getSilverPoint(),personRatingRank.getHeartPoint());
                personRatingRankInfo.setIdentityCardNumber(BaseUtils.encryptIdNumber(personRatingRank.getIdentityCardNumber()));
                personRatingRankInfo.setPlayerName(BaseUtils.encryptRealName(personRatingRank.getPlayerName()));
                personRatingRankInfo.setBindDateTimeStr(BaseUtils.formatDateToStrDay(personRatingRank.getBindDateTime()));
                personRatingRankInfo.setCreateDatetimeStr(BaseUtils.formatDateToStrDay(personRatingRank.getCreateDatetime()));
                personRatingRankInfo.setRatingLevel(ratingLevel);
                personRatingRankInfoList.add(personRatingRankInfo);
        }
        map.put("page",page);
        map.put("page_szie",page_size);
        map.put("total",total);
        map.put("list",personRatingRankInfoList);
        }
        return map;

    }

    @Override
    public Map<String, Object> getMatchApplyGradeList(Integer page, Integer page_size, String idNumber, Integer type, String matchTime) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        List<MatchApplyGrade> matchApplyGrades =matchApplyGradeDao.getMatchApplyGradeList(page, page_size, idNumber, type, matchTime);
        Integer total =matchApplyGradeDao.getMatchApplyGradeListCount(idNumber, type, matchTime);
        List<MatchApplyGradeInfo> personRatingRankInfoList =new ArrayList<>();
        String ratingLevel="";
        if (!matchApplyGrades.isEmpty() && total!=0){
            for (int i = 0; i < matchApplyGrades.size(); i++) {
                MatchApplyGrade matchApplyGrade =new MatchApplyGrade();
                MatchApplyGradeInfo matchApplyGradeInfo =new MatchApplyGradeInfo();
                matchApplyGrade=matchApplyGrades.get(i);
                BeanUtils.copyProperties(matchApplyGradeInfo,matchApplyGrade);
                ratingLevel =matchApplyService.getStandardName(matchApplyGrade.getGoldenPoint(),matchApplyGrade.getSilverPoint(),matchApplyGrade.getHeartPoint());
                matchApplyGradeInfo.setIdentityCardNumber(BaseUtils.encryptIdNumber(matchApplyGrade.getIdentityCardNumber()));
                matchApplyGradeInfo.setPlayerName(BaseUtils.encryptRealName(matchApplyGrade.getPlayerName()));
                matchApplyGradeInfo.setCreateDatetimeStr(BaseUtils.formatDateToStrDay(matchApplyGrade.getCreateDatetime()));
                matchApplyGradeInfo.setRatingLevel(ratingLevel);
                if (idNumber==null || "".equals(idNumber.trim())){
                    matchApplyGradeInfo.setBonus("0");
                }else{
                    if (matchApplyGrade.getBonus()>0.0001){
                        matchApplyGradeInfo.setBonus(BaseUtils.getTwoDecimal(matchApplyGrade.getBonus()));
                    }
                }

                personRatingRankInfoList.add(matchApplyGradeInfo);
            }
            map.put("page",page);
            map.put("page_szie",page_size);
            map.put("total",total);
            map.put("list",personRatingRankInfoList);
        }
        return map;
    }

    @Override
    public Map<String, Object> importMatchApplyGrade(List<List<List<String>>> list) {
        User user =null;
        Map<String, Object> map =new HashedMap();
        List<UserInfo> userInfoList =new ArrayList<>();
        for (int i=0;i<list.size();i++) {
            List<List<String>> rowList = list.get(i);
            if (rowList.size() == 0 || rowList.get(0).size() != 7) {
                continue;
            }
            for (int j = 1; j < rowList.size(); j++) {
                List<String> cellList = rowList.get(j);

                if (cellList.size() == 0) {
                    continue;
                }
                // 姓名
                String cell0 = cellList.get(0);
                String realName = cell0;
                // 身份证号码
                String cell1 = cellList.get(1);
                String idNumber = cell1;
                Integer userId = 0;
                Integer  goldenRank=0;
                Double bonus=0.0;
                Integer  matchType=0;
                if (realName != null && !"".equals(realName.trim()) && !"无".equals(realName.trim()) &&
                        idNumber != null && !"".equals(idNumber.trim())) {
                    try {
                        user = userJpaDao.findByIdNumberAndRealName(idNumber, realName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (user != null) {
                        userId = user.getId();

                        // 排名
                        String cell2 = cellList.get(2);
                        String goldenRankStr = cell2;
                        goldenRank=Integer.parseInt(goldenRankStr);

                        // 金分
                        String cell3 = cellList.get(3);
                        String goldenPoint= cell3;

                        // 银分
                        String cell4 = cellList.get(4);
                        String silverPoint = cell4;

                        // 红分
                        String cell5 = cellList.get(5);
                        String  heartPoint = cell5;

                        // 比赛类型
                        String cell6 = cellList.get(6);
                        String matchTypeName = cell6;
                        matchType=MatchApplyGradeConstant.getMatchType(matchTypeName);

                        //比赛时间
                        String cell7 = cellList.get(7);
                        String matchTime= cell7;

                        //奖金
                        String cell8 = cellList.get(8);
                        String bonusStr = cell8;
                        bonus=BaseUtils.getTwoDecimalDouble(bonusStr);

                        MatchApplyGrade matchApplyGrade = null;


                        try {
                            matchApplyGrade = matchApplyGradeDao.getMatchApplyGradeByIdNumber(idNumber,matchType );

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (matchApplyGrade == null) {
                            BigDecimal g=new BigDecimal(goldenPoint);
                            BigDecimal s=new BigDecimal(silverPoint);
                            BigDecimal h=new BigDecimal(heartPoint);
                            matchApplyGradeDao.addMatchApplyGrade(idNumber, realName, g, s, h,goldenRank,matchTime ,matchType,bonus);

                        }
                    }
                }
            }
        }
        map.put("list", userInfoList);
        return map;
    }
}
