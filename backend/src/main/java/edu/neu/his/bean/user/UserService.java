package edu.neu.his.bean.user;

import edu.neu.his.bean.registration.RegistrationLevel;
import edu.neu.his.bean.registration.RegistrationLevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//redis缓存
//@CacheConfig(cacheNames="userCache")
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RegistrationLevelMapper registrationLevelMapper;

    @Transactional
    public void addUser(User user) {
        userMapper.addUser(user);
        int new_id = userMapper.getUidByUserName(user.getUsername());
        user.setUid(new_id);
        userMapper.addUserInfo(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userMapper.deleteUserInfo(id);
        userMapper.deleteUser(id);
    }

    @Transactional
    public void updateUser(User user) {
        userMapper.updateUserInfo(user);
        userMapper.updateUser(user);
    }

    @Transactional
    public List<User> allUsers() {
        return userMapper.findAll();
    }

    public List selectDoctorList(int departmentId, int registrationLevelId) {
        RegistrationLevel registration_level = registrationLevelMapper.find(departmentId);
        if(registration_level.getName().equals("专家号")){
           return userMapper.selectDoctorList(departmentId, "主治医生");
        }
        return userMapper.selectDoctorList(departmentId, "");
    }

    @Transactional
    public List<Role> allRoles() {return userMapper.allRoles();}

    @Transactional
    public User findByUid(int uid) {
        return userMapper.find(uid);
    }

    @Transactional
    public boolean canInsert(User user) {
        int id_num = userMapper.checkIdExists(user.getUid());
        int name_num = userMapper.checkNameExists(user.getUsername());
        if(id_num>=1|| name_num>=1)
            return false;
        else
            return true;
    }

    @Transactional
    public boolean canUpdate(User user) {
        int id_num = userMapper.checkIdExists(user.getUid());
        int name_num = userMapper.checkNameExists(user.getUsername());
        if(id_num==0 || name_num>1 || id_num>1)
            return false;
        else
            return true;
    }

    @Transactional
    public int canDelete(int id) {
        return userMapper.checkIdExists(id);//0,不能删
    }
}
