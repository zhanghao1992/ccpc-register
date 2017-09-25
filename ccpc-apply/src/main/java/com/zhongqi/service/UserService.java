package com.zhongqi.service;

import com.zhongqi.entity.User;
import com.zhongqi.model.UserModel;

import java.util.List;

/**
 * Created by ningcs on 2017/7/5.
 */
public interface UserService {

    /**
     * 获取当前用户信息
     */
    public UserModel getCurrentUserInfo(String realName, String idNumber, String mobile,Boolean cutOffStatus);

    /**
     * 获取当前用户信息
     */
    public UserModel getCurrentUser(String idNumber);

    /**
     * 添加用户信息
     */
    public void addUser(String realName,String idNumber,String mobile);

    /**
     * 更新用户信息
     */
    public void updateUser(String idNumber,String mobile,Integer id);

    public int[] addUser(List<User> list );

    //通过userId获取用户信息
    public User findByUserId(Integer userId);

    //手机号不能重复
    public User findByMobile(String mobile);
}
