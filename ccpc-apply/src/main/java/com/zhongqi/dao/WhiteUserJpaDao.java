package com.zhongqi.dao;

import com.zhongqi.entity.WhiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ningcs on 2017/7/4.
 */
@Repository
public interface WhiteUserJpaDao extends JpaRepository<WhiteUser,Integer> {
    public WhiteUser findByIdNumberAndRealName(String idNumber, String realName);
}
