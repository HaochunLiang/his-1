package edu.neu.his.auto;

import edu.neu.his.bean.diagnosis.MedicalRecordDiagnoseTemplate;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface AutoMedicalRecordDiagnoseTemplateMapper {
    @Delete({
        "delete from medical_record_diagnose_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into medical_record_diagnose_template (title, user_id, ",
        "department_id, `type`, ",
        "create_time)",
        "values (#{title,jdbcType=VARCHAR}, #{user_id,jdbcType=INTEGER}, ",
        "#{department_id,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, ",
        "#{create_time,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(MedicalRecordDiagnoseTemplate record);

    @Select({
        "select",
        "id, title, user_id, department_id, `type`, create_time",
        "from medical_record_diagnose_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    MedicalRecordDiagnoseTemplate selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, title, user_id, department_id, `type`, create_time",
        "from medical_record_diagnose_template"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    List<MedicalRecordDiagnoseTemplate> selectAll();

    @Update({
        "update medical_record_diagnose_template",
        "set title = #{title,jdbcType=VARCHAR},",
          "user_id = #{user_id,jdbcType=INTEGER},",
          "department_id = #{department_id,jdbcType=INTEGER},",
          "`type` = #{type,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MedicalRecordDiagnoseTemplate record);
}