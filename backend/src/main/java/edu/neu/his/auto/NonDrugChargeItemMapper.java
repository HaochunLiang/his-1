package edu.neu.his.auto;

import edu.neu.his.bean.nondrug.NonDrugChargeItem;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface NonDrugChargeItemMapper {
    @Delete({
        "delete from non_drug_charge_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into non_drug_charge_item (code, pinyin, ",
        "format, `name`, fee, ",
        "expense_classification_id, department_id)",
        "values (#{code,jdbcType=VARCHAR}, #{pinyin,jdbcType=VARCHAR}, ",
        "#{format,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{fee,jdbcType=REAL}, ",
        "#{expense_classification_id,jdbcType=INTEGER}, #{department_id,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(NonDrugChargeItem record);

    @Select({
        "select",
        "id, code, pinyin, format, `name`, fee, expense_classification_id, department_id",
        "from non_drug_charge_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="pinyin", property="pinyin", jdbcType=JdbcType.VARCHAR),
        @Result(column="format", property="format", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="fee", property="fee", jdbcType=JdbcType.REAL),
        @Result(column="expense_classification_id", property="expense_classification_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER)
    })
    NonDrugChargeItem selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, code, pinyin, format, `name`, fee, expense_classification_id, department_id",
        "from non_drug_charge_item"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="pinyin", property="pinyin", jdbcType=JdbcType.VARCHAR),
        @Result(column="format", property="format", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="fee", property="fee", jdbcType=JdbcType.REAL),
        @Result(column="expense_classification_id", property="expense_classification_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER)
    })
    List<NonDrugChargeItem> selectAll();

    @Update({
        "update non_drug_charge_item",
        "set code = #{code,jdbcType=VARCHAR},",
          "pinyin = #{pinyin,jdbcType=VARCHAR},",
          "format = #{format,jdbcType=VARCHAR},",
          "`name` = #{name,jdbcType=VARCHAR},",
          "fee = #{fee,jdbcType=REAL},",
          "expense_classification_id = #{expense_classification_id,jdbcType=INTEGER},",
          "department_id = #{department_id,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(NonDrugChargeItem record);
}