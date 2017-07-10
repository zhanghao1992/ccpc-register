package com.zhongqi.dao;

import com.zhongqi.entity.MatchApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ningcs on 2017/7/4.
 */
@Repository
public interface MatchApplyJpaDao extends JpaRepository<MatchApply,Integer> {
    public MatchApply findByIdNumberAndStatus(String idNumber,Integer status);

}
