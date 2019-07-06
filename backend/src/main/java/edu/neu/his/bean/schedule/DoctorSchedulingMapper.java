package edu.neu.his.bean.schedule;

import edu.neu.his.bean.schedule.DoctorSchedulingInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "DoctorSchedulingMapper")
public interface DoctorSchedulingMapper {

    //@Select("SELECT * FROM doctor_scheduling_info")
    @Select("SELECT user_info.uid as id,user_info.real_name as name,department.name as department_name,"+
            "user_info.title,registration_level.name as registration_Level,doctor_scheduling_shift.shift as shift,expiry_date,scheduling_limit "+
            "FROM doctor_scheduling_info,user_info,department,registration_level,doctor_scheduling_shift "+
            "WHERE doctor_scheduling_info.uid=user_info.uid and user_info.department_id=department.id and "+
            "registration_level.id=user_info.registration_level_id and doctor_scheduling_info.shift_id=doctor_scheduling_shift.id")
    List<DoctorSchedulingInfo> findAll();

    @Insert("INSERT INTO doctor_scheduling_info (uid,shift_id,expiry_date,scheduling_limit,residue) values (#{doctorSchedulingInfo.id},#{shift_id},"+
            "#{doctorSchedulingInfo.expiry_date},#{doctorSchedulingInfo.scheduling_limit},#{doctorSchedulingInfo.scheduling_limit})")
    void addDoctorScheduling(DoctorSchedulingInfo doctorSchedulingInfo, @Param("shift_id") int shift_id);

    @Update("UPDATE user_info set registration_level_id=#{level_id} WHERE uid=#{doctorSchedulingInfo.id}")
    void addDoctorScheduling1(DoctorSchedulingInfo doctorSchedulingInfo, @Param("level_id") int level_id);

    //@Delete("DELETE FROM doctor_scheduling_info WHERE name = #{name}")
    @Delete("DELETE doctor_scheduling_info FROM doctor_scheduling_info,user_info WHERE doctor_scheduling_info.uid=user_info.uid and user_info.uid=#{id}")
    void deleteDoctorSchedulingById(@Param("id") int id);

    @Update("UPDATE doctor_scheduling_info set shift_id = #{shift_id},scheduling_limit = #{doctorSchedulingInfo.scheduling_limit},"+
            "expiry_date = #{doctorSchedulingInfo.expiry_date}, residue = #{doctorSchedulingInfo.scheduling_limit} WHERE uid = #{doctorSchedulingInfo.id}")
    void updateDoctorScheduling(DoctorSchedulingInfo doctorSchedulingInfo, @Param("shift_id") int shift_id);

    @Select("SELECT real_name as name, department.name as department_name FROM user_info,department WHERE user_info.department_id=department.id and user_info.uid=#{uid}")
    List<DoctorSchedulingInfo> findAddInfo(@Param("uid") int uid);

    @Select("SELECT uid as id, department.name as department_name FROM user_info,department WHERE user_info.department_id=department.id and user_info.real_name=#{name}")
    List<DoctorSchedulingInfo> findAddNameInfo(@Param("name") String name);

    @Select("SELECT id  FROM doctor_scheduling_shift WHERE shift = #{name}")
    int shiftName2ID(@Param("name") String name);

    @Select("SELECT uid as id FROM user_info WHERE uid=#{uid}")
    List<DoctorSchedulingInfo> getId(@Param("uid") int uid);

    @Select("SELECT id FROM registration_level WHERE name = #{name}")
    int levelName2ID(@Param("name") String name);

    @Select("SELECT uid FROM doctor_scheduling_info WHERE uid=#{uid}")
    List<Integer> getAddedId(@Param("uid") int uid);

}
