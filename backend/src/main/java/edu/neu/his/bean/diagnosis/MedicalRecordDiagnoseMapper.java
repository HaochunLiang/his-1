package edu.neu.his.bean.diagnosis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface MedicalRecordDiagnoseMapper {
    @Select({
            "select",
            "id, medical_record_id",
            "from medical_record_diagnose",
            "where medical_record_id = #{medical_record_id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER)
    })
    MedicalRecordDiagnose selectByMedicalRecordId(Integer medical_record_id);
}
