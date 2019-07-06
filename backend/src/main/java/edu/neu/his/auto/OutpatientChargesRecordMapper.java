package edu.neu.his.auto;

import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecord;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface OutpatientChargesRecordMapper {
    @Delete({
        "delete from outpatient_charges_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into outpatient_charges_record (medical_record_id, bill_record_id, ",
        "item_id, `type`, expense_classification_id, ",
        "`status`, quantity, ",
        "cost, execute_department_id, ",
        "create_time, collect_time, ",
        "return_time, create_user_id, ",
        "collect_user_id, return_user_id)",
        "values (#{medical_record_id,jdbcType=INTEGER}, #{bill_record_id,jdbcType=INTEGER}, ",
        "#{item_id,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, #{expense_classification_id,jdbcType=INTEGER}, ",
        "#{status,jdbcType=VARCHAR}, #{quantity,jdbcType=INTEGER}, ",
        "#{cost,jdbcType=REAL}, #{execute_department_id,jdbcType=INTEGER}, ",
        "#{create_time,jdbcType=VARCHAR}, #{collect_time,jdbcType=VARCHAR}, ",
        "#{return_time,jdbcType=VARCHAR}, #{create_user_id,jdbcType=INTEGER}, ",
        "#{collect_user_id,jdbcType=INTEGER}, #{return_user_id,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(OutpatientChargesRecord record);

    @Select({
        "select",
        "id, medical_record_id, bill_record_id, item_id, `type`, expense_classification_id, ",
        "`status`, quantity, cost, execute_department_id, create_time, collect_time, ",
        "return_time, create_user_id, collect_user_id, return_user_id",
        "from outpatient_charges_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="bill_record_id", property="bill_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="item_id", property="item_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="expense_classification_id", property="expense_classification_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER),
        @Result(column="cost", property="cost", jdbcType=JdbcType.REAL),
        @Result(column="execute_department_id", property="execute_department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="collect_time", property="collect_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="return_time", property="return_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="create_user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="collect_user_id", property="collect_user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="return_user_id", property="return_user_id", jdbcType=JdbcType.INTEGER)
    })
    OutpatientChargesRecord selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, medical_record_id, bill_record_id, item_id, `type`, expense_classification_id, ",
        "`status`, quantity, cost, execute_department_id, create_time, collect_time, ",
        "return_time, create_user_id, collect_user_id, return_user_id",
        "from outpatient_charges_record"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="bill_record_id", property="bill_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="item_id", property="item_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="expense_classification_id", property="expense_classification_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER),
        @Result(column="cost", property="cost", jdbcType=JdbcType.REAL),
        @Result(column="execute_department_id", property="execute_department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="collect_time", property="collect_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="return_time", property="return_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="create_user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="collect_user_id", property="collect_user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="return_user_id", property="return_user_id", jdbcType=JdbcType.INTEGER)
    })
    List<OutpatientChargesRecord> selectAll();

    @Select({
            "select",
            "id, medical_record_id, bill_record_id, item_id, `type`, expense_classification_id, ",
            "`status`, quantity, cost, execute_department_id, create_time, collect_time, ",
            "return_time, create_user_id, collect_user_id, return_user_id",
            "from outpatient_charges_record " +
                    "where type = #{type} and item_id = #{item_id}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
            @Result(column="bill_record_id", property="bill_record_id", jdbcType=JdbcType.INTEGER),
            @Result(column="item_id", property="item_id", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="expense_classification_id", property="expense_classification_id", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
            @Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER),
            @Result(column="cost", property="cost", jdbcType=JdbcType.REAL),
            @Result(column="execute_department_id", property="execute_department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="collect_time", property="collect_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="return_time", property="return_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_user_id", property="create_user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="collect_user_id", property="collect_user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="return_user_id", property="return_user_id", jdbcType=JdbcType.INTEGER)
    })
    OutpatientChargesRecord findByItemIdAndType(@Param("item_id") int item_id,@Param("type") int type);

    @Update({
        "update outpatient_charges_record",
        "set medical_record_id = #{medical_record_id,jdbcType=INTEGER},",
          "bill_record_id = #{bill_record_id,jdbcType=INTEGER},",
          "item_id = #{item_id,jdbcType=INTEGER},",
          "`type` = #{type,jdbcType=INTEGER},",
          "expense_classification_id = #{expense_classification_id,jdbcType=INTEGER},",
          "`status` = #{status,jdbcType=VARCHAR},",
          "quantity = #{quantity,jdbcType=INTEGER},",
          "cost = #{cost,jdbcType=REAL},",
          "execute_department_id = #{execute_department_id,jdbcType=INTEGER},",
          "create_time = #{create_time,jdbcType=VARCHAR},",
          "collect_time = #{collect_time,jdbcType=VARCHAR},",
          "return_time = #{return_time,jdbcType=VARCHAR},",
          "create_user_id = #{create_user_id,jdbcType=INTEGER},",
          "collect_user_id = #{collect_user_id,jdbcType=INTEGER},",
          "return_user_id = #{return_user_id,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(OutpatientChargesRecord record);
}