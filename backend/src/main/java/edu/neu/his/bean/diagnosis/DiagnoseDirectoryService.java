package edu.neu.his.bean.diagnosis;

import edu.neu.his.bean.disease.Disease;
import edu.neu.his.bean.disease.DiseaseClassification;
import edu.neu.his.bean.disease.DiseaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiagnoseDirectoryService {
    @Autowired DiseaseMapper diseaseMapper;

    @Transactional
    public void updateDisease(int rawId, Disease disease) {
        diseaseMapper.update(rawId, disease.getId(), disease.getCode(),disease.getName(),disease.getClassification_id(),disease.getPinyin(),disease.getCustom_name(),disease.getCustom_pinyin());
    }

    @Transactional
    public Disease findDiseaseByName(String name) {
        return diseaseMapper.findByName(name);
    }

    @Transactional
    public void insertDisease(Disease disease) {
        diseaseMapper.insertDisease(disease);
    }

    @Transactional
    public List<Disease> findAll(int classification_id) {
        return diseaseMapper.findAll(classification_id);
    }

    @Transactional
    public void deleteDisease(int id) {
        diseaseMapper.deleteDisease(id);
    }

    @Transactional
    public void insertDiseaseClassification(DiseaseClassification diseaseClassification) {
        diseaseMapper.insertDiseaseClassification(diseaseClassification);
    }

    @Transactional
    public boolean checkCodeExist(String code) {
        return diseaseMapper.checkCodeExist(code)==1;
    }

    @Transactional
    public boolean checkIdExist(int id) {
        return diseaseMapper.checkIdExist(id)==1;
    }

    @Transactional
    public boolean checkClassificationExist(int classification_id) {
        return diseaseMapper.checkClassificationExist(classification_id)==1;
    }

    @Transactional
    public List<DiseaseClassification> findAllDiseaseClassification() {
        return diseaseMapper.findAllDiseaseClassification();
    }
}
