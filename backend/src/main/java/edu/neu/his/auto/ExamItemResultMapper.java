package edu.neu.his.auto;

import edu.neu.his.bean.exam.ExamItemResult;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface ExamItemResultMapper {
    @Delete({
        "delete from exam_item_result",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into exam_item_result (`file`, exam_item_id, ",
        "user_id, `result`, ",
        "advice)",
        "values (#{file,jdbcType=VARCHAR}, #{exam_item_id,jdbcType=INTEGER}, ",
        "#{user_id,jdbcType=INTEGER}, #{result,jdbcType=LONGVARCHAR}, ",
        "#{advice,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(ExamItemResult record);

    @Select({
        "select",
        "id, `file`, exam_item_id, user_id, `result`, advice, create_time ",
        "from exam_item_result",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="file", property="file", jdbcType=JdbcType.VARCHAR),
        @Result(column="exam_item_id", property="exam_item_id", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="result", property="result", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="advice", property="advice", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.LONGVARCHAR)
    })
    ExamItemResult selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, `file`, exam_item_id, user_id, `result`, advice, create_time",
        "from exam_item_result"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="file", property="file", jdbcType=JdbcType.VARCHAR),
        @Result(column="exam_item_id", property="exam_item_id", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="result", property="result", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="advice", property="advice", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<ExamItemResult> selectAll();

    @Update({
        "update exam_item_result",
        "set `file` = #{file,jdbcType=VARCHAR},",
          "exam_item_id = #{exam_item_id,jdbcType=INTEGER},",
          "user_id = #{user_id,jdbcType=INTEGER},",
          "`result` = #{result,jdbcType=LONGVARCHAR},",
          "advice = #{advice,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ExamItemResult record);

    @Select({
            "select",
            "id, `file`, exam_item_id, user_id, `result`, advice, create_time ",
            "from exam_item_result",
            "where exam_item_id = #{exam_item_id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="file", property="file", jdbcType=JdbcType.VARCHAR),
            @Result(column="exam_item_id", property="exam_item_id", jdbcType=JdbcType.INTEGER),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="result", property="result", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="advice", property="advice", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.LONGVARCHAR)
    })
    ExamItemResult selectByExamItemId(Integer exam_item_id);
}