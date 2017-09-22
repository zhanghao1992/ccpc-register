package com.zhongqi.service.impl;

import com.zhongqi.dao.*;
import com.zhongqi.dto.ResponseRatingForQueryInfo;
import com.zhongqi.entity.*;
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

    private static Integer MATCH_Apply_NORMAL = 1;
    @Autowired
    UserDao userDao;
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
    private RatingPersonLeverDetailJpaDao ratingPersonLeverDetailJpaDao;

    @Override
    public UserModel getCurrentUserInfo(String realName, String idNumber, String mobile) {
        User user = null;
        UserModel userModel = null;
        user = userJpaDao.findByIdNumber(idNumber);
        if (user == null) {
            this.addUser(realName, idNumber, mobile);
        } else {
            this.updateUser(idNumber, mobile, user.getId());
        }
        user = userJpaDao.findByIdNumber(idNumber);
        if (user != null) {
            userModel = this.getUserModel(user);
            userModel = this.getMatchApply(userModel, idNumber);
        }
        return userModel;

    }

    @Override
    public UserModel getCurrentUser(String idNumber) {

        User user = null;
        UserModel userModel = null;

        // 通过身份证号获取用户
        user = userJpaDao.findByIdNumber(idNumber);
        if (user != null) {
            userModel = this.getUserModel(user);
            userModel = this.getMatchApply(userModel, idNumber);
        }
        return userModel;
    }

    @Override
    @Transactional
    public void addUser(String realName, String idNumber, String mobile) {
        User user = new User();
        user.setIdNumber(idNumber);
        user.setRealName(realName);
        user.setMobile(mobile);
        user.setQueryTime(new Date());
        userJpaDao.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void updateUser(String idNumber, String mobile, Integer id) {
        User user = userJpaDao.findByIdNumber(idNumber);
        user.setMobile(mobile);
        userJpaDao.saveAndFlush(user);
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

    // 获取用户ID对应的加密码（如果没有就创建一个）
    private String getRelevanceUser(Integer userId) {
        RelevanceUser relevanceUser = null;
        if (userId != null && userId != 0) {
            relevanceUser = matchApplyService.findByUserId(userId);
            if (relevanceUser == null) {
                relevanceUser = matchApplyService.createRelevanceUserId(userId);
            }
        }
        relevanceUser = matchApplyService.findByUserId(userId);
        return relevanceUser.getUserIdCode();

    }

    // 获取用户Model对象
    private UserModel getUserModel(User user) {
        UserModel userModel = null;

        // 获取用户ID对应的加密码（如果没有就创建一个）
        String userIdCode = this.getRelevanceUser(user.getId());

        userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setUserId(userIdCode);
        userModel.setMobile(user.getMobile());
        userModel.setRealName(user.getRealName());
        userModel.setIdNumber(user.getIdNumber());

        return userModel;
    }

    // 获取用户Model详细信息
    private UserModel getMatchApply(UserModel userModel, String idNumber) {
        ResponseRatingForQueryInfo responseRatingForQueryInfo = null;
        if (BaseUtils.compareCurrentTime(cutOffDate)) {
            // 通过接口获取信息
            responseRatingForQueryInfo = masterPointQueryService.findMasterPointsRank(idNumber);
        } else {
            // 查询本地数据库信息
            responseRatingForQueryInfo = matchApplyService.findMasterPointsRank(idNumber);
        }

        // 排名、等级信息
        if (responseRatingForQueryInfo != null) {
            RatingPersonLeverDetail ratingPersonLeverDetail = ratingPersonLeverDetailJpaDao.findByLevelName(responseRatingForQueryInfo.getLevel_name());
            if (ratingPersonLeverDetail != null) {
                userModel.setLevelCode(ratingPersonLeverDetail.getGradeCode().toString());
                userModel.setLevelName(responseRatingForQueryInfo.getLevel_name());
            }
            userModel.setMasterPointRank(responseRatingForQueryInfo.getRanking());
        }

        // 报名信息
        MatchApply matchApply = matchApplyJpaDao.findByIdNumberAndStatus(idNumber, MATCH_Apply_NORMAL);
        if (matchApply != null) {
            MatchDay matchDay = matchDayJpaDao.findByMatchDayId(matchApply.getMatchDayId());
            if (matchDay != null) {
                userModel.setMatchDayId(matchApply.getMatchDayId());
                userModel.setDayInfo(matchDay.getDayInfo());
                userModel.setDayInfoDetail(matchDay.getDayInfoDetail());
            }
            MatchPlace matchPlace = matchPlaceJpaDao.findByMatchPlaceId(matchApply.getMatchPlaceId());
            if (matchPlace != null) {
                userModel.setMatchPlaceId(matchApply.getMatchPlaceId());
                userModel.setPlace(matchPlace.getPlace());
                userModel.setPlaceDetail(matchPlace.getPlaceDetail());
            }
        }
        return userModel;
    }
}
