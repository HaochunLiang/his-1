package edu.neu.his.bean.drug;

import edu.neu.his.bean.drug.Drug;
import edu.neu.his.util.Importable;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface DrugMapper extends Importable<Drug> {
    @Insert({
            "insert into drug (id, code, `name`, ",
            "format, unit, manufacturer, ",
            "dosage_form, `type`, ",
            "price, pinyin, stock)",
            "values (#{id,jdbcType=INTEGER}, #{code,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
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
            "where name like concat('%',#{name,jdbcType=VARCHAR},'%')"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
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
    List<Drug> selectByName(String name);
}
