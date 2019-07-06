package edu.neu.his.bean.outpatientCharges;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "ChargeAndRefundMapper")
public interface ChargeAndRefundMapper {
    @Select("SELECT * from outpatient_charges_record where medical_record_id = #{medical_record_id}")
    List<OutpatientChargesRecord> findByMedicalRecordId(@Param("medical_record_id") int medical_record_id);

    @Select("SELECT * from outpatient_charges_record where medical_record_id = #{medical_record_id} and id = #{id}")
    OutpatientChargesRecord findByMedicalRecordIdAndId(@Param("medical_record_id") int medical_record_id, @Param("id") int id);

    @Select("SELECT * from outpatient_charges_record where medical_record_id = #{medical_record_id} and create_time > #{start_time} and create_time < #{end_time}")
    List<OutpatientChargesRecord> findByMedicalRecordIdAndTime(@Param("medical_record_id") int medical_record_id,
                                                               @Param("start_time") String start_time, @Param("end_time") String end_time);

    @Select("SELECT * from outpatient_charges_record where item_id = #{item_id}")
    OutpatientChargesRecord findByItemId(@Param("item_id") int item_id);

    @Select("SELECT * from outpatient_charges_record where item_id = #{item_id} and type = #{type}")
    OutpatientChargesRecord findByItemIdAndType(@Param("item_id") int item_id,@Param("type") int type);

    @Select("SELECT * from outpatient_charges_record where medical_record_id = #{medical_record_id} and item_id = #{item_id} and status = #{status}")
    OutpatientChargesRecord findByMedicalRecordIdAndItemId(@Param("medical_record_id") int medical_record_id,
                                                                 @Param("item_id") int item_id, @Param("status") String status);

    @Select("SELECT * from outpatient_charges_record where medical_record_id = #{medical_record_id} and type = #{type} " +
            "and create_time > #{start_time} and create_time < #{end_time} and status = #{status}")
    List<OutpatientChargesRecord> findExamRecordByMedicalRecordId(@Param("medical_record_id") int medical_record_id,
                                                                  @Param("type") int type,
                                                                  @Param("start_time") String start_time, @Param("end_time") String end_time,
                                                                  @Param("status") String status);
}
