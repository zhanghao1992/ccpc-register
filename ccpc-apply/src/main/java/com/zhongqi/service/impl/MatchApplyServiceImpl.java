package com.zhongqi.service.impl;

import com.zhongqi.dao.*;
import com.zhongqi.dto.MatchApplyGrade.MatchApplyGradeInfo;
import com.zhongqi.dto.ParameterResult;
import com.zhongqi.dto.PersonRatingRankInfo;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.entity.*;
import com.zhongqi.model.MatchAddresssDayDetail;
import com.zhongqi.model.MatchApplySkuInfo;
import com.zhongqi.model.MatchDayModel;
import com.zhongqi.service.MatchApplyService;
import com.zhongqi.util.BaseUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by ningcs on 2017/7/4.
 */
@Service
public class MatchApplyServiceImpl implements MatchApplyService {

    private static Integer MATCH_DAY_NORMAL = 1;
    private static Integer MATCH_Apply_NORMAL = 1;

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

    @Autowired
    private CpSourceJpaDao cpSourceJpaDao;

    @Autowired
    private RelevanceUserJpaDao relevanceUserJpaDao;

    @Autowired
    private CpHotCountJpaDao cpHotCountJpaDao;

    @Autowired
    private MatchApplyService matchApplyService;

    @Autowired
    private MatchApplyGradeDao matchApplyGradeDao;


    /**
     * 修改对应库存，保存报名信息
     * @param cpId
     * @param idNumber
     * @param matchDayId
     * @param matchPlaceId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MatchAddresssDayDetail doMatchApply(String cpId, String idNumber, Integer matchDayId, Integer matchPlaceId) throws Exception {

        // 判断库存是否可以报名，修改SKU库存
        this.addMatchApplySkuCount(matchDayId, matchPlaceId);

        // 保存报名信息
        MatchAddresssDayDetail matchAddresssDayDetail = this.applyMatch(cpId, matchDayId, matchPlaceId, idNumber);

        return matchAddresssDayDetail;
    }

    @Override
    public ResponseRatingForQueryInfo findMasterPointsRank(String idNumber) {
        PersonRatingRank personRatingRank = personRatingRankJpaDao.findByIdentityCardNumber(idNumber);
        ResponseRatingForQueryInfo responseRatingForQueryInfo = null;
        if (personRatingRank != null) {
            responseRatingForQueryInfo = new ResponseRatingForQueryInfo();
            responseRatingForQueryInfo.setRanking(personRatingRank.getGoldenRank());
            String LevelName = this.getStandardName(personRatingRank.getGoldenPoint(), personRatingRank.getSilverPoint(), personRatingRank.getHeartPoint());
            responseRatingForQueryInfo.setLevel_name(LevelName);
        }
        return responseRatingForQueryInfo;
    }

    @Override
    public Map<String, Object> getMatchApplyDayList() {
        Map<String, Object> maps = new HashedMap();

        List<MatchDay> matchDays = matchDayJpaDao.findByStatus(MATCH_DAY_NORMAL);

        Boolean checkPeopleCount = true;
        Integer listCount = null;

        List<MatchDayModel> matchDayModels = new LinkedList<>();

        if (!matchDays.isEmpty()) {
            for (MatchDay matchDay : matchDays) {

                MatchDayModel matchDayModel = new MatchDayModel();
                matchDayModel.setId(matchDay.getMatchDayId());
                matchDayModel.setDayInfo(matchDay.getDayInfo());
                matchDayModel.setDayInfDetail(matchDay.getDayInfoDetail());
                matchDayModel.setCheckPeople(checkPeopleCount);

                List<MatchApplySku> matchApplySkuList = matchApplySkuJpaDao.findByMatchDayId(matchDay.getMatchDayId());

                if (!matchApplySkuList.isEmpty()) {
                    for (int i = 0; i < matchApplySkuList.size(); i++) {
                        if (matchApplySkuList.get(i).getCurrentPlayers() == null) {
                            matchApplySkuList.get(i).setCurrentPlayers(0);
                        }
                        if (matchApplySkuList.get(i).getTotalPlayers() > matchApplySkuList.get(i).getCurrentPlayers()) {
                            if (listCount == null) {
                                listCount = matchDay.getMatchDayId();
                            }
                            matchDayModel.setCheckPeople(false);
                        }
                    }
                }

                matchDayModels.add(matchDayModel);

                maps.put("listCount", listCount);
                maps.put("list", matchDayModels);
            }
        }
        return maps;
    }

    //获取报名时间+地点的动态“库存”统计信息
    @Override
    public Map<String, Object> findByMatchDayId(Integer matchDayId) {
        Map<String, Object> maps = new HashedMap();

        List<MatchApplySkuInfo> list = new ArrayList<>();
        Integer listCount = null;

        List<MatchApplySku> matchApplySkuList = matchApplySkuJpaDao.findByMatchDayId(matchDayId);

        if (!matchApplySkuList.isEmpty()) {
            for (int i = 0; i < matchApplySkuList.size(); i++) {
                MatchApplySkuInfo matchApplySkuInfo = new MatchApplySkuInfo();

                matchApplySkuInfo.setId(matchApplySkuList.get(i).getId());
                matchApplySkuInfo.setMatchDayId(matchApplySkuList.get(i).getMatchDayId());
                matchApplySkuInfo.setMatchPlaceId(matchApplySkuList.get(i).getMatchPlaceId());

                if (matchApplySkuList.get(i).getCurrentPlayers() == null) {
                    matchApplySkuList.get(i).setCurrentPlayers(0);
                }
                matchApplySkuInfo.setRemainPlayers(matchApplySkuList.get(i).getTotalPlayers() - matchApplySkuList.get(i).getCurrentPlayers());

                if (matchApplySkuList.get(i).getTotalPlayers() > matchApplySkuList.get(i).getCurrentPlayers()) {
                    if (listCount == null) {
                        listCount = i;
                    }
                } else {
                }
                MatchPlace matchPlace = matchPlaceJpaDao.findByMatchPlaceId(matchApplySkuList.get(i).getMatchPlaceId());
                if (matchPlace != null) {
                    matchApplySkuInfo.setPlace(matchPlace.getPlace());
                    matchApplySkuInfo.setPlaceDetail(matchPlace.getPlaceDetail());
                }

                list.add(matchApplySkuInfo);
            }
            maps.put("listCount", listCount);
            maps.put("list", list);
        }

        return maps;
    }

    @Override
    public MatchAddresssDayDetail applyMatch(String cpId, Integer matchDayId, Integer matchPlaceId, String idNumber) {
        MatchApply matchApply = new MatchApply();

        MatchPlace matchPlace = matchPlaceJpaDao.findByMatchPlaceId(matchPlaceId);
        MatchDay matchDay = matchDayJpaDao.findByMatchDayId(matchDayId);

        MatchAddresssDayDetail matchAddresssDayDetail = new MatchAddresssDayDetail();

        if (matchPlace != null && matchDay != null) {
            matchAddresssDayDetail.setDayDetail(matchDay.getDayInfoDetail());
            matchAddresssDayDetail.setPlaceDetail(matchPlace.getPlaceDetail());
        }

        matchApply.setCpIdCode(cpId);
        matchApply.setMatchPlaceId(matchPlaceId);
        matchApply.setMatchDayId(matchDayId);
        matchApply.setIdNumber(idNumber);
        matchApply.setApplyTime(new Date());
        matchApply.setStatus(MATCH_Apply_NORMAL);

        matchApplyJpaDao.saveAndFlush(matchApply);

        return matchAddresssDayDetail;
    }

    public List<RatingPersonLeverDetail> getPersonLeverCallList() {
        synchronized (this) {
            List<RatingPersonLeverDetail> personLeverCallList = ParameterResult.getPersonLeverCallEntityList();
            if (null != personLeverCallList && personLeverCallList.size() > 0) {
                return personLeverCallList;
            }
            personLeverCallList = ratingPersonLeverDetailJpaDao.findRatingPersonLeverDetailList();
            ParameterResult.setPersonLeverCallEntityList(personLeverCallList);
            return personLeverCallList;
        }
    }

    @Override
    public String getStandardName(BigDecimal g, BigDecimal s, BigDecimal r) {

        List<RatingPersonLeverDetail> personLeverCallList = this.getPersonLeverCallList();

        Optional<RatingPersonLeverDetail> first =
                personLeverCallList.stream().filter(p -> (g.compareTo(p.getGolden()) != -1) && (s.compareTo(p.getSilver()) != -1) && (r.compareTo(p.getHeart()) != -1)).findFirst();

        RatingPersonLeverDetail info = null;
        if (first.isPresent()) {
            info = first.get();
            return info.getLevelName();
        }

        return null;
    }

    @Override
    @Transactional
    public void addPersonRatingRankList(List<PersonRatingRank> list) {
        List<PersonRatingRank> newlist = null;
        Integer TotalPersonRatingRank = list.size();
        personRatingRankDao.addPersonRatingRankList(list);
    }

    @Override
    public PersonRatingRank findByIdentityCardNumber(String idNumber) {
        return personRatingRankJpaDao.findByIdentityCardNumber(idNumber);
    }

    @Override
    public Integer findByCountPersonRating() {
        return personRatingRankJpaDao.findByCountPersonRating();
    }

    @Override
    public MatchApply findByIdNumber(String idNumber) {
        return matchApplyJpaDao.findByIdNumberAndStatus(idNumber, MATCH_Apply_NORMAL);
    }


    @Override
    @Transactional
    public String addCpSource(String cpId) {
        CpSource cpSource = cpSourceJpaDao.findByCpId(cpId);
        if (cpSource == null) {
            cpSource = new CpSource();

            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            uuid = uuid.substring(10);

            cpSource.setCpIdCode(uuid + cpId);
            cpSource.setCpId(cpId);
            cpSource.setCreateDate(new Date());
            cpSourceJpaDao.saveAndFlush(cpSource);
        }
        cpSource = cpSourceJpaDao.findByCpId(cpId);

        return cpSource.getCpIdCode();

    }

    // 创建用户ID和加密随机码的对应关系
    @Override
    @Transactional
    public RelevanceUser createRelevanceUserId(Integer userId) {
        String userIdCode = "";
        RelevanceUser relevanceUser = null;

        userIdCode = BaseUtils.getTenRandomLetterAndNumber();
        relevanceUser = this.findByUserIdCode(userIdCode);

        while(relevanceUser != null){
            userIdCode = BaseUtils.getTenRandomLetterAndNumber();
            relevanceUser = this.findByUserIdCode(userIdCode);
        }

        if (relevanceUser == null) {
            relevanceUser = new RelevanceUser();
            relevanceUser.setUserId(userId);
            relevanceUser.setUserIdCode(userIdCode);
            relevanceUser.setCreateDate(new Date());
            relevanceUserJpaDao.saveAndFlush(relevanceUser);
            return relevanceUser;
        }
        return null;

    }

    @Override
    public RelevanceUser findByUserIdCode(String userIdCode) {
        return relevanceUserJpaDao.findByUserIdCode(userIdCode);
    }

    @Override
    public RelevanceUser findByUserId(Integer userId) {
        return relevanceUserJpaDao.findByUserId(userId);
    }

    @Override
    @Transactional
    public void AddCpHotCount(String cpId) {
        CpHotCount cpHotCount = new CpHotCount();
        cpHotCount.setCpId(cpId);
        cpHotCount.setCreateDate(new Date());
        cpHotCountJpaDao.save(cpHotCount);
    }

    // 判断SKU库存是否可以报名，修改SKU库存
    public void addMatchApplySkuCount(Integer matchDayId, Integer matchPlaceId) throws Exception {

        MatchApplySku matchApplySku = matchApplySkuJpaDao.findByMatchDayIdAndMatchPlaceId(matchDayId, matchPlaceId);

        if (matchApplySku != null) {
            if (matchApplySku.getCurrentPlayers() == null) {
                matchApplySku.setCurrentPlayers(0);
            }

            Integer total = matchApplySku.getTotalPlayers();
            Integer current = matchApplySku.getCurrentPlayers();

            if(current < total){
                matchApplySku.setCurrentPlayers(matchApplySku.getCurrentPlayers() + 1);

                matchApplySkuJpaDao.saveAndFlush(matchApplySku);
            }else{
                throw new Exception("报名名额已满");
            }

        }else{
            throw new Exception("对应库存信息不存在");
        }
    }

    @Override
    public CpSource findByCpIdCode(String cpIdCode) {
        return cpSourceJpaDao.findByCpIdCode(cpIdCode);
    }

    @Override
    public Map<String, Object> personRatingRankList(Integer page, Integer page_size, String idNumber) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<PersonRatingRank> personRatingRanks = personRatingRankDao.personRatingRankList(page, page_size, idNumber);
        Integer total = personRatingRankDao.personRatingRankListCount(idNumber);
        List<PersonRatingRankInfo> personRatingRankInfoList = new ArrayList<>();
        String ratingLevel = "";
        if (!personRatingRanks.isEmpty() && total != 0) {
            for (int i = 0; i < personRatingRanks.size(); i++) {
                PersonRatingRank personRatingRank = new PersonRatingRank();
                PersonRatingRankInfo personRatingRankInfo = new PersonRatingRankInfo();
                personRatingRank = personRatingRanks.get(i);
                BeanUtils.copyProperties(personRatingRankInfo, personRatingRank);
                ratingLevel = this.getStandardName(personRatingRank.getGoldenPoint(), personRatingRank.getSilverPoint(), personRatingRank.getHeartPoint());
                personRatingRankInfo.setIdentityCardNumber(BaseUtils.encryptIdNumber(personRatingRank.getIdentityCardNumber()));
                personRatingRankInfo.setPlayerName(BaseUtils.encryptRealName(personRatingRank.getPlayerName()));
                personRatingRankInfo.setBindDateTimeStr(BaseUtils.formatDateToStrDay(personRatingRank.getBindDateTime()));
                personRatingRankInfo.setCreateDatetimeStr(BaseUtils.formatDateToStrDay(personRatingRank.getCreateDatetime()));
                personRatingRankInfo.setRatingLevel(ratingLevel);
                personRatingRankInfoList.add(personRatingRankInfo);
            }
            map.put("page", page);
            map.put("page_szie", page_size);
            map.put("total", total);
            map.put("list", personRatingRankInfoList);
        }
        return map;

    }

    @Override
    public Map<String, Object> getMatchApplyGradeList(Integer page, Integer page_size, String idNumber, Integer type, String matchTime) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<MatchApplyGrade> matchApplyGrades = matchApplyGradeDao.getMatchApplyGradeList(page, page_size, idNumber, type, matchTime);
        Integer total = matchApplyGradeDao.getMatchApplyGradeListCount(idNumber, type, matchTime);
        List<MatchApplyGradeInfo> personRatingRankInfoList = new ArrayList<>();
        String ratingLevel = "";
        if (!matchApplyGrades.isEmpty() && total != 0) {
            for (int i = 0; i < matchApplyGrades.size(); i++) {
                MatchApplyGrade matchApplyGrade = new MatchApplyGrade();
                MatchApplyGradeInfo matchApplyGradeInfo = new MatchApplyGradeInfo();
                matchApplyGrade = matchApplyGrades.get(i);
                BeanUtils.copyProperties(matchApplyGradeInfo, matchApplyGrade);
                ratingLevel = this.getStandardName(matchApplyGrade.getGoldenPoint(), matchApplyGrade.getSilverPoint(), matchApplyGrade.getHeartPoint());
                matchApplyGradeInfo.setIdentityCardNumber(BaseUtils.encryptIdNumber(matchApplyGrade.getIdentityCardNumber()));
                matchApplyGradeInfo.setPlayerName(BaseUtils.encryptRealName(matchApplyGrade.getPlayerName()));
                matchApplyGradeInfo.setCreateDatetimeStr(BaseUtils.formatDateToStrDay(matchApplyGrade.getCreateDatetime()));
                matchApplyGradeInfo.setRatingLevel(ratingLevel);
                if (idNumber == null || "".equals(idNumber.trim())) {
                    matchApplyGradeInfo.setBonus("0");
                } else {
                    if (matchApplyGrade.getBonus() > 0.0001) {
                        matchApplyGradeInfo.setBonus(BaseUtils.getTwoDecimal(matchApplyGrade.getBonus()));
                    }
                }

                personRatingRankInfoList.add(matchApplyGradeInfo);
            }
            map.put("page", page);
            map.put("page_szie", page_size);
            map.put("total", total);
            map.put("list", personRatingRankInfoList);
        }
        return map;
    }


}
