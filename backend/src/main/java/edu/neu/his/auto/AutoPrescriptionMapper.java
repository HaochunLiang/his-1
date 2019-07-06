package edu.neu.his.auto;

import edu.neu.his.bean.prescription.Prescription;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface AutoPrescriptionMapper {
    @Delete({
        "delete from prescription",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into prescription (medical_record_id, `type`, ",
        "`status`, create_time, ",
        "user_id)",
        "values (#{medical_record_id,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, ",
        "#{status,jdbcType=VARCHAR}, #{create_time,jdbcType=VARCHAR}, ",
        "#{user_id,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Prescription record);

    @Select({
        "select",
        "id, medical_record_id, `type`, `status`, create_time, user_id",
        "from prescription",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER)
    })
    Prescription selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, medical_record_id, `type`, `status`, create_time, user_id",
        "from prescription"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER)
    })
    List<Prescription> selectAll();

    @Update({
        "update prescription",
        "set medical_record_id = #{medical_record_id,jdbcType=INTEGER},",
          "`type` = #{type,jdbcType=INTEGER},",
          "`status` = #{status,jdbcType=VARCHAR},",
          "create_time = #{create_time,jdbcType=VARCHAR},",
          "user_id = #{user_id,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Prescription record);
}