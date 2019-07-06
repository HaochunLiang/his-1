
package edu.neu.his.bean.prescription;

import edu.neu.his.bean.prescription.Prescription;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "PrescriptionMapper")
public interface PrescriptionMapper {
    @Select({
            "select",
            "id, medical_record_id, `type`, `status`, create_time, user_id",
            "from prescription",
            "where medical_record_id = #{medical_record_id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER)
    })
    List<Prescription> selectByMedicalRecordId(int medical_record_id);



    @Delete({
            "delete",
            "from prescription_item",
            "where prescription_id = #{prescriptionId,jdbcType=INTEGER}"
    })
    void removeAllItems(int prescriptionId);

    @Select({
            "select",
            "id, medical_record_id, `type`, `status`, create_time, user_id",
            "from prescription",
            "where medical_record_id = #{medical_record_id,jdbcType=INTEGER} " +
                    "and type <> #{type,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER)
    })
    List<Prescription> selectByMedicalRecordIdAndType(int medical_record_id,String type);

    @Select("select * from prescription where id = #{id}")
    Prescription selectByPrimaryKey(@Param("id") int id);
}
