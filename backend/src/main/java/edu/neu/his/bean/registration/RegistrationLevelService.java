package edu.neu.his.bean.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RegistrationLevelService {
    @Autowired
    private RegistrationLevelMapper registrationLevelMapper;

    @Transactional
    public void updateDepartment(RegistrationLevel registration_level) {
        registrationLevelMapper.update(registration_level);
    }

    @Transactional
    public RegistrationLevel findByName(String name) {
        return registrationLevelMapper.findByName(name);
    }

    @Transactional
    public void insertRegistration_level(RegistrationLevel registration_level) {
        registrationLevelMapper.insert(registration_level);
    }

    @Transactional
    public List<RegistrationLevel> findAll() {
        List<RegistrationLevel> levels = registrationLevelMapper.findAll();
        Collections.sort(levels, (o1, o2) -> {
            if(o1.getSeq_num()>o2.getSeq_num()){
                return 1;
            }else if(o1.getSeq_num()<o2.getSeq_num()){
                return -1;
            }else{
                return 0;
            }
        });
        return levels;
    }

    @Transactional
    public void deleteRegistration_level(int id) {
        registrationLevelMapper.delete(id);
    }

    @Transactional
    public RegistrationLevel findById(int id) {
        return  registrationLevelMapper.findById(id);
    }

    @Transactional
    public boolean canUpdate(RegistrationLevel registrationLevel) {
        int id_num = registrationLevelMapper.checkIdExists(registrationLevel.getId());
        int name_num = registrationLevelMapper.checkNameExists(registrationLevel.getName());
        if(id_num==0 || name_num>1 || id_num>1)
            return false;
        else if(name_num==1){
            RegistrationLevel d = registrationLevelMapper.findByName(registrationLevel.getName());
            if(d.getId() != registrationLevel.getId())
                return false;
            else
                return true;
        }else
            return true;
    }

    @Transactional
    public boolean canInsert(RegistrationLevel registrationLevel) {
        int id_num = registrationLevelMapper.checkIdExists(registrationLevel.getId());
        int name_num = registrationLevelMapper.checkNameExists(registrationLevel.getName());
        if(id_num>=1 || name_num>=1)
            return false;
        else
            return true;
    }

    @Transactional
    public int canDelete(int id) {
        return registrationLevelMapper.checkIdExists(id);//0,不能删
    }

    @Transactional
    public RegistrationLevel findDefault() {
        List<RegistrationLevel> list = registrationLevelMapper.findDefault();
        if(list.size()!=0)
            return  registrationLevelMapper.findDefault().get(0);
        else
            return registrationLevelMapper.findAll().get(0);
    }
}
