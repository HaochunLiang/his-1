package edu.neu.his.auto;

import edu.neu.his.bean.examTemplate.ExamTemplateItem;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface ExamTemplateItemMapper {
    @Delete({
        "delete from exam_template_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into exam_template_item (exam_template_id, non_drug_item_id)",
        "values (#{exam_template_id,jdbcType=INTEGER}, #{non_drug_item_id,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(ExamTemplateItem record);

    @Select({
        "select",
        "id, exam_template_id, non_drug_item_id",
        "from exam_template_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exam_template_id", property="exam_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER)
    })
    ExamTemplateItem selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, exam_template_id, non_drug_item_id",
        "from exam_template_item"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exam_template_id", property="exam_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER)
    })
    List<ExamTemplateItem> selectAll();

    @Update({
        "update exam_template_item",
        "set exam_template_id = #{exam_template_id,jdbcType=INTEGER},",
          "non_drug_item_id = #{non_drug_item_id,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ExamTemplateItem record);

    @Select({
            "select",
            "id, exam_template_id, non_drug_item_id",
            "from exam_template_item",
            "where exam_template_id = #{templateId}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="exam_template_id", property="exam_template_id", jdbcType=JdbcType.INTEGER),
            @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER)
    })
    List<ExamTemplateItem> selectByTemplateId(int templateId);

    @Select({
            "select",
            "id, exam_template_id, non_drug_item_id",
            "from exam_template_item",
            "where non_drug_item_id = #{nonDrugId, jdbcType=INTEGER}",
            "and exam_template_id = #{examId, jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="exam_template_id", property="exam_template_id", jdbcType=JdbcType.INTEGER),
            @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER),
    })
    ExamTemplateItem selectOneByDetail(Integer nonDrugId, int examId);
}