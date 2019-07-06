package edu.neu.his.bean.exam;

import java.util.List;

import edu.neu.his.util.Importable;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "ExamItemMapper")
public interface ExamItemMapper  {
    @Delete({
        "delete from exam_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into exam_item (exam_id, non_drug_item_id, ",
        "`status`)",
        "values (#{exam_id,jdbcType=INTEGER}, #{non_drug_item_id,jdbcType=INTEGER}, ",
        "#{status,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(ExamItem record);

    @Select({
        "select",
        "id, exam_id, non_drug_item_id, `status`",
        "from exam_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exam_id", property="exam_id", jdbcType=JdbcType.INTEGER),
        @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    ExamItem selectByPrimaryKey(Integer id);

       @Select({
            "select",
            "id, exam_id, non_drug_item_id, `status`",
            "from exam_item",
            "where exam_id = #{examId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="exam_id", property="exam_id", jdbcType=JdbcType.INTEGER),
            @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    List<ExamItem> selectByExamId(Integer examId);

    @Select({
            "select",
            "id, exam_id, non_drug_item_id, `status`",
            "from exam_item",
            "where non_drug_item_id = #{non_drug_item_id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="exam_id", property="exam_id", jdbcType=JdbcType.INTEGER),
            @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    ExamItem selectByItemId(Integer non_drug_item_id);

    @Select({
            "select",
            "id, exam_id, non_drug_item_id, `status`",
            "from exam_item",
            "where non_drug_item_id = #{nonDrugId, jdbcType=INTEGER}",
            "and exam_id = #{examId, jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="exam_id", property="exam_id", jdbcType=JdbcType.INTEGER),
            @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    ExamItem selectOneByDetail(Integer nonDrugId, Integer examId);


    @Select({
        "select",
        "id, exam_id, non_drug_item_id, `status`",
        "from exam_item"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exam_id", property="exam_id", jdbcType=JdbcType.INTEGER),
        @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    List<ExamItem> selectAll();

    @Update({
        "update exam_item",
        "set exam_id = #{exam_id,jdbcType=INTEGER},",
          "non_drug_item_id = #{non_drug_item_id,jdbcType=INTEGER},",
          "`status` = #{status,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ExamItem record);


    @Select({
            "select exam_id from exam_item ",
            "where id = #{id}"
    })
    int selectExamIdByExamItemId(@Param("id") int id);
}