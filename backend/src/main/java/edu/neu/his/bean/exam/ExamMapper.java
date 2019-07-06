package edu.neu.his.bean.exam;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "ExamMapper")
public interface ExamMapper {
    @Delete({
        "delete from exam",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into exam (medical_record_id, `type`, ",
        "create_time, user_id, ",
        "`status`)",
        "values (#{medical_record_id,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, ",
        "#{create_time,jdbcType=VARCHAR}, #{user_id,jdbcType=INTEGER}, ",
        "#{status,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Exam record);

    @Select({
        "select",
        "id, medical_record_id, `type`, create_time, user_id, `status`",
        "from exam",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    Exam selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, medical_record_id, `type`, create_time, user_id, `status`",
        "from exam"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    List<Exam> selectAll();

    @Update({
        "update exam",
        "set medical_record_id = #{medical_record_id,jdbcType=INTEGER},",
          "`type` = #{type,jdbcType=INTEGER},",
          "create_time = #{create_time,jdbcType=VARCHAR},",
          "user_id = #{user_id,jdbcType=INTEGER},",
          "`status` = #{status,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Exam record);

    @Select({
            "select",
            "id, medical_record_id, `type`, create_time, user_id, `status`",
            "from exam",
            "where medical_record_id = #{medicalRecordId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    List<Exam> selectByMedicalRecordId(int medicalRecordId);
}