package com.zhongqi.dao;

import com.zhongqi.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ningcs on 2017/7/4.
 */

public interface UserDao {
    public int[] addUser(List<User> list );
}
