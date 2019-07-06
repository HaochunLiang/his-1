package edu.neu.his.auto;

import edu.neu.his.bean.diagnosis.MedicalRecordDiagnose;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface AutoMedicalRecordDiagnoseMapper {
    @Delete({
        "delete from medical_record_diagnose",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into medical_record_diagnose (medical_record_id)",
        "values (#{medical_record_id,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(MedicalRecordDiagnose record);

    @Select({
        "select",
        "id, medical_record_id",
        "from medical_record_diagnose",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER)
    })
    MedicalRecordDiagnose selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, medical_record_id",
        "from medical_record_diagnose"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER)
    })
    List<MedicalRecordDiagnose> selectAll();

    @Update({
        "update medical_record_diagnose",
        "set medical_record_id = #{medical_record_id,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MedicalRecordDiagnose record);
}