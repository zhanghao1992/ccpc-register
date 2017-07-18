package com.zhongqi.service.impl;

import com.zhongqi.dao.*;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.entity.MatchApply;
import com.zhongqi.entity.MatchDay;
import com.zhongqi.entity.MatchPlace;
import com.zhongqi.entity.User;
import com.zhongqi.model.UserModel;
import com.zhongqi.service.MasterPointQueryService;
import com.zhongqi.service.MatchApplyService;
import com.zhongqi.service.UserService;
import com.zhongqi.util.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by ningcs on 2017/7/5.
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${cut-off-data}")
    private String cutOffDate;
    @Autowired
    private MatchPlaceJpaDao matchPlaceJpaDao;

    @Autowired
    private MatchApplyJpaDao matchApplyJpaDao;

    @Autowired
    private MatchDayJpaDao matchDayJpaDao;

    @Autowired
    private UserJpaDao userJpaDao;

    @Autowired
    private MasterPointQueryService masterPointQueryService;

    @Autowired
    private MatchApplyService matchApplyService;

    @Autowired
    UserDao userDao;

    @Override
    public UserModel getCurrentUserInfo(String realName,String idNumber,String mobile) {
        User user=null;
        UserModel userModel =null;
         user = userJpaDao.findByIdNumber(idNumber);
        if (user==null){
            this.addUser(realName, idNumber, mobile);
        }else {
            this.updateUser(realName,idNumber,mobile, user.getId());
        }
        user = userJpaDao.findByIdNumber(idNumber);
        if (user!=null) {
            userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setMobile(user.getMobile());
            userModel.setRealName(user.getRealName());
            ResponseRatingForQueryInfo responseRatingForQueryInfo =null;
            if (BaseUtils.compareCurrentTime(cutOffDate)){
                responseRatingForQueryInfo = masterPointQueryService.findMasterPointsRank(idNumber);
            }else{
                responseRatingForQueryInfo = matchApplyService.findMasterPointsRank(idNumber);
            }

            if (responseRatingForQueryInfo != null) {
                userModel.setLevelCode(responseRatingForQueryInfo.getLevel_name());
                userModel.setMasterPointRank(responseRatingForQueryInfo.getRanking());
            }
            MatchApply matchApply =matchApplyJpaDao.findByIdNumberAndStatus(idNumber,MATCH_Apply_NORMAL);
            if (matchApply!=null){
                MatchDay matchDay =matchDayJpaDao.findById(matchApply.getMatchDayId());
                if (matchDay!=null){
                    userModel.setMatchDayId(matchApply.getMatchDayId());
                    userModel.setDayInfo(matchDay.getDayInfo());
                    userModel.setDayInfoDetail(matchDay.getDayInfoDetail());
                }
                MatchPlace matchPlace =matchPlaceJpaDao.findById(matchApply.getId());
                if (matchPlace!=null){
                    userModel.setMatchPlaceId(matchApply.getId());
                    userModel.setPlace(matchPlace.getPlace());
                    userModel.setPlaceDetail(matchPlace.getPlaceDetail());
                }
            }
        }
        return userModel;

    }


    @Override
    public UserModel getCurrentUser(String idNumber) {
        UserModel userModel =new UserModel();
        ResponseRatingForQueryInfo responseRatingForQueryInfo =null;
        if (BaseUtils.compareCurrentTime(cutOffDate)){
            responseRatingForQueryInfo = masterPointQueryService.findMasterPointsRank(idNumber);
        }else{
            responseRatingForQueryInfo = matchApplyService.findMasterPointsRank(idNumber);
        }

        if (responseRatingForQueryInfo != null) {
            userModel.setLevelCode(responseRatingForQueryInfo.getLevel_name());
            userModel.setMasterPointRank(responseRatingForQueryInfo.getRanking());
        }
        MatchApply matchApply =matchApplyJpaDao.findByIdNumberAndStatus(idNumber,MATCH_Apply_NORMAL);
        if (matchApply!=null){
            MatchDay matchDay =matchDayJpaDao.findById(matchApply.getMatchDayId());
            if (matchDay!=null){
                userModel.setMatchDayId(matchApply.getMatchDayId());
                userModel.setDayInfo(matchDay.getDayInfo());
                userModel.setDayInfoDetail(matchDay.getDayInfoDetail());
            }
            MatchPlace matchPlace =matchPlaceJpaDao.findById(matchApply.getId());
            if (matchPlace!=null){
                userModel.setMatchPlaceId(matchApply.getId());
                userModel.setPlace(matchPlace.getPlace());
                userModel.setPlaceDetail(matchPlace.getPlaceDetail());
            }
        }
        return userModel;
    }

    @Override
    @Transactional
    public void addUser(String realName,String idNumber,String mobile){
        User user =new User();
        user.setIdNumber(idNumber);
        user.setRealName(realName);
        user.setMobile(mobile);
        user.setQueryTime(new Date());
        userJpaDao.save(user);
    }

    @Override
    @Transactional
    public void updateUser(String realName, String idNumber, String mobile, Integer id) {
        User user= userJpaDao.findByIdNumber(idNumber);
        user.setRealName(realName);
        user.setMobile(mobile);
        userJpaDao.save(user);
    }

    @Override
    @Transactional
    public int[] addUser(List<User> list) {
        return userDao.addUser(list);
    }


    @Override
    public User findByUserId(Integer userId) {
        return userJpaDao.findById(userId);
    }


    private static Integer MATCH_Apply_NORMAL=1;
}
