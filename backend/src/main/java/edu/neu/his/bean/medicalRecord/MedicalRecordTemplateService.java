package edu.neu.his.bean.medicalRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalRecordTemplateService {
    @Autowired
    private MedicalRecordTemplateMapper medicalRecordTemplateMapper;

    @Transactional
    public List<MedicalRecordTemplate> selectAll(){
        return medicalRecordTemplateMapper.selectAll();
    }

    @Transactional
    public MedicalRecordTemplate selectById(int id){
        return medicalRecordTemplateMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public List<MedicalRecordTemplate> selectByTitle(String title){
        return medicalRecordTemplateMapper.selectByTitle(title);
    }

    @Transactional
    public int insert(MedicalRecordTemplate medicalRecordTemplate){
        medicalRecordTemplateMapper.insert(medicalRecordTemplate);
        return medicalRecordTemplate.getId();
    }

    @Transactional
    public int update(MedicalRecordTemplate medicalRecordTemplate){
        return medicalRecordTemplateMapper.updateByPrimaryKey(medicalRecordTemplate);
    }

    @Transactional
    public int delete(int id){
        return medicalRecordTemplateMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public List<MedicalRecordTemplate> selectByUser(int uid){
        List<MedicalRecordTemplate> listByUserId = medicalRecordTemplateMapper.selectByUserId(uid,MedicalRecordStatus.Personal);
        return listByUserId;
    }

    @Transactional
    public List<MedicalRecordTemplate> selectByDepartment(int department_id){
        List<MedicalRecordTemplate> list = medicalRecordTemplateMapper.selectByDepartmentId(department_id,MedicalRecordStatus.Department);
        return list;
    }

    @Transactional
    public List<MedicalRecordTemplate> selectByType(int type){
        return medicalRecordTemplateMapper.selectByType(type);
    }
}
