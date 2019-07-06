package edu.neu.his.bean.medicalRecord;

import edu.neu.his.bean.medicalRecord.MedicalRecord;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

@Mapper
@Component(value = "MedicalRecordMapper")
public interface MedicalRecordMapper {
    @Delete({
        "delete from medical_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into medical_record (id, create_time, `status`, ",
        "chief_complaint, current_medical_history, ",
        "current_treatment_situation, past_history, ",
        "allergy_history, physical_examination)",
        "values (#{id,jdbcType=INTEGER}, #{create_time,jdbcType=VARCHAR}, #{status,jdbcType=VARCHAR}, ",
        "#{chief_complaint,jdbcType=LONGVARCHAR}, #{current_medical_history,jdbcType=LONGVARCHAR}, ",
        "#{current_treatment_situation,jdbcType=LONGVARCHAR}, #{past_history,jdbcType=LONGVARCHAR}, ",
        "#{allergy_history,jdbcType=LONGVARCHAR}, #{physical_examination,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(MedicalRecord record);

    @Select({
        "select",
        "id, create_time, `status`, chief_complaint, current_medical_history, current_treatment_situation, ",
        "past_history, allergy_history, physical_examination",
        "from medical_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="chief_complaint", property="chief_complaint", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="current_medical_history", property="current_medical_history", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="current_treatment_situation", property="current_treatment_situation", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="past_history", property="past_history", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="allergy_history", property="allergy_history", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="physical_examination", property="physical_examination", jdbcType=JdbcType.LONGVARCHAR)
    })
    MedicalRecord selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, create_time, `status`, chief_complaint, current_medical_history, current_treatment_situation, ",
        "past_history, allergy_history, physical_examination",
        "from medical_record"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="chief_complaint", property="chief_complaint", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="current_medical_history", property="current_medical_history", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="current_treatment_situation", property="current_treatment_situation", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="past_history", property="past_history", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="allergy_history", property="allergy_history", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="physical_examination", property="physical_examination", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MedicalRecord> selectAll();

    @Update({
        "update medical_record",
        "set create_time = #{create_time,jdbcType=VARCHAR},",
          "`status` = #{status,jdbcType=VARCHAR},",
          "chief_complaint = #{chief_complaint,jdbcType=LONGVARCHAR},",
          "current_medical_history = #{current_medical_history,jdbcType=LONGVARCHAR},",
          "current_treatment_situation = #{current_treatment_situation,jdbcType=LONGVARCHAR},",
          "past_history = #{past_history,jdbcType=LONGVARCHAR},",
          "allergy_history = #{allergy_history,jdbcType=LONGVARCHAR},",
          "physical_examination = #{physical_examination,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MedicalRecord record);
}