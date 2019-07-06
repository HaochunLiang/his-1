package edu.neu.his.bean.user;

import edu.neu.his.bean.user.Role;
import edu.neu.his.bean.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "UserMapper")
public interface UserMapper {

    @Select("SELECT id FROM user where username = #{username}")
    int getUidByUserName(@Param("username") String username);

    @Select("SELECT uid, username ,password, real_name, department_id, role_id, participate_in_scheduling, title," +
            "department.name as department_name, role.name as role_name " +
            "FROM user, user_info, department, role " +
            "WHERE uid = #{uid} and user.id = user_info.uid and department.id = user_info.department_id and role.id = user_info.role_id")
    User find(@Param("uid") int uid);

    @Select("SELECT username ,password, real_name, FROM user,user_info WHERE username = #{username} and user.id = user_info.uid")
    List<User> findByName(@Param("username") String username);

    @Select("SELECT uid, username, password, real_name, department_id,department.name as department_name,role_id, role.name as role_name, title, participate_in_scheduling " +
            "FROM user,user_info,role,department " +
            "WHERE user.id = user_info.uid and department.id = user_info.department_id and user_info.role_id=role.id")
    List<User> findAll();

    @Insert("INSERT INTO user (username, password) VALUES(#{username}, #{password})")
    void addUser(User user);

    @Insert("INSERT INTO user_info (uid, real_name, department_id, role_id, participate_in_scheduling, title) " +
            "values (#{uid}, #{real_name}, #{department_id}, #{role_id}, #{participate_in_scheduling}, #{title})")
    void addUserInfo(User user);

    @Update("UPDATE user SET username = #{username}, password = #{password} WHERE id = #{uid}")
    void updateUser(User user);

    @Update("UPDATE user_info SET real_name = #{real_name}, department_id = #{department_id}, " +
            "participate_in_scheduling = #{participate_in_scheduling}, role_id = #{role_id}, title = #{title} " +
            "where uid = #{uid}")
    void updateUserInfo(User user);

    @Delete("DELETE from user where id = #{id}")
    void deleteUser(@Param("id") int uid);

    @Delete("DELETE from user_info where uid = #{uid}")
    void deleteUserInfo(@Param("uid") int uid);

    @Select("select * from userinfo where title=${title} and department_id=${departmentId}")
    List<User> selectDoctorList(int departmentId, String title);
    
    @Select("SELECT * FROM role")
    List<Role> allRoles();

    @Select("SELECT count(*) FROM user WHERE id = #{id}")
    int checkIdExists(@Param("id") int id);

    @Select("SELECT count(*) FROM user WHERE username = #{username}")
    int checkNameExists(@Param("username") String username);
}
