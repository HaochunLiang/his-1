package edu.neu.his.bean.outpatientCharges;

import edu.neu.his.auto.AutoDrugMapper;
import edu.neu.his.auto.NonDrugChargeItemMapper;
import edu.neu.his.bean.department.DepartmentMapper;
import edu.neu.his.bean.drug.Drug;
import edu.neu.his.bean.drug.DrugMapper;
import edu.neu.his.bean.exam.ExamItem;
import edu.neu.his.bean.expenseClassification.ExpenseClassificationMapper;
import edu.neu.his.bean.nondrug.NonDrugChargeItem;
import edu.neu.his.bean.prescription.Prescription;
import edu.neu.his.bean.prescription.PrescriptionItem;
import edu.neu.his.bean.exam.ExamStatus;
import edu.neu.his.bean.prescription.PrescriptionMapper;
import edu.neu.his.bean.prescription.PrescriptionStatus;
import edu.neu.his.bean.exam.ExamItemMapper;
import edu.neu.his.auto.AutoPrescriptionItemMapper;
import edu.neu.his.auto.OutpatientChargesRecordMapper;
import edu.neu.his.bean.registration.OutpatientRegistrationMapper;
import edu.neu.his.bean.registration.Registration;
import edu.neu.his.bean.registration.RegistrationConfig;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChargeAndRefundService {
    @Autowired
    private ChargeAndRefundMapper chargeAndRefundMapper;

    @Autowired
    private OutpatientChargesRecordMapper outpatientChargesRecordMapper;

    @Autowired
    private AutoPrescriptionItemMapper autoPrescriptionItemMapper;

    @Autowired
    private ExamItemMapper examItemMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    private AutoDrugMapper drugMapper;

    @Autowired
    private NonDrugChargeItemMapper nonDrugChargeItemMapper;

    @Autowired
    private ExpenseClassificationMapper expenseClassificationMapper;

    @Autowired
    private OutpatientRegistrationMapper outpatientRegistrationMapper;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Transactional
    public List<OutpatientChargesRecord> findByMedicalRecordIdAndStatus(int medical_record_id,String status){
        List<OutpatientChargesRecord> list = chargeAndRefundMapper.findByMedicalRecordId(medical_record_id);
        List<OutpatientChargesRecord> records = new ArrayList<>();
        list.forEach(outpatientChargesRecord -> {
            if(outpatientChargesRecord.getStatus().equals(status))
                records.add(outpatientChargesRecord);
        });
        return records;
    }

    @Transactional
    public OutpatientChargesRecord findByMedicalRecordIdAndId(int medical_record_id, int id){
        return  chargeAndRefundMapper.findByMedicalRecordIdAndId(medical_record_id, id);
    }

    @Transactional
    public List<OutpatientChargesRecord> findByMedicalRecordIdAndTime(int medical_record_id, String start_time, String end_time){
        return  chargeAndRefundMapper.findByMedicalRecordIdAndTime(medical_record_id, start_time, end_time);
    }

    @Transactional
    public int update (OutpatientChargesRecord outpatientChargesRecord){
        return outpatientChargesRecordMapper.updateByPrimaryKey(outpatientChargesRecord);
    }

    @Transactional
    public Map outpatientChargesRecordToMap(OutpatientChargesRecord record){
        Map res = Utils.objectToMap(record);
        res.put("excute_department", departmentMapper.selectById(record.getExecute_department_id()).getName());
        res.put("expense_classification", expenseClassificationMapper.findClassificationById(record.getExpense_classification_id()));
        String itemName = "";

        if(record.getType() == 0){
            //prescription drug
            PrescriptionItem item = autoPrescriptionItemMapper.selectByPrimaryKey(record.getItem_id());
            res.put("item",Utils.objectToMap(item));
            res.put("fee", res.get("cost"));
            res.put("mount", res.get("amount"));
            Drug drug = drugMapper.selectByPrimaryKey(item.getDrug_id());
            itemName = drug.getName();
            res.put("item_detail",Utils.objectToMap(drug));
        }else if(record.getType() == 1){
            //exam non drug
            ExamItem item = examItemMapper.selectByPrimaryKey(record.getItem_id());
            System.out.println("w "+item);
            System.out.println("w1 "+Utils.objectToMap(item));
            res.put("item",Utils.objectToMap(item));
            res.put("fee", res.get("cost"));
            res.put("amount", 1);
            NonDrugChargeItem drug = nonDrugChargeItemMapper.selectByPrimaryKey(item.getNon_drug_item_id());
            itemName = drug.getName();
            Map drugMap = Utils.objectToMap(drug);
            drugMap.put("price", drug.getFee());
            res.put("item_detail",drugMap);
        }
        res.put("status", record.getStatus());
        res.put("item_name", itemName);
        return res;
    }

    @Transactional
    public int insert(OutpatientChargesRecord record){
        outpatientChargesRecordMapper.insert(record);
        return record.getId();
    }

    @Transactional
    public boolean itemHasReturn(OutpatientChargesRecord record){
        int item_id = record.getItem_id();
        if((int)record.getType()==OutpatientChargesRecordStatus.Prescription){
            PrescriptionItem prescriptionItem = autoPrescriptionItemMapper.selectByPrimaryKey(item_id);
            if(prescriptionItem.getStatus().equals(PrescriptionStatus.PrescriptionItemReturned))
                return true;
            else return false;
        }
        else {
            //System.out.println("-------item_id--------"+item_id);
            //ExamItem examItem = examItemMapper.selectByItemId(item_id);
            ExamItem examItem = examItemMapper.selectByPrimaryKey(item_id);
            //System.out.println("===="+examItem);
            //if(examItem.getStatus().equals(ExamStatus.Cancelled))
            if(examItem.getStatus().equals(ExamStatus.ToRegister))
                return true;
            else return false;
        }
    }

    @Transactional
    public Map statistic(int uid, String start_time, String end_time){
        List<Registration> list = outpatientRegistrationMapper.findByDoctor(uid, RegistrationConfig.registrationFinished,start_time,end_time);
        Map data = new HashMap();
        data.put("count",list.size());
        List<Map> patients = new ArrayList<>();

        for (Registration registration : list) {
            int medical_record_id = registration.getMedical_record_id();
            Map people = new HashMap();
            List<Prescription> prescriptions = prescriptionMapper.selectByMedicalRecordIdAndType(medical_record_id,PrescriptionStatus.PrescriptionItemReturned);
            float sum = 0;
            for (Prescription prescription : prescriptions) {
                OutpatientChargesRecord record = chargeAndRefundMapper.findByMedicalRecordIdAndItemId(medical_record_id,prescription.getId(),OutpatientChargesRecordStatus.Charged);
                sum += record.getCost();
            }
            people.put("medical_record_id", medical_record_id);
            people.put("prescription_money",sum);
            people.put("name",registration.getPatient_name());

            List<OutpatientChargesRecord> examList = chargeAndRefundMapper.findExamRecordByMedicalRecordId(medical_record_id,OutpatientChargesRecordStatus.Exam,start_time,end_time,OutpatientChargesRecordStatus.Charged);
            float exam_sum=0;
            for (OutpatientChargesRecord outpatientChargesRecord : examList) {
                exam_sum += outpatientChargesRecord.getCost();
            }
            people.put("exam_money",exam_sum);

            patients.add(people);
        }
        data.put("patients",patients);

        return data;
    }

    public List<OutpatientChargesRecord> findAll() {
        return outpatientChargesRecordMapper.selectAll();
    }
}
