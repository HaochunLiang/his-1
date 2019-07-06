package edu.neu.his.auto;

import edu.neu.his.bean.drug.Drug;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface AutoDrugMapper {
    @Delete({
        "delete from drug",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into drug (id, code, `name`, ",
        "format, unit, manufacturer, ",
        "dosage_form, `type`, ",
        "price, pinyin, stock)",
        "values (#{id}, #{code,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{format,jdbcType=VARCHAR}, #{unit,jdbcType=VARCHAR}, #{manufacturer,jdbcType=VARCHAR}, ",
        "#{dosage_form,jdbcType=VARCHAR}, #{type,jdbcType=VARCHAR}, ",
        "#{price,jdbcType=REAL}, #{pinyin,jdbcType=VARCHAR}, #{stock,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Drug record);

    @Select({
        "select",
        "id, code, `name`, format, unit, manufacturer, dosage_form, `type`, price, pinyin, ",
        "stock",
        "from drug",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="format", property="format", jdbcType=JdbcType.VARCHAR),
        @Result(column="unit", property="unit", jdbcType=JdbcType.VARCHAR),
        @Result(column="manufacturer", property="manufacturer", jdbcType=JdbcType.VARCHAR),
        @Result(column="dosage_form", property="dosage_form", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.REAL),
        @Result(column="pinyin", property="pinyin", jdbcType=JdbcType.VARCHAR),
        @Result(column="stock", property="stock", jdbcType=JdbcType.INTEGER)
    })
    Drug selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, code, `name`, format, unit, manufacturer, dosage_form, `type`, price, pinyin, ",
        "stock",
        "from drug"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="format", property="format", jdbcType=JdbcType.VARCHAR),
        @Result(column="unit", property="unit", jdbcType=JdbcType.VARCHAR),
        @Result(column="manufacturer", property="manufacturer", jdbcType=JdbcType.VARCHAR),
        @Result(column="dosage_form", property="dosage_form", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.REAL),
        @Result(column="pinyin", property="pinyin", jdbcType=JdbcType.VARCHAR),
        @Result(column="stock", property="stock", jdbcType=JdbcType.INTEGER)
    })
    List<Drug> selectAll();

    @Update({
        "update drug",
        "set code = #{code,jdbcType=VARCHAR},",
          "`name` = #{name,jdbcType=VARCHAR},",
          "format = #{format,jdbcType=VARCHAR},",
          "unit = #{unit,jdbcType=VARCHAR},",
          "manufacturer = #{manufacturer,jdbcType=VARCHAR},",
          "dosage_form = #{dosage_form,jdbcType=VARCHAR},",
          "`type` = #{type,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=REAL},",
          "pinyin = #{pinyin,jdbcType=VARCHAR},",
          "stock = #{stock,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Drug record);
}