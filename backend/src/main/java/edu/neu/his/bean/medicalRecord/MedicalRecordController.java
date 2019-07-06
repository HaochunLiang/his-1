package edu.neu.his.bean.medicalRecord;

import edu.neu.his.bean.diagnosis.DiagnoseItemType;
import edu.neu.his.bean.diagnosis.MedicalRecordDiagnose;
import edu.neu.his.bean.diagnosis.MedicalRecordDiagnoseItem;
import edu.neu.his.bean.diagnosis.MedicalRecordDiagnoseService;
import edu.neu.his.bean.disease.Disease;
import edu.neu.his.bean.disease.DiseaseMapper;
import edu.neu.his.bean.registration.Registration;
import edu.neu.his.config.Auth;
import edu.neu.his.bean.registration.RegistrationConfig;
import edu.neu.his.config.Response;
import edu.neu.his.bean.registration.OutpatientRegistrationService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private OutpatientRegistrationService outpatientRegistrationService;

    @Autowired
    private MedicalRecordDiagnoseService medicalRecordDiagnoseService;

    @Autowired
    DiseaseMapper diseaseMapper;

    @RequestMapping("/allDiagnoseDiseases")
    public Map allDiagnoseDiseases(@RequestBody Map req){
        Map data = new HashMap();
        int uid = Auth.uid(req);
        List<Disease> chineseDiagnoseDiseases = diseaseMapper.findAll(472);
        List<Disease> westernDiagnoseDiseases = medicalRecordDiagnoseService.selectAllDisease().stream().filter(i->i.getClassification_id() != 472).collect(Collectors.toList());
        data.put("chineseDiagnoseDiseases", chineseDiagnoseDiseases);
        data.put("westernDiagnoseDiseases", westernDiagnoseDiseases);
        return Response.ok(data);
    }

    @PostMapping("/getPatientList")
    @ResponseBody
    public Map getPatientList(@RequestBody Map req){
        Map data = new HashMap();
        int uid = Auth.uid(req);
        List<Registration> list = outpatientRegistrationService.findByDoctor(uid);
        List<Registration> waitList = new ArrayList<>();
        List<Registration> pendList = new ArrayList<>();
        list.forEach(registration -> {
            int medical_record_id = registration.getMedical_record_id();
            MedicalRecord medicalRecord = medicalRecordService.findMedicalRecordById(medical_record_id);
            if(medicalRecord==null) {
                if(registration.getStatus().equals(RegistrationConfig.registrationAvailable))
                    waitList.add(registration);
            }
            else if(medicalRecord.getStatus().equals(MedicalRecordStatus.TemporaryStorage) || medicalRecord.getStatus().equals(MedicalRecordStatus.Committed))
                pendList.add(registration);
        });

        data.put("waiting",waitList);
        data.put("pending",pendList);
        return Response.ok(data);
    }

    @PostMapping("/registrationInfo")
    @ResponseBody
    public Map registrationInfo(@RequestBody Map req){
        String type = (String)req.get("type");
        String medical_certificate_number = (String)req.get("medical_certificate_number");
        return Response.ok(medicalRecordService.find(type,medical_certificate_number, RegistrationConfig.registrationAvailable));
    }

    @PostMapping("/allHistoryMedicalRecord")
    @ResponseBody
    public Map recordHistory(@RequestBody Map req){
        List<Map> data = new ArrayList<>();
        String type = (String)req.get("type");
        String medical_certificate_number = (String)req.get("medical_certificate_number");

        //获得挂号信息
        List<Registration> registrationList = medicalRecordService.findHistory(type,medical_certificate_number);
        registrationList.forEach(registration -> {
            int medical_record_id = registration.getMedical_record_id();
            //获得病历
            MedicalRecord medicalRecord = medicalRecordService.findMedicalRecordById(medical_record_id);
            if(medicalRecord!=null && medicalRecord.getStatus().equals(MedicalRecordStatus.Finished)) {
                Map record = Utils.objectToMap(medicalRecord);
                //获得诊断
                Map diagnose = new HashMap();
                MedicalRecordDiagnose medicalRecordDiagnose = medicalRecordDiagnoseService.findDiagnoseByMedicalRecordId(medical_record_id);
                if(medicalRecordDiagnose!=null)
                    diagnose = medicalRecordDiagnoseService.getExistDiagnose(medicalRecordDiagnose);

                //向病历中加入诊断
                record.put("diagnose", diagnose);
                //向历史病历列表中加入该病历
                data.add(record);
            }
        });

        return Response.ok(data);
    }

    @PostMapping("/getMedicalRecord")
    @ResponseBody
    public Map createMedicalRecord(@RequestBody Map req){
        int medical_record_id = (int)req.get("medical_record_id");
        if(!medicalRecordService.canOperateMedicalRecord(medical_record_id))
            return Response.error("错误，挂号不存在或该挂号已取消");

        MedicalRecord medicalRecord = medicalRecordService.findMedicalRecordById(medical_record_id);
        Map data;
        Map diagnose;

        if(medicalRecord==null) {
            medicalRecord = new MedicalRecord();
            medicalRecord.setId(medical_record_id);
            medicalRecord.setStatus(MedicalRecordStatus.TemporaryStorage);
            medicalRecord = init(medicalRecord);
            medicalRecordService.insertMedicalRecord(medicalRecord);
            medicalRecord.setId(medical_record_id);
            Registration registration = outpatientRegistrationService.findRegistrationById(medical_record_id);
            registration.setStatus(RegistrationConfig.registrationFinished);
            outpatientRegistrationService.updateStatus(registration);
        }

        data = Utils.objectToMap(medicalRecord);
        MedicalRecordDiagnose medicalRecordDiagnose = medicalRecordDiagnoseService.findDiagnoseByMedicalRecordId(medical_record_id);

        if(medicalRecordDiagnose!=null) {
            diagnose = medicalRecordDiagnoseService.getExistDiagnose(medicalRecordDiagnose);
        }else {
            medicalRecordDiagnose = new MedicalRecordDiagnose();
            medicalRecordDiagnose.setMedical_record_id(medical_record_id);
            medicalRecordDiagnoseService.insertDiagnose(medicalRecordDiagnose);
            diagnose = medicalRecordDiagnoseService.getEmptyDiagnose(medicalRecordDiagnose);
        }

        data.put("diagnose", diagnose);
        return Response.ok(data);
    }

    @PostMapping("/saveMedicalRecord")
    @ResponseBody
    public Map saveMedicalRecord(@RequestBody Map req){
        int medical_record_id = (int)req.get("id");
        MedicalRecord medicalRecord = medicalRecordService.findMedicalRecordById(medical_record_id);
        if(medicalRecord==null)
            return Response.error("错误，该病历不存在");
        else if(medicalRecord.getStatus().equals(MedicalRecordStatus.Finished))
            return Response.error("错误，该病历已诊毕");

        //更新病历
        MedicalRecord newMedicalRecord = Utils.fromMap(req,MedicalRecord.class);
        newMedicalRecord.setStatus(MedicalRecordStatus.Committed);
        newMedicalRecord.setCreate_time(medicalRecord.getCreate_time());
        medicalRecordService.updateMedicalRecord(newMedicalRecord);

        //更新诊断
        updateDiagnose(req,medical_record_id);

        return Response.ok();
    }

    @PostMapping("/updateMedicalRecord")
    @ResponseBody
    public Map updateMedicalRecord(@RequestBody Map req){
        int medical_record_id = (int)req.get("id");
        MedicalRecord medicalRecord = medicalRecordService.findMedicalRecordById(medical_record_id);
        if(medicalRecord==null)
            return Response.error("错误，该病历不存在");
        else if(!medicalRecord.getStatus().equals(MedicalRecordStatus.TemporaryStorage))
            return Response.error("错误，该病历已提交或已诊毕");

        //更新病历
        MedicalRecord newMedicalRecord = Utils.fromMap(req,MedicalRecord.class);
        newMedicalRecord.setStatus(MedicalRecordStatus.TemporaryStorage);
        newMedicalRecord.setCreate_time(medicalRecord.getCreate_time());
        medicalRecordService.updateMedicalRecord(newMedicalRecord);

        //更新诊断
        updateDiagnose(req,medical_record_id);

        return Response.ok();
    }

    @PostMapping("/confirmMedicalRecord")
    @ResponseBody
    public Map confirmMedicalRecord(@RequestBody Map req){
        int medical_record_id = (int)req.get("id");
        MedicalRecord medicalRecord = medicalRecordService.findMedicalRecordById(medical_record_id);
        if(medicalRecord==null)
            return Response.error("错误，该病历不存在");
        else if(medicalRecord.getStatus().equals(MedicalRecordStatus.Finished))
            return Response.error("错误，该病历已诊毕");

        //更新病历
        medicalRecord = Utils.fromMap(req,MedicalRecord.class);
        medicalRecord.setStatus(MedicalRecordStatus.Finished);
        medicalRecordService.updateMedicalRecord(medicalRecord);

        //更新诊断
        updateDiagnose(req,medical_record_id);

        return Response.ok();
    }

    private MedicalRecord init(MedicalRecord medicalRecord){
        medicalRecord.setAllergy_history("");
        medicalRecord.setChief_complaint("");
        medicalRecord.setCurrent_medical_history("");
        medicalRecord.setCurrent_treatment_situation("");
        medicalRecord.setPast_history("");
        medicalRecord.setPhysical_examination("");
        medicalRecord.setCreate_time(Utils.getSystemTime());
        return medicalRecord;
    }

    private void updateDiagnose(Map req, int medical_record_id){
        //获得诊断ID
        int diagnose_id = medicalRecordDiagnoseService.findDiagnoseByMedicalRecordId(medical_record_id).getId();

        //删除现有诊断子目
        medicalRecordDiagnoseService.deleteAllByDiagnoseId(diagnose_id);

        Map diagnose = (Map)req.get("diagnose");
        List westernList = (List)diagnose.get("western_diagnose");
        List chineseList = (List)diagnose.get("chinese_diagnose");

        //更新诊断子目
        westernList.forEach(object->{
            Map itemMap = (Map)object;
            Utils.initMap(itemMap);
            MedicalRecordDiagnoseItem medicalRecordDiagnoseItem = Utils.fromMap(itemMap,MedicalRecordDiagnoseItem.class);
            medicalRecordDiagnoseItem.setMedical_record_diagnose_id(diagnose_id);
            medicalRecordDiagnoseItem.setDiagnose_type(DiagnoseItemType.Western);
            medicalRecordDiagnoseService.insertDiagnoseItem(medicalRecordDiagnoseItem);
        });
        chineseList.forEach(object->{
            Map itemMap = (Map)object;
            Utils.initMap(itemMap);
            MedicalRecordDiagnoseItem medicalRecordDiagnoseItem = Utils.fromMap(itemMap,MedicalRecordDiagnoseItem.class);
            medicalRecordDiagnoseItem.setMedical_record_diagnose_id(diagnose_id);
            medicalRecordDiagnoseItem.setDiagnose_type(DiagnoseItemType.Chinese);
            medicalRecordDiagnoseService.insertDiagnoseItem(medicalRecordDiagnoseItem);
        });
    }
}
