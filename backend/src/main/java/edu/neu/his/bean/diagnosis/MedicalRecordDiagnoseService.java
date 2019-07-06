package edu.neu.his.bean.diagnosis;

import edu.neu.his.auto.AutoMedicalRecordDiagnoseItemMapper;
import edu.neu.his.auto.AutoMedicalRecordDiagnoseMapper;
import edu.neu.his.bean.disease.Disease;
import edu.neu.his.bean.disease.DiseaseMapper;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MedicalRecordDiagnoseService {
    @Autowired
    private MedicalRecordDiagnoseMapper medicalRecordDiagnoseMapper;

    @Autowired
    private MedicalRecordDiagnoseItemMapper medicalRecordDiagnoseItemMapper;

    @Autowired
    private AutoMedicalRecordDiagnoseMapper autoMedicalRecordDiagnoseMapper;

    @Autowired
    private AutoMedicalRecordDiagnoseItemMapper autoMedicalRecordDiagnoseItemMapper;

    @Autowired
    DiseaseMapper diseaseMapper;

    @Transactional
    public MedicalRecordDiagnose findDiagnoseByMedicalRecordId(int medical_record_id){
        return medicalRecordDiagnoseMapper.selectByMedicalRecordId(medical_record_id);
    }

    @Transactional
    public List<MedicalRecordDiagnoseItem> findDiagnoseItemByDiagnoseId(int medical_record_diagnose_id){
        return medicalRecordDiagnoseItemMapper.selectByDiagnoseId(medical_record_diagnose_id);
    }

    @Transactional
    public List<MedicalRecordDiagnoseItem> findDiagnoseItemByDiagnoseIdAndType(int medical_record_diagnose_id,String diagnose_type){
        return medicalRecordDiagnoseItemMapper.selectByDiagnoseIdAndType(medical_record_diagnose_id,diagnose_type);
    }

    @Transactional
    public int insertDiagnose(MedicalRecordDiagnose medicalRecordDiagnose){
        autoMedicalRecordDiagnoseMapper.insert(medicalRecordDiagnose);
        return medicalRecordDiagnose.getId();
    }

    @Transactional
    public Map getExistDiagnose(MedicalRecordDiagnose medicalRecordDiagnose){
        Map diagnose = Utils.objectToMap(medicalRecordDiagnose);
        List<MedicalRecordDiagnoseItem> westernList = findDiagnoseItemByDiagnoseIdAndType(medicalRecordDiagnose.getId(), DiagnoseItemType.Western);
        List<MedicalRecordDiagnoseItem> chineseList = findDiagnoseItemByDiagnoseIdAndType(medicalRecordDiagnose.getId(), DiagnoseItemType.Chinese);
        diagnose.put("western_diagnose", westernList);
        diagnose.put("chinese_diagnose", chineseList);

        return diagnose;
    }

    @Transactional
    public Map getEmptyDiagnose(MedicalRecordDiagnose medicalRecordDiagnose){
        Map diagnose = Utils.objectToMap(medicalRecordDiagnose);
        List<MedicalRecordDiagnoseItem> westernList = new ArrayList<>();
        List<MedicalRecordDiagnoseItem> chineseList = new ArrayList<>();
        diagnose.put("western_diagnose", westernList);
        diagnose.put("chinese_diagnose", chineseList);

        return diagnose;
    }

    @Transactional
    public void deleteAllByDiagnoseId(int medical_record_diagnose_id){
        List<MedicalRecordDiagnoseItem> medicalRecordDiagnoseItems = medicalRecordDiagnoseItemMapper.selectByDiagnoseId(medical_record_diagnose_id);
        medicalRecordDiagnoseItems.forEach(medicalRecordDiagnoseItem -> {
            int item_id = medicalRecordDiagnoseItem.getId();
            autoMedicalRecordDiagnoseItemMapper.deleteByPrimaryKey(item_id);
        });
    }

    @Transactional
    public int insertDiagnoseItem(MedicalRecordDiagnoseItem medicalRecordDiagnoseItem){
        autoMedicalRecordDiagnoseItemMapper.insert(medicalRecordDiagnoseItem);
        return medicalRecordDiagnoseItem.getId();
    }

    @Transactional
    public List<Disease> selectAllDisease() {
        return diseaseMapper.findall();
    }
}
