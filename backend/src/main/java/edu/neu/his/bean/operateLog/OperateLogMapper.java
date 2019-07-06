package edu.neu.his.bean.operateLog;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "OperateLogMapper")
public interface OperateLogMapper {
    @Insert("INSERT INTO operate_log(user_id, operate_id, type , bill_record_id, fee, create_time) " +
            "VALUES(#{user_id}, #{operate_id}, #{type}, #{bill_record_id}, #{fee}, #{create_time})")
    void insert(OperateLog operateLog);
}
