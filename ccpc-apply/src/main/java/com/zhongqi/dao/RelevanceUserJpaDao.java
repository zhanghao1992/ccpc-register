package com.zhongqi.dao;

import com.zhongqi.entity.RelevanceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ningcs on 2017/7/4.
 */
@Repository
public interface RelevanceUserJpaDao extends JpaRepository<RelevanceUser,Integer> {
    public RelevanceUser findByUserIdCode(String userIdCode);
    public RelevanceUser findByUserId(Integer  userId);

}
