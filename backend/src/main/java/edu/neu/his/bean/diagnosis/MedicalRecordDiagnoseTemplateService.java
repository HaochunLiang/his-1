package edu.neu.his.bean.diagnosis;

import edu.neu.his.auto.AutoMedicalRecordDiagnoseTemplateItemMapper;
import edu.neu.his.auto.AutoMedicalRecordDiagnoseTemplateMapper;
import edu.neu.his.bean.medicalRecord.MedicalRecordStatus;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicalRecordDiagnoseTemplateService {
    @Autowired
    private AutoMedicalRecordDiagnoseTemplateMapper autoMedicalRecordDiagnoseTemplateMapper;

    @Autowired
    private AutoMedicalRecordDiagnoseTemplateItemMapper autoMedicalRecordDiagnoseTemplateItemMapper;

    @Autowired
    private MedicalRecordDiagnoseTemplateMapper medicalRecordDiagnoseTemplateMapper;

    @Autowired
    private MedicalRecordDiagnoseTemplateItemMapper medicalRecordDiagnoseTemplateItemMapper;

    @Transactional
    public int insertDiagnoseTemplate(MedicalRecordDiagnoseTemplate medicalRecordTemplate){
        autoMedicalRecordDiagnoseTemplateMapper.insert(medicalRecordTemplate);
        return medicalRecordTemplate.getId();
    }

    @Transactional
    public List<MedicalRecordDiagnoseTemplate> selectByUser(int uid){
        List<MedicalRecordDiagnoseTemplate> listByUserId = medicalRecordDiagnoseTemplateMapper.selectByUserId(uid, MedicalRecordStatus.Personal);
        return listByUserId;
    }

    @Transactional
    public List<MedicalRecordDiagnoseTemplate> selectByDepartment(int department_id){
        List<MedicalRecordDiagnoseTemplate> list = medicalRecordDiagnoseTemplateMapper.selectByDepartmentId(department_id,MedicalRecordStatus.Department);
        return list;
    }

    @Transactional
    public List<MedicalRecordDiagnoseTemplate> selectByType(int type){
        return medicalRecordDiagnoseTemplateMapper.selectByType(type);
    }

    @Transactional
    public List<Map> returnMapList(List<MedicalRecordDiagnoseTemplate> list){
        List<Map> resultList = new ArrayList<>();
        list.forEach(medicalRecordDiagnoseTemplate -> {
            Map diagnose = Utils.objectToMap(medicalRecordDiagnoseTemplate);
            List<MedicalRecordDiagnoseTemplateItem> westernItems = medicalRecordDiagnoseTemplateItemMapper.selectByDiagnoseTemplateIdAndType(medicalRecordDiagnoseTemplate.getId(),DiagnoseItemType.Western);
            diagnose.put("western_diagnose", westernItems);
            List<MedicalRecordDiagnoseTemplateItem> chineseItems = medicalRecordDiagnoseTemplateItemMapper.selectByDiagnoseTemplateIdAndType(medicalRecordDiagnoseTemplate.getId(),DiagnoseItemType.Chinese);
            diagnose.put("chinese_diagnose", chineseItems);
            resultList.add(diagnose);
        });
        return resultList;
    }

    @Transactional
    public List<MedicalRecordDiagnoseTemplate> selectByTitle(String title){
        return medicalRecordDiagnoseTemplateMapper.selectByTitle(title);
    }

    @Transactional
    public int insertDiagnoseTemplateItem(MedicalRecordDiagnoseTemplateItem medicalRecordTemplateItem){
        autoMedicalRecordDiagnoseTemplateItemMapper.insert(medicalRecordTemplateItem);
        return medicalRecordTemplateItem.getId();
    }

    @Transactional
    public int updateDiagnoseTemplate(MedicalRecordDiagnoseTemplate medicalRecordDiagnoseTemplate){
        return autoMedicalRecordDiagnoseTemplateMapper.updateByPrimaryKey(medicalRecordDiagnoseTemplate);
    }

    @Transactional
    public void deleteAllItem(int diagnose_template_id){
        List<MedicalRecordDiagnoseTemplateItem> diagnoseTemplateItems = medicalRecordDiagnoseTemplateItemMapper.selectByDiagnoseTemplateId(diagnose_template_id);
        diagnoseTemplateItems.forEach(diagnoseTemplateItem->{
            autoMedicalRecordDiagnoseTemplateItemMapper.deleteByPrimaryKey(diagnoseTemplateItem.getId());
        });
    }

    @Transactional
    public MedicalRecordDiagnoseTemplate selectTemplateById(int id){
        return autoMedicalRecordDiagnoseTemplateMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int deleteTemplateById(int id){
        return autoMedicalRecordDiagnoseTemplateMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public Map returnDiagnoseTemplateMap(int id){
        Map diagnose = new HashMap();
        List<MedicalRecordDiagnoseTemplateItem> westernItems = medicalRecordDiagnoseTemplateItemMapper.selectByDiagnoseTemplateIdAndType(id,DiagnoseItemType.Western);
        diagnose.put("western_diagnose", westernItems);
        List<MedicalRecordDiagnoseTemplateItem> chineseItems = medicalRecordDiagnoseTemplateItemMapper.selectByDiagnoseTemplateIdAndType(id,DiagnoseItemType.Chinese);
        diagnose.put("chinese_diagnose", chineseItems);

        return diagnose;
    }
}
