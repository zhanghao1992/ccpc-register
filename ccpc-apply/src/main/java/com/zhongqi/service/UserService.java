package com.zhongqi.service;

import com.zhongqi.model.UserModel;

/**
 * Created by ningcs on 2017/7/5.
 */
public interface UserService {

    /**
     * 获取当前用户信息
     */
    public UserModel getCurrentUserInfo(String realName, String idNumber, String mobile);

    /**
     * 添加用户信息
     */
    public void addUser(String realName,String idNumber,String mobile);

    /**
     * 更新用户信息
     */
    public void updateUser(String realName,String idNumber,String mobile,Integer id);
}
