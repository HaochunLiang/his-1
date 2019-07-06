package edu.neu.his.bean.medicalRecord;

import edu.neu.his.bean.registration.Registration;
import edu.neu.his.bean.registration.RegistrationConfig;
import edu.neu.his.bean.registration.OutpatientRegistrationMapper;
import edu.neu.his.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    private OutpatientRegistrationMapper outpatientRegistrationMapper;

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Transactional
    public List<Registration> findHistory(String type, String number){
        if(type.equals("id"))
            return outpatientRegistrationMapper.findRegistrationByIdNumber(number);
        else
            return outpatientRegistrationMapper.findRegistrationByMedicalCertificateNumber(number);
    }

    @Transactional
    public boolean medicalRecordHasSubmit(int id){
        String medicalRecordStatus = this.getStatusById(id);
        if(Common.YITIJIAO.equals(medicalRecordStatus)){
            return true;
        }
        return false;
    }

    @Transactional
    public List<Registration> find(String type, String number, String status){
        if(type.equals("id"))
            return outpatientRegistrationMapper.findRegistrationByIdNumberAndStatus(number, status);
        else
            return outpatientRegistrationMapper.findRegistrationByMedicalCertificateNumberAndStatus(number, status);
    }

    @Transactional
    public MedicalRecord findMedicalRecordById(int medical_record_id){
        return medicalRecordMapper.selectByPrimaryKey(medical_record_id);
    }

    @Transactional
    public int insertMedicalRecord(MedicalRecord medicalRecord){
        medicalRecordMapper.insert(medicalRecord);
        return medicalRecord.getId();
    }

    @Transactional
    public int updateMedicalRecord(MedicalRecord medicalRecord){
        return medicalRecordMapper.updateByPrimaryKey(medicalRecord);
    }

    @Transactional
    public String getStatusById(Integer id){
        MedicalRecord medicalRecord = medicalRecordMapper.selectByPrimaryKey(id);
        if(medicalRecord == null){
            return null;
        }
        return medicalRecord.getStatus();
    }


    @Transactional
    public boolean canOperateMedicalRecord(int medical_record_id){
        Registration registration = outpatientRegistrationMapper.findRegistrationById(medical_record_id);
        if(registration==null)
            return false;

        if(!registration.getStatus().equals(RegistrationConfig.registrationCanceled))
            return true;
        else return false;
    }

    @Transactional
    public boolean hasSubmit(Integer id){
        MedicalRecord medicalRecord = medicalRecordMapper.selectByPrimaryKey(id);
        if(medicalRecord==null){
            return false;
        }
        if(!Common.YITIJIAO.equals(medicalRecord.getStatus())){
            return false;
        }
        return true;
    }
}
