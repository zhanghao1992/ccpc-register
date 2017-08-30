package com.zhongqi.service.impl;

import com.zhongqi.dao.*;
import com.zhongqi.dto.ParameterResult;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.entity.*;
import com.zhongqi.model.MatchAddresssDayDetail;
import com.zhongqi.model.MatchApplySkuInfo;
import com.zhongqi.model.MatchDayModel;
import com.zhongqi.service.MatchApplyService;
import com.zhongqi.util.BaseUtils;
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


    @Override
    public ResponseRatingForQueryInfo findMasterPointsRank(String idNumber) {
        PersonRatingRank personRatingRank = personRatingRankJpaDao.findByIdentityCardNumber(idNumber);
        ResponseRatingForQueryInfo responseRatingForQueryInfo =null;
        if (personRatingRank != null) {
            responseRatingForQueryInfo=new ResponseRatingForQueryInfo();
            responseRatingForQueryInfo.setRanking(personRatingRank.getGoldenRank());
            String LevelName = matchApplyService.getStandardName(personRatingRank.getGoldenPoint(), personRatingRank.getSilverPoint(), personRatingRank.getHeartPoint());
            responseRatingForQueryInfo.setLevel_name(LevelName);
        }
        return responseRatingForQueryInfo;
    }

    @Override
    public Map<String,Object> getMatchApplyDayList() {
        Map<String,Object> maps =new HashedMap();
        List<MatchDay> matchDays = matchDayJpaDao.findByStatus(MATCH_DAY_NORMAL);
        Boolean checkPeopleCount = true;
        Integer listCount =null ;
        List<MatchDayModel> matchDayModels =new LinkedList<>();
        if (!matchDays.isEmpty()) {
            for (MatchDay matchDay : matchDays) {
                MatchDayModel matchDayModel = new MatchDayModel();
                matchDayModel.setId(matchDay.getId());
                matchDayModel.setDayInfo(matchDay.getDayInfo());
                matchDayModel.setDayInfDetail(matchDay.getDayInfoDetail());
                matchDayModel.setCheckPeople(checkPeopleCount);
                List<MatchApplySku> matchApplySkuList = matchApplySkuJpaDao.findByMatchDayId(matchDay.getId());
                if (!matchApplySkuList.isEmpty()) {
                    for (int i=0;i<matchApplySkuList.size();i++){
                        if (matchApplySkuList.get(i).getCurrentPlayers()==null ){
                            matchApplySkuList.get(i).setCurrentPlayers(0);
                        }
                       if( matchApplySkuList.get(i).getTotalPlayers() > matchApplySkuList.get(i).getCurrentPlayers()){
                           if (listCount==null){
                               listCount =matchDay.getId();
                           }
                           matchDayModel.setCheckPeople(false);
                       }
                    }
                }
                matchDayModels.add(matchDayModel);
                maps.put("listCount",listCount);
                maps.put("list",matchDayModels);
            }
        }
        return maps;
    }

    @Override
    public Map<String,Object> findByMatchDayId(Integer matchDayId) {
        Map<String,Object> maps =new HashedMap();
        List<MatchApplySkuInfo> list = new ArrayList<>();
        Integer listCount =null;
        List<MatchApplySku> matchApplySkuList = matchApplySkuJpaDao.findByMatchDayId(matchDayId);
        if (!matchApplySkuList.isEmpty()) {
            for (int i=0;i<matchApplySkuList.size();i++){
                MatchApplySkuInfo matchApplySkuInfo = new MatchApplySkuInfo();
                matchApplySkuInfo.setId(matchApplySkuList.get(i).getId());
                matchApplySkuInfo.setMatchDayId(matchApplySkuList.get(i).getMatchDayId());
                matchApplySkuInfo.setMatchPlaceId(matchApplySkuList.get(i).getMatchPlaceId());
                if (matchApplySkuList.get(i).getCurrentPlayers()==null ){
                    matchApplySkuList.get(i).setCurrentPlayers(0);
                }
                matchApplySkuInfo.setRemainPlayers(matchApplySkuList.get(i).getTotalPlayers() - matchApplySkuList.get(i).getCurrentPlayers());

                if (matchApplySkuList.get(i).getTotalPlayers() >matchApplySkuList.get(i).getCurrentPlayers()){
                    if (listCount==null){
                        listCount =i;
                    }
                }else {
                }
                MatchPlace matchPlace = matchPlaceJpaDao.findById(matchApplySkuList.get(i).getMatchPlaceId());
                if (matchPlace != null) {
                    matchApplySkuInfo.setPlace(matchPlace.getPlace());
                    matchApplySkuInfo.setPlaceDetail(matchPlace.getPlaceDetail());
                }
                list.add(matchApplySkuInfo);
            }
            maps.put("listCount",listCount);
            maps.put("list",list);
        }

        return maps;
    }

    @Override
    public MatchAddresssDayDetail applyMatch(String cpId, Integer matchDayId, Integer matchPlaceId, String idNumber) {
        MatchApply matchApply = new MatchApply();
        MatchPlace matchPlace = matchPlaceJpaDao.findById(matchPlaceId);
        MatchDay matchDay = matchDayJpaDao.findById(matchDayId);
        MatchAddresssDayDetail matchAddresssDayDetail =new MatchAddresssDayDetail();
        if (matchPlace!=null && matchDay!=null){
            matchAddresssDayDetail.setDayDetail(matchDay.getDayInfoDetail());
            matchAddresssDayDetail.setPlaceDetail(matchPlace.getPlaceDetail());
        }
        matchApply.setCpIdCode(cpId);
        matchApply.setMatchPlaceId(matchPlaceId);
        matchApply.setMatchDayId(matchDayId);
        matchApply.setIdNumber(idNumber);
        matchApply.setApplyTime(new Date());
        matchApply.setStatus(MATCH_Apply_NORMAL);
        matchApplyJpaDao.save(matchApply);

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
    public String  getStandardName(BigDecimal g, BigDecimal s, BigDecimal r) {
        Map<String,String> map =new HashedMap();
        String levelCode="";
        String levelName="";
        List<RatingPersonLeverDetail> personLeverCallList = getPersonLeverCallList();
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
        CpSource cpSource1 = cpSourceJpaDao.findByCpId(cpId);
        if (cpSource1 == null) {
            CpSource cpSource = new CpSource();
            String ss = BaseUtils.getTenRandomLetterAndNumber();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            uuid = uuid.substring(10);
            System.out.println(uuid);
            cpSource.setCpIdCode(uuid + cpId);
            cpSource.setCpId(cpId);
            cpSource.setCreateDate(new Date());
            cpSourceJpaDao.save(cpSource);
        }
        cpSource1 = cpSourceJpaDao.findByCpId(cpId);

        return cpSource1.getCpIdCode();

    }

    @Override
    @Transactional
    public RelevanceUser createRelevanceUserId(Integer userId) {
        String userIdCode = BaseUtils.getTenRandomLetterAndNumber();
        RelevanceUser relevanceUser1 = this.findByUserIdCode(userIdCode);
        if (relevanceUser1 == null) {
            RelevanceUser relevanceUser = new RelevanceUser();
            relevanceUser.setUserId(userId);
            relevanceUser.setUserIdCode(userIdCode);
            relevanceUser.setCreateDate(new Date());
            relevanceUserJpaDao.save(relevanceUser);
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
    public void AddCpHotCount(String cpId) {
        CpHotCount cpHotCount = new CpHotCount();
        cpHotCount.setCpId(cpId);
        cpHotCount.setCreateDate(new Date());
        cpHotCountJpaDao.save(cpHotCount);
    }


    public void  addMatchApplySkuCount(Integer matchDayId, Integer matchPlaceId){

        MatchApplySku matchApplySku =matchApplySkuJpaDao.findByMatchDayIdAndMatchPlaceId(matchDayId,matchPlaceId);

        if (matchApplySku!=null  ){
            if (matchApplySku.getCurrentPlayers()==null ){
                matchApplySku.setCurrentPlayers(0);
            }
            if ( matchApplySku.getTotalPlayers()>matchApplySku.getCurrentPlayers()){
                matchApplySku.setCurrentPlayers(matchApplySku.getCurrentPlayers()+1);
            }
        }
    }

    @Override
    public CpSource findByCpIdCode(String cpIdCode) {
        return cpSourceJpaDao.findByCpIdCode(cpIdCode);
    }

    private static Integer MATCH_DAY_NORMAL = 1;
    private static Integer MATCH_Apply_NORMAL = 1;
}
