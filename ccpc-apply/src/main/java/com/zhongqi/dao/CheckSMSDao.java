package com.zhongqi.dao;

import com.zhongqi.entity.CheckSMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by songrenfei on 2017/2/22.
 */
@Repository
public interface CheckSMSDao extends JpaRepository<CheckSMS ,Integer> {

    public List<CheckSMS> findByMobileAndCheckCodeOrderBySendTimeDesc(String mobile, String checkCode);

    public List<CheckSMS> findByMobileOrderBySendTimeDesc(String mobile);

}
