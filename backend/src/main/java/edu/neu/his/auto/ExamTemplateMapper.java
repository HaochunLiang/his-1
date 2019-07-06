package edu.neu.his.auto;

import edu.neu.his.bean.examTemplate.ExamTemplate;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface ExamTemplateMapper {
    @Delete({
        "delete from exam_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into exam_template (template_name, user_id, ",
        "department_id, display_type, ",
        "`type`, create_time)",
        "values (#{template_name,jdbcType=VARCHAR}, #{user_id,jdbcType=INTEGER}, ",
        "#{department_id,jdbcType=INTEGER}, #{display_type,jdbcType=INTEGER}, ",
        "#{type,jdbcType=INTEGER}, #{create_time,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(ExamTemplate record);

    @Select({
        "select",
        "id, template_name, user_id, department_id, display_type, `type`, create_time",
        "from exam_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="template_name", property="template_name", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="display_type", property="display_type", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    ExamTemplate selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, template_name, user_id, department_id, display_type, `type`, create_time",
        "from exam_template"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="template_name", property="template_name", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="display_type", property="display_type", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    List<ExamTemplate> selectAll();

    @Update({
        "update exam_template",
        "set template_name = #{template_name,jdbcType=VARCHAR},",
          "user_id = #{user_id,jdbcType=INTEGER},",
          "department_id = #{department_id,jdbcType=INTEGER},",
          "display_type = #{display_type,jdbcType=INTEGER},",
          "`type` = #{type,jdbcType=INTEGER},",
          "create_time = #{create_time,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ExamTemplate record);

    @Select({
            "select",
            "id, template_name, user_id, department_id, display_type,  `type`, create_time",
            "from exam_template",
            "where template_name = #{templateName}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="template_name", property="template_name", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="display_type", property="display_type", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    ExamTemplate selectByName(String templateName);

    @Delete({
            "delete from exam_template_item",
            "where exam_template_id = #{id,jdbcType=INTEGER}"
    })
    int deleteItemByPrimaryKey(Integer id);

}