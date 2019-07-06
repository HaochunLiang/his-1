package edu.neu.his.bean.workloadstatictic;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Mapper
@Component(value = "WorkloadRecordMapper")
public interface WorkloadRecordMapper {

    @Select("insert into workload_record " +
            "(type,user_id,department_id,fee) " +
            "values (#{type},#{user_id},#{department_id},#{fee})")
    void insert(WorkloadRecord workloadRecord);


    @Select("SELECT user_id,count(*) as count,sum(fee) as fee FROM workload_record " +
            "WHERE CAST(create_time AS DATETIME) >= CAST(#{start_date} AS DATETIME) and CAST(create_time AS DATETIME) <= CAST(#{end_date} AS DATETIME) " +
            "group by user_id")
    List<Map> userStatistic(@Param("start_date") String start_date,@Param("end_date") String end_date);

    @Select("SELECT department_id,count(*) as count,sum(fee) as fee FROM workload_record " +
            "WHERE CAST(create_time AS DATETIME) >= CAST(#{start_date} AS DATETIME) and CAST(create_time AS DATETIME) <= CAST(#{end_date} AS DATETIME) " +
            "group by department_id")
    List<Map> departmentStatistic(@Param("start_date") String start_date,@Param("end_date") String end_date);

    @Select("SELECT type,count(*) as count,sum(fee) as fee FROM workload_record " +
            "WHERE CAST(create_time AS DATETIME) >= CAST(#{start_date} AS DATETIME) and CAST(create_time AS DATETIME) <= CAST(#{end_date} AS DATETIME) " +
            "group by type")
    List<Map> typeStatistic(@Param("start_date") String start_date,@Param("end_date") String end_date);

}
