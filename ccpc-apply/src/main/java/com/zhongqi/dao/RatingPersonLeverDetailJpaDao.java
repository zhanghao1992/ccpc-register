package com.zhongqi.dao;

import com.zhongqi.entity.RatingPersonLeverDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ningcs on 2017/7/5.
 */
@Repository
public interface RatingPersonLeverDetailJpaDao extends JpaRepository<RatingPersonLeverDetail,Integer>{

    public RatingPersonLeverDetail findByLevelName(String  levelName);

    public RatingPersonLeverDetail findByGradeCode(Integer gradeCode);

    @Query(value = "select * from RatingPersonLeverDetail order by id",nativeQuery=true)
    public List<RatingPersonLeverDetail> findRatingPersonLeverDetailList();

}
