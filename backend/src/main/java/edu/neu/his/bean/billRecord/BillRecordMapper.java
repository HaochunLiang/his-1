package edu.neu.his.bean.billRecord;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类对数据库中的bill_record表进行数据持久化操作
 *
 * @author 王婧怡
 * @version 1.0
 */
@Mapper
@Component(value = "AutoBillRecordMapper")
public interface BillRecordMapper {
    /**
     * 向bill_record表中插入一条数据
     * @param billRecord 需要插入表中的BillRecord对象
     */
    @Insert("INSERT INTO bill_record(medical_record_id, type , print_status, " +
            "cost, should_pay, truely_pay, retail_fee, user_id, create_time) " +
        "VALUES(#{medical_record_id}, #{type}, #{print_status}, " +
            "#{cost}, #{should_pay}, #{truely_pay}, #{retail_fee}, #{user_id}, #{create_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(BillRecord billRecord);

    /**
     * 根据主键id查找相对应的bill_record表中的记录
     * @param id bill_record表的主键，代表票据记录的流水号
     * @return 将查找到的记录生成BillRecord对象返回
     */
    @Select("SELECT * from bill_record where id = #{id}")
    BillRecord find(@Param("id") int id);

    /**
     * 根据用户id和时间段查找该用户在该时间段的所有票据记录
     * @param user_id 需要查找票据记录的用户的id
     * @param start_time 查找时间段的开始时间
     * @param end_time 查找时间段的结束时间
     * @return 返回符合条件的所有票据记录列表
     */
    @Select("SELECT * from bill_record where user_id = #{user_id} and create_time > #{start_time} and create_time < #{end_time}")
    List<BillRecord> findByUserIdAndTime(@Param("user_id") int user_id, @Param("start_time") String start_time, @Param("end_time") String end_time);
}
