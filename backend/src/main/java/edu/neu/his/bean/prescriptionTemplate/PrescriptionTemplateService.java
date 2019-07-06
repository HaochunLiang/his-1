package edu.neu.his.bean.prescriptionTemplate;

import edu.neu.his.auto.AutoDrugMapper;
import edu.neu.his.auto.OutpatientChargesRecordMapper;
import edu.neu.his.auto.PrescriptionTemplateItemMapper;
import edu.neu.his.auto.PrescriptionTemplateMapper;
import edu.neu.his.bean.drug.Drug;
import edu.neu.his.bean.drug.DrugService;
import edu.neu.his.bean.user.User;
import edu.neu.his.bean.medicalRecord.MedicalRecordService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PrescriptionTemplateService {

    @Autowired
    PrescriptionTemplateMapper prescriptionTemplateMapper;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    PrescriptionTemplateItemMapper itemMapper;

    @Autowired
    AutoDrugMapper drugMapper;

    @Autowired
    DrugService drugService;

    @Autowired
    OutpatientChargesRecordMapper outpatientChargesRecordMapper;


    @Transactional
    public int create(Map req, User user, String name, List<Map> drugIds){
        PrescriptionTemplate prescription = Utils.fromMap(req, PrescriptionTemplate.class);
        prescription.setCreate_time(Utils.getSystemTime());
        prescription.setTemplate_name(name);
        prescription.setUser_id(user.getUid());
        prescription.setDepartment_id(user.getDepartment_id());
        prescriptionTemplateMapper.insert(prescription);
        addItems(prescription.getId(), drugIds);
        return prescription.getId();
    }

    @Transactional
    public boolean recordMedicalHasSubmit(int id){
        return medicalRecordService.medicalRecordHasSubmit(id);
    }

    @Transactional
    public String rename(String templateName){
        while(prescriptionTemplateMapper.selectByName(templateName)!=null){
            templateName += "(1)";
        }
        return templateName;
    }

    @Transactional
    public void addItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionTemplateItem prescriptionItem = new PrescriptionTemplateItem();
            prescriptionItem = Utils.fromMap(i, PrescriptionTemplateItem.class);
            prescriptionItem.setDrug_id((Integer)i.get("drug_id"));
            prescriptionItem.setPrescription_template_id(prescriptionId);
            itemMapper.insert(prescriptionItem);
        }
    }

    @Transactional
    public void updateItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionTemplateItem prescriptionItem = itemMapper.selectByDetail(prescriptionId, (Integer) i.get("id"));
            prescriptionItem.setAmount((Integer) i.get("amount"));
            prescriptionItem.setNote((String)i.get("note"));
            itemMapper.updateByPrimaryKey(prescriptionItem);
        }
    }

    @Transactional
    public void removeItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionTemplateItem item = itemMapper.selectByDetail(prescriptionId, (Integer) i.get("drug_id"));
            itemMapper.deleteByPrimaryKey(item.getId());
        }
    }


    @Transactional
    public void removeAllItems(int prescriptionId){
        itemMapper.deleteAllByPrescriptionId(prescriptionId);
    }

    @Transactional
    public List<Map> items(int prescriptionId){
        List<PrescriptionTemplateItem> items = itemMapper.selectByPrescriptionId(prescriptionId);
        List<Map> res = new ArrayList<>();
        for(PrescriptionTemplateItem item:items){
            Map map = Utils.objectToMap(item);
            Drug drug = drugMapper.selectByPrimaryKey(item.getDrug_id());
            map.put("drug_item", Utils.objectToMap(drug));
            res.add(map);
        }
        return res;
    }

    @Transactional
    public Map detail(int prescriptionId){
        PrescriptionTemplate prescriptionTemplate = prescriptionTemplateMapper.selectByPrimaryKey(prescriptionId);
        Map res = Utils.objectToMap(prescriptionTemplate);
        res.put("items", items(prescriptionId));
        return res;
    }

    @Transactional
    public List<PrescriptionTemplate> findAll(User user){
       return prescriptionTemplateMapper.selectAll().stream().filter(item->{
           switch (item.getDisplay_type()){
               case 0:
                   if(item.getUser_id() == user.getUid()){
                       return true;
                   }
                   break;
               case 1:
                   if(item.getDepartment_id() == user.getDepartment_id()){
                       return true;
                   }
                   break;
               case 2:
                   return true;
           }
           return false;
       }).collect(Collectors.toList());
    }

    @Transactional
    public void delete(int id){
        List<PrescriptionTemplateItem> list = itemMapper.selectByPrescriptionId(id);
        for (PrescriptionTemplateItem item : list) {
            itemMapper.deleteByPrimaryKey(item.getId());
        }
        prescriptionTemplateMapper.deleteByPrimaryKey(id);
    }


    @Transactional
    public PrescriptionTemplate selectById(int id){
        return prescriptionTemplateMapper.selectByPrimaryKey(id);
    }


}
