package edu.neu.his.bean.registration;

import edu.neu.his.bean.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "OutpatientRegistrationMapper")
public interface OutpatientRegistrationMapper {
    @Select("SELECT DISTINCT user_info.uid, username, password, real_name, department_id,department.name as department_name,role_id, role.name as role_name, title, participate_in_scheduling " +
            "FROM user,user_info,role,department, doctor_scheduling " +
            "WHERE user.id = user_info.uid and department.id = user_info.department_id and user_info.role_id=role.id " +
            "and user_info.department_id = #{department_id}")
    // and doctor_scheduling.registration_level_id = #{registration_level_id} and doctor_scheduling.uid = user.id"
    List<User> findByDepartmentAndTitle(@Param("department_id") int department_id, @Param("registration_level_id") int registration_level_id, @Param("curr_date") String curr_date);

    @Insert("INSERT INTO registration (address,age,birthday,consultation_date,medical_category,patient_name," +
            "outpatient_doctor_id,registration_department_id,settlement_category_id,registration_source,gender," +
            "medical_insurance_diagnosis,id_number,medical_certificate_number, status, cost, registration_category)" +
            " VALUES(#{address},#{age}, #{birthday}, #{consultation_date}, #{medical_category}, " +
            "#{patient_name}, #{outpatient_doctor_id}, #{registration_department_id}, #{settlement_category_id}, " +
            "#{registration_source}, #{gender}, #{medical_insurance_diagnosis}, #{id_number}, " +
            "#{medical_certificate_number}, #{status}, #{cost}, #{registration_category})")
    @Options(useGeneratedKeys = true, keyProperty = "medical_record_id")
    void insert(Registration registration);

    @Select("SELECT * FROM registration WHERE registration.medical_record_id = #{medical_record_id}")
    Registration findRegistrationById(@Param("medical_record_id") int medical_record_id);

    @Update("UPDATE registration SET status = #{status} WHERE medical_record_id = #{medical_record_id}")
    void update(Registration registration);

    @Select("SELECT * FROM registration WHERE medical_certificate_number = #{medical_certificate_number}")
    List<Registration> findRegistrationByMedicalCertificateNumber(@Param("medical_certificate_number") String medical_certificate_number);

    @Select("SELECT * FROM registration WHERE outpatient_doctor_id = #{outpatient_doctor_id}")
    List<Registration> findRegistrationByDoctor(@Param("outpatient_doctor_id") int outpatient_doctor_id);

    @Select("SELECT * FROM registration WHERE id_number = #{id_number}")
    List<Registration> findRegistrationByIdNumber(@Param("id_number") String id_number);

    @Select("SELECT * FROM registration WHERE medical_certificate_number = #{medical_certificate_number} and status = #{status}")
    List<Registration> findRegistrationByMedicalCertificateNumberAndStatus(@Param("medical_certificate_number") String medical_certificate_number,
                                                                           @Param("status") String status);

    @Select("SELECT * FROM registration WHERE id_number = #{id_number} and status = #{status}")
    List<Registration> findRegistrationByIdNumberAndStatus(@Param("id_number") String id_number, @Param("status") String status);

    @Select("SELECT medical_record_id FROM registration WHERE patient_name = #{patient_name}")
    List<Integer> findMedicalRecordIdByName(@Param("patient_name") String patient_name);

    @Select("SELECT * FROM registration WHERE patient_name LIKE #{patient_name}")
    List<Registration> findMedicalRecordLikeName(@Param("patient_name") String patient_name);

    @Select("SELECT * FROM registration WHERE outpatient_doctor_id = #{outpatient_doctor_id} and status = #{status} and " +
            "consultation_date < #{end_time} and consultation_date > #{start_time}")
    List<Registration> findByDoctor(@Param("outpatient_doctor_id") int outpatient_doctor_id,
                     @Param("status") String status,
                     @Param("start_time") String start_time,
                     @Param("end_time") String end_time);


}
