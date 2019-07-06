package edu.neu.his.bean.registration;

import edu.neu.his.bean.daily.DailyCollect;
import edu.neu.his.bean.daily.DailyDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "RegistrationDailyCollectMapper")
public interface RegistrationDailyCollectMapper {
    @Select("SELECT id, start_time, end_time, user_id FROM daily_collect WHERE user_id = #{uid}")
    List<DailyCollect> findDailyCollectByUid(@Param("uid") int uid);

    @Select("select * from daily_detail where daily_collect_id = #{daily_collect_id}")
    List<DailyDetail> findDailyDetailById(@Param("daily_collect_id") int daily_collect_id);

}
