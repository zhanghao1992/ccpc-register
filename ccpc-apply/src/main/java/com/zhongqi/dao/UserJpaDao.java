package com.zhongqi.dao;

import com.zhongqi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ningcs on 2017/7/4.
 */
@Repository
public interface UserJpaDao extends JpaRepository<User,Integer> {
    public User findByIdNumber(String idNumber);
}
