package edu.neu.his.auto;

import edu.neu.his.bean.prescription.PrescriptionItem;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface AutoPrescriptionItemMapper {
    @Delete({
        "delete from prescription_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into prescription_item (`usage`, dosage, ",
        "frequency, day_count, ",
        "times, amount, prescription_id, ",
        "drug_id, `status`, ",
        "note)",
        "values (#{usage,jdbcType=VARCHAR}, #{dosage,jdbcType=VARCHAR}, ",
        "#{frequency,jdbcType=VARCHAR}, #{day_count,jdbcType=INTEGER}, ",
        "#{times,jdbcType=INTEGER}, #{amount,jdbcType=INTEGER}, #{prescription_id,jdbcType=INTEGER}, ",
        "#{drug_id,jdbcType=INTEGER}, #{status,jdbcType=VARCHAR}, ",
        "#{note,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(PrescriptionItem record);

    @Select({
        "select",
        "id, `usage`, dosage, frequency, day_count, times, amount, prescription_id, drug_id, ",
        "`status`, note",
        "from prescription_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="usage", property="usage", jdbcType=JdbcType.VARCHAR),
        @Result(column="dosage", property="dosage", jdbcType=JdbcType.VARCHAR),
        @Result(column="frequency", property="frequency", jdbcType=JdbcType.VARCHAR),
        @Result(column="day_count", property="day_count", jdbcType=JdbcType.INTEGER),
        @Result(column="times", property="times", jdbcType=JdbcType.INTEGER),
        @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
        @Result(column="prescription_id", property="prescription_id", jdbcType=JdbcType.INTEGER),
        @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="note", property="note", jdbcType=JdbcType.LONGVARCHAR)
    })
    PrescriptionItem selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, `usage`, dosage, frequency, day_count, times, amount, prescription_id, drug_id, ",
        "`status`, note",
        "from prescription_item"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="usage", property="usage", jdbcType=JdbcType.VARCHAR),
        @Result(column="dosage", property="dosage", jdbcType=JdbcType.VARCHAR),
        @Result(column="frequency", property="frequency", jdbcType=JdbcType.VARCHAR),
        @Result(column="day_count", property="day_count", jdbcType=JdbcType.INTEGER),
        @Result(column="times", property="times", jdbcType=JdbcType.INTEGER),
        @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
        @Result(column="prescription_id", property="prescription_id", jdbcType=JdbcType.INTEGER),
        @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="note", property="note", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<PrescriptionItem> selectAll();

    @Update({
        "update prescription_item",
        "set `usage` = #{usage,jdbcType=VARCHAR},",
          "dosage = #{dosage,jdbcType=VARCHAR},",
          "frequency = #{frequency,jdbcType=VARCHAR},",
          "day_count = #{day_count,jdbcType=INTEGER},",
          "times = #{times,jdbcType=INTEGER},",
          "amount = #{amount,jdbcType=INTEGER},",
          "prescription_id = #{prescription_id,jdbcType=INTEGER},",
          "drug_id = #{drug_id,jdbcType=INTEGER},",
          "`status` = #{status,jdbcType=VARCHAR},",
          "note = #{note,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PrescriptionItem record);


    @Select({
            "select",
            "*",
            "from prescription_item",
            "where prescription_id = #{prescriptionId,jdbcType=INTEGER}",
            "and drug_id = #{drugId}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
            @Result(column="prescription_id", property="prescription_id", jdbcType=JdbcType.INTEGER),
            @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
            @Result(column="note", property="note", jdbcType=JdbcType.LONGVARCHAR)
    })
    PrescriptionItem selectByDetail(int prescriptionId, int drugId);

    @Select({
            "select",
            "*",
            "from prescription_item",
            "where prescription_id = #{prescriptionId,jdbcType=INTEGER}",
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
            @Result(column="prescription_id", property="prescription_id", jdbcType=JdbcType.INTEGER),
            @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
            @Result(column="note", property="note", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<PrescriptionItem> selectByPrescriptionId(int prescriptionId);
}