package edu.neu.his.bean.schedule;

import edu.neu.his.bean.schedule.DoctorSchedulingShift;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "DoctorSchedulingShiftMapper")
public interface DoctorSchedulingShiftMapper {

    @Select("SELECT * FROM doctor_scheduling_shift")
    List<DoctorSchedulingShift> findAll();

    @Insert("INSERT INTO doctor_scheduling_shift (shift,sort) values (#{shift},#{sort})")
    void addDoctorSchedulingShift(DoctorSchedulingShift doctorSchedulingShift);

    @Delete("DELETE FROM doctor_scheduling_shift WHERE shift = #{shift}")
    void deleteDoctorSchedulingShiftByName(@Param("shift") String shift);

    @Update("UPDATE doctor_scheduling_shift set shift = #{shift}, sort = #{sort} WHERE id = #{id}")
    void updateDoctorSchedulingShift(DoctorSchedulingShift doctorSchedulingShift);
}
