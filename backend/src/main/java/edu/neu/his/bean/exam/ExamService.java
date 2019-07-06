package edu.neu.his.bean.exam;

import edu.neu.his.bean.outpatientCharges.ChargeAndRefundMapper;
import edu.neu.his.bean.outpatientCharges.ChargeAndRefundService;
import edu.neu.his.bean.medicalRecord.MedicalRecordService;
import edu.neu.his.bean.nondrug.NonDrugChargeItem;
import edu.neu.his.bean.nondrug.NonDrugChargeService;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecordStatus;
import edu.neu.his.bean.user.User;
import edu.neu.his.util.Common;
import edu.neu.his.util.Utils;
import edu.neu.his.util.ExcelImportation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ExamService {
    @Autowired
    ExamMapper examMapper;

    @Autowired
    ExamItemMapper examItemMapper;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    NonDrugChargeService nonDrugChargeService;

    @Autowired
    ChargeAndRefundService chargeAndRefundService;

    @Autowired
    ChargeAndRefundMapper chargeAndRefundMapper;

    @Transactional
    public int insertItem(ExamItem item){
        return examItemMapper.insert(item);
    }

    @Transactional
    public boolean medicalRecordHasSubmit(Exam exam){
        String medicalRecordStatus = medicalRecordService.getStatusById(exam.getMedical_record_id());
        if(!Common.YITIJIAO.equals(medicalRecordStatus)){
            return false;
        }
        return true;
    }

    @Transactional
    public int insert(Exam exam){
        return examMapper.insert(exam);
    }

    @Transactional
    public List<Integer> getNonDrugItemIdListById(Integer id){
        List<ExamItem> itemList = examItemMapper.selectByExamId(id);
        return itemList.stream().map(ExamItem::getNon_drug_item_id).collect(Collectors.toList());
    }

    @Transactional
    public List<ExamItem> getExamItemListById(Integer id){
        List<ExamItem> itemList = examItemMapper.selectByExamId(id);
        return itemList;
    }

    @Transactional
    public Exam selectById(Integer id){
        return examMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int updateByPrimaryKey(Exam record){
        return examMapper.updateByPrimaryKey(record);
    }

    public List<Exam> selectByMedicalRecordIdAndType(int medicalRecordId, int type){
        List<Exam> list = examMapper.selectAll();
        List<Exam> res = new ArrayList<>();
        for(Exam exam:list){
            if(exam.getMedical_record_id() == medicalRecordId && exam.getType()==type){
                res.add(exam);
            }
        }
        return res;
    }

    @Transactional
    public List list(int medicalRecordId, User user){
        List res = new ArrayList();
        List<Exam> examList = examMapper.selectByMedicalRecordId(medicalRecordId);
        //System.out.println("ExamService.java 192 examList没有过滤掉不可见的");
        System.out.println("examList:"+examList);
        for (Exam exam : examList) {
            Map examMap = Utils.objectToMap(exam);
            List<ExamItem> examItemList = examItemMapper.selectByExamId(exam.getId());
            List itemList = new ArrayList();
            for (ExamItem examItem : examItemList) {
                Map examItemMap = Utils.objectToMap(examItem);
                NonDrugChargeItem nonDrugChargeItem = nonDrugChargeService.selectById(examItem.getNon_drug_item_id());
                examItemMap.put("non_drug_item", Utils.objectToMap(nonDrugChargeItem));
                //if(nonDrugChargeItem.getDepartment_id() == user.getDepartment_id()){
                System.out.println("ExamService.java 103 由于调试暂时不过滤科室");
                //System.out.println("examItem:"+examItem);
                if(examItemCharged(examItem.getId()))
                    itemList.add(examItemMap);
                //}
            }
            examMap.put("items", itemList);
            res.add(examMap);
        }
        return res;
    }


    @Transactional
    public List<NonDrugChargeItem> allItemsByType(int type){
        return nonDrugChargeService.findAll().stream().filter(item->{
            if(type == 0 && item.getExpense_classification_id() == 3){
                return true;
            }else if(type == 1 && item.getExpense_classification_id() == 7){
                return true;
            }else if(type == 2 && item.getExpense_classification_id() == 16){
                return true;
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Transactional
    public int delete(int id){
        return examMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public boolean deleteAllItemById(int id) {
        List<ExamItem> list = examItemMapper.selectByExamId(id);
        for (ExamItem o : list) {
            if(examItemMapper.deleteByPrimaryKey(o.getId()) != 1){
                return false;
            }
        }
        return true;
    }

    public boolean examItemCharged(int item_id) {
        //System.out.println("wwwwxwxw "+chargeAndRefundMapper.findByItemIdAndType(item_id,OutpatientChargesRecordStatus.Exam));
        //System.out.println("examItemCharges "+item_id);
        return chargeAndRefundMapper.findByItemIdAndType(item_id,OutpatientChargesRecordStatus.Exam).getStatus().equals(OutpatientChargesRecordStatus.Charged);
    }

    public List listByType(int type, int medicalRecordId, User systemUser) {
        List<Exam> examList = selectByMedicalRecordIdAndType(medicalRecordId, type);
        List res = new ArrayList();
        for (Exam exam : examList) {
            Map examMap = Utils.objectToMap(exam);
            if(exam.getType()!=type) continue;
            List<ExamItem> examItemList = examItemMapper.selectByExamId(exam.getId());
            List itemList = new ArrayList();
            for (ExamItem examItem : examItemList) {
                Map examItemMap = Utils.objectToMap(examItem);
                NonDrugChargeItem nonDrugChargeItem = nonDrugChargeService.selectById(examItem.getNon_drug_item_id());
                examItemMap.put("non_drug_item", Utils.objectToMap(nonDrugChargeItem));
                itemList.add(examItemMap);
            }
            examMap.put("exam_item", itemList);
            res.add(examMap);
        }
        return res;
    }
}
