package edu.neu.his.auto;

import edu.neu.his.bean.diagnosis.MedicalRecordDiagnoseTemplateItem;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface AutoMedicalRecordDiagnoseTemplateItemMapper {
    @Delete({
        "delete from medical_record_diagnose_template_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into medical_record_diagnose_template_item (medical_record_diagnose_template_id, ",
        "disease_id, disease_name, ",
        "disease_code, diagnose_type, ",
        "main_symptom, suspect, syndrome_differentiation)",
        "values (#{medical_record_diagnose_template_id,jdbcType=INTEGER}, ",
        "#{disease_id,jdbcType=INTEGER}, #{disease_name,jdbcType=VARCHAR}, ",
        "#{disease_code,jdbcType=VARCHAR}, #{diagnose_type,jdbcType=VARCHAR}, ",
        "#{main_symptom,jdbcType=BIT}, #{suspect,jdbcType=BIT}, #{syndrome_differentiation,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(MedicalRecordDiagnoseTemplateItem record);

    @Select({
        "select",
        "id, medical_record_diagnose_template_id, disease_id, disease_name, disease_code, ",
        "diagnose_type, main_symptom, suspect, syndrome_differentiation",
        "from medical_record_diagnose_template_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_diagnose_template_id", property="medical_record_diagnose_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="disease_id", property="disease_id", jdbcType=JdbcType.INTEGER),
        @Result(column="disease_name", property="disease_name", jdbcType=JdbcType.VARCHAR),
        @Result(column="disease_code", property="disease_code", jdbcType=JdbcType.VARCHAR),
        @Result(column="diagnose_type", property="diagnose_type", jdbcType=JdbcType.VARCHAR),
        @Result(column="main_symptom", property="main_symptom", jdbcType=JdbcType.BIT),
        @Result(column="suspect", property="suspect", jdbcType=JdbcType.BIT),
        @Result(column="syndrome_differentiation", property="syndrome_differentiation", jdbcType=JdbcType.LONGVARCHAR)
    })
    MedicalRecordDiagnoseTemplateItem selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, medical_record_diagnose_template_id, disease_id, disease_name, disease_code, ",
        "diagnose_type, main_symptom, suspect, syndrome_differentiation",
        "from medical_record_diagnose_template_item"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_diagnose_template_id", property="medical_record_diagnose_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="disease_id", property="disease_id", jdbcType=JdbcType.INTEGER),
        @Result(column="disease_name", property="disease_name", jdbcType=JdbcType.VARCHAR),
        @Result(column="disease_code", property="disease_code", jdbcType=JdbcType.VARCHAR),
        @Result(column="diagnose_type", property="diagnose_type", jdbcType=JdbcType.VARCHAR),
        @Result(column="main_symptom", property="main_symptom", jdbcType=JdbcType.BIT),
        @Result(column="suspect", property="suspect", jdbcType=JdbcType.BIT),
        @Result(column="syndrome_differentiation", property="syndrome_differentiation", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MedicalRecordDiagnoseTemplateItem> selectAll();

    @Update({
        "update medical_record_diagnose_template_item",
        "set medical_record_diagnose_template_id = #{medical_record_diagnose_template_id,jdbcType=INTEGER},",
          "disease_id = #{disease_id,jdbcType=INTEGER},",
          "disease_name = #{disease_name,jdbcType=VARCHAR},",
          "disease_code = #{disease_code,jdbcType=VARCHAR},",
          "diagnose_type = #{diagnose_type,jdbcType=VARCHAR},",
          "main_symptom = #{main_symptom,jdbcType=BIT},",
          "suspect = #{suspect,jdbcType=BIT},",
          "syndrome_differentiation = #{syndrome_differentiation,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MedicalRecordDiagnoseTemplateItem record);
}