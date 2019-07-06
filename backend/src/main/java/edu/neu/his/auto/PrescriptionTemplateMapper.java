package edu.neu.his.auto;

import edu.neu.his.bean.prescriptionTemplate.PrescriptionTemplate;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface PrescriptionTemplateMapper {
    @Delete({
        "delete from prescription_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into prescription_template (template_name, create_time, ",
        "display_type, `type`, ",
        "user_id, department_id)",
        "values (#{template_name,jdbcType=VARCHAR}, #{create_time,jdbcType=VARCHAR}, ",
        "#{display_type,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, ",
        "#{user_id,jdbcType=INTEGER}, #{department_id,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(PrescriptionTemplate record);

    @Select({
        "select",
        "id, template_name, create_time, display_type, `type`, user_id, department_id",
        "from prescription_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="template_name", property="template_name", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="display_type", property="display_type", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER)
    })
    PrescriptionTemplate selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, template_name, create_time, display_type, `type`, user_id, department_id",
        "from prescription_template"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="template_name", property="template_name", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="display_type", property="display_type", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER)
    })
    List<PrescriptionTemplate> selectAll();

    @Update({
        "update prescription_template",
        "set template_name = #{template_name,jdbcType=VARCHAR},",
          "create_time = #{create_time,jdbcType=VARCHAR},",
          "display_type = #{display_type,jdbcType=INTEGER},",
          "`type` = #{type,jdbcType=INTEGER},",
          "user_id = #{user_id,jdbcType=INTEGER},",
          "department_id = #{department_id,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PrescriptionTemplate record);

    @Select({
            "select",
            "id, template_name, create_time, display_type, `type`, user_id, department_id",
            "from prescription_template",
            "where template_name=#{name}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="template_name", property="template_name", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="display_type", property="display_type", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER)
    })
   PrescriptionTemplate selectByName(String templateName);
}