package edu.neu.his.bean.schedule;

import edu.neu.his.bean.schedule.AllSchedule;
import edu.neu.his.bean.schedule.DoctorSchedulingInfo;
import edu.neu.his.bean.schedule.DoctorWorkforce1;
import edu.neu.his.bean.schedule.Schedule;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "DoctorWorkforceMapper")
public interface DoctorWorkforceMapper {

    @Select("SELECT user_info.real_name as name,doctor_scheduling_shift.shift as shift,expiry_date,scheduling_limit "+
            "FROM doctor_scheduling_info,user_info,department,registration_level,doctor_scheduling_shift "+
            "WHERE doctor_scheduling_info.uid=user_info.uid and doctor_scheduling_info.shift_id=doctor_scheduling_shift.id")
    List<DoctorWorkforce1> findAll();

    @Insert("INSERT INTO doctor_scheduling (uid,schedule_date,week,valid,reg_limit,registration_level_id,residue,shift) "+
            "values (#{uid},#{schedule.scheduleDate},#{schedule.week},#{schedule.valid},#{schedule.reg_limit},#{registration_level_id},#{schedule.residue},#{schedule.shift})")
    void addDoctorWorkforce(Schedule schedule, @Param("uid") int uid, @Param("registration_level_id") int registration_level_id);

    @Delete("DELETE FROM doctor_scheduling WHERE id = #{id}")
    void deleteDoctorWorkforceById(@Param("id") int id);

    @Delete("DELETE FROM doctor_scheduling_info WHERE name = #{name}")
    void chooseDoctorWorkforceByName(@Param("name") String name);

    @Update("UPDATE doctor_scheduling_info set shift = #{shift} WHERE id = #{id}")
    void updateDoctorWorkforce(DoctorWorkforce1 doctorWorkforce1);

    //@Select("SELECT doctor_scheduling.id,schedule_date,week,doctor_scheduling_info.name,department_name,doctor_scheduling.shift,doctor_scheduling.reg_limit,residue,valid,doctor_scheduling_info.registration_Level  FROM doctor_scheduling_info,doctor_scheduling WHERE doctor_scheduling_info.name = doctor_scheduling.name")
    @Select("SELECT doctor_scheduling.id as id,user_info.real_name as name,schedule_date,week,doctor_scheduling.shift as shift,"+
            "department.name as department_name,registration_level.name as registration_Level,reg_limit,doctor_scheduling.residue,valid "+
            "FROM doctor_scheduling,doctor_scheduling_info,user_info,department,registration_level "+
            "WHERE doctor_scheduling_info.uid=user_info.uid and user_info.department_id=department.id and doctor_scheduling.uid=doctor_scheduling_info.uid"+
            " and registration_level.id=user_info.registration_level_id ")
    List<AllSchedule> findAllSchedule();

    @Select("SELECT id,schedule_date FROM doctor_scheduling WHERE schedule_date = #{schedule_date}")
    List<AllSchedule> findTimeConflict(@Param("schedule_date") String schedule_date);

    //@Insert("INSERT INTO doctor_scheduling (name,schedule_date,shift,week,residue,valid,reg_limit,registration_Level) values (#{name},#{scheduleDate},#{shift},#{week},#{residue},#{valid},#{reg_limit},#{registration_Level})")
    @Insert("INSERT INTO doctor_scheduling (uid,schedule_date,week,valid,reg_limit,registration_level_id,residue,shift) values "+
            "(#{uid},#{schedule.scheduleDate},#{schedule.week},#{schedule.valid},#{schedule.reg_limit},#{registration_level_id},#{schedule.residue},#{schedule.shift})")
    void addOneDoctorWorkforce(Schedule schedule, @Param("registration_level_id") int registration_level_id, @Param("uid") int uid);

    //@Select("SELECT doctor_scheduling.id,schedule_date,week,doctor_scheduling_info.name,department_name,doctor_scheduling.shift,scheduling_limit,residue,valid,doctor_scheduling_info.registration_Level  FROM doctor_scheduling_info,doctor_scheduling WHERE doctor_scheduling_info.name = doctor_scheduling.name and doctor_scheduling_info.name = #{name}")
    /*@Select("SELECT user_info.uid as id, schedule_date, week, user_info.real_name as name, department.name as department_name, "+
            "doctor_scheduling.shift as shift, scheduling_limit as reg_limit, doctor_scheduling.residue, valid, registration_level.name as registration_Level "+
            "FROM doctor_scheduling_info, user_info, department, registration_level, doctor_scheduling "+
            "WHERE doctor_scheduling_info.uid=user_info.uid and user_info.department_id=department.id and "+
            "doctor_scheduling.uid=doctor_scheduling_info.uid "+
            " and registration_level.id=user_info.registration_level_id and user_info.real_name=#{name}")*/
    @Select("SELECT user_info.uid as id, user_info.real_name as name, department.name as department_name, " +
            "doctor_scheduling_shift.shift as shift, scheduling_limit as reg_limit, doctor_scheduling_info.residue, registration_level.name as registration_Level " +
            "FROM doctor_scheduling_info, user_info, department, registration_level, doctor_scheduling_shift " +
            "WHERE doctor_scheduling_info.uid=user_info.uid and user_info.department_id=department.id and doctor_scheduling_shift.id=doctor_scheduling_info.shift_id " +
            "and registration_level.id=user_info.registration_level_id and user_info.real_name=#{name}")
    List<AllSchedule> findAddInfo(@Param("name") String name);

    @Select("SELECT user_info.uid as id, user_info.real_name as name, department.name as department_name, " +
            "doctor_scheduling_shift.shift as shift, scheduling_limit as reg_limit, doctor_scheduling_info.residue, registration_level.name as registration_Level " +
            "FROM doctor_scheduling_info, user_info, department, registration_level, doctor_scheduling_shift " +
            "WHERE doctor_scheduling_info.uid=user_info.uid and user_info.department_id=department.id and doctor_scheduling_shift.id=doctor_scheduling_info.shift_id " +
            "and registration_level.id=user_info.registration_level_id and user_info.uid=#{id}")
    List<AllSchedule> findAddInfoByID(@Param("id") int id);

    @Select("SELECT id,schedule_date FROM doctor_scheduling WHERE schedule_date = #{schedule_date} and shift = #{shift} and uid = #{uid}")
    List<AllSchedule> findAddRowConflict(@Param("uid") int uid, @Param("schedule_date") String schedule_date, @Param("shift") String shift);

    @Select("SELECT id FROM registration_level WHERE name = #{name}")
    int registrationLevel2ID(@Param("name") String name);

    @Select("SELECT uid FROM user_info WHERE real_name = #{name}")
    int name2ID(@Param("name") String name);

    @Select("SELECT real_name as name FROM user_info WHERE real_name=#{name}")
    List<DoctorSchedulingInfo> getName(@Param("name") String name);

    @Select("SELECT name FROM registration_level")
    List<String> getRegistrationLevels();

    @Select("SELECT user_info.real_name as name, scheduling_limit as reg_limit, residue as residue "+
            "FROM user_info, doctor_scheduling_info "+
            "WHERE user_info.uid=doctor_scheduling_info.uid and user_info.uid=#{id}")
    List<Schedule> injectDoctorWorkforce(Schedule schedule);
}
