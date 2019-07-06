package edu.neu.his.bean.outpatientCharges;

import edu.neu.his.bean.billRecord.BillRecord;
import edu.neu.his.bean.department.DepartmentMapper;
import edu.neu.his.bean.department.DepartmentService;
import edu.neu.his.bean.drug.DrugService;
import edu.neu.his.bean.exam.ExamItemService;
import edu.neu.his.bean.exam.Exam;
import edu.neu.his.bean.exam.ExamService;
import edu.neu.his.bean.expenseClassification.ExpenseClassificationService;
import edu.neu.his.bean.nondrug.NonDrugChargeService;
import edu.neu.his.bean.operateLog.OperateLog;
import edu.neu.his.bean.operateLog.OperateStatus;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecord;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecordStatus;
import edu.neu.his.bean.prescription.PrescriptionMapper;
import edu.neu.his.bean.prescription.PrescriptionItemMapper;
import edu.neu.his.bean.prescription.PrescriptionItem;
import edu.neu.his.bean.prescription.Prescription;
import edu.neu.his.bean.registration.Registration;
import edu.neu.his.bean.billRecord.BillRecordStatus;
import edu.neu.his.bean.registration.OutpatientRegistrationService;
import edu.neu.his.bean.registration.RegistrationLevelService;
import edu.neu.his.bean.user.User;
import edu.neu.his.bean.user.UserService;
import edu.neu.his.bean.workloadstatictic.WorkloadRecord;
import edu.neu.his.bean.workloadstatictic.WorkloadRecordService;
import edu.neu.his.bean.workloadstatictic.WorkloadRecordType;
import edu.neu.his.config.*;
import edu.neu.his.bean.billRecord.BillRecordService;
import edu.neu.his.bean.outpatientCharges.ChargeAndRefundService;
import edu.neu.his.bean.operateLog.OperateLogService;
import edu.neu.his.bean.registration.OutpatientRegistrationService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/outpatientCharge")
public class ChargeAndRefundController {
    @Autowired
    private ChargeAndRefundService chargeAndRefundService;

    @Autowired
    private BillRecordService billRecordService;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private OutpatientRegistrationService outpatientRegistrationService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private PrescriptionItemMapper prescriptionItemMapper;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private DrugService drugService;

    @Autowired
    private ExamItemService examItemService;

    @Autowired
    private NonDrugChargeService nonDrugChargeService;

    @Autowired
    private ExpenseClassificationService expenseClassificationService;

    @Autowired
    private WorkloadRecordService workloadRecordService;

    @Autowired
    private ExamService examService;

    @RequestMapping("/getChargeItems")
    @ResponseBody
    public Map info(@RequestBody Map req){
        int medical_record_id = (int) req.get("medical_record_id");
        List<OutpatientChargesRecord> list = chargeAndRefundService.findByMedicalRecordIdAndStatus(medical_record_id, OutpatientChargesRecordStatus.ToCharge);
        List<Map> data = new ArrayList<>();

        for (OutpatientChargesRecord outpatientChargesRecord : list) {

            Map record = Utils.objectToMap(outpatientChargesRecord);
            if(outpatientChargesRecord.getType()==OutpatientChargesRecordStatus.Prescription){
                int drug_id = prescriptionItemMapper.selectById(outpatientChargesRecord.getItem_id()).getDrug_id();
                String name = drugService.selectDrugById(drug_id).getName();
                record.put("item_name",name);
            }else if(outpatientChargesRecord.getType()==OutpatientChargesRecordStatus.Exam){
                //outpatientChargeRecord是一张收费记录表，表里的type 01 表示处方和检查，item_id分别指向非药品项目prescription_item
                System.out.println("v1 "+outpatientChargesRecord.getItem_id());
                System.out.println("v2 "+examItemService.selectByItemId(outpatientChargesRecord.getItem_id()));
                int nondrug_id = examItemService.selectByPrimaryKey(outpatientChargesRecord.getItem_id()).getNon_drug_item_id();
                //int nondrug_id = outpatientChargesRecord.getItem_id();
                //int nondrug_id = examItemService.selectByItemId(outpatientChargesRecord.getItem_id()).getNon_drug_item_id();
                String name = nonDrugChargeService.selectById(nondrug_id).getName();
                record.put("item_name",name);
            }

            String department_name = departmentMapper.selectById(outpatientChargesRecord.getExecute_department_id()).getName();
            record.put("excute_department",department_name);

            String expense_classification = expenseClassificationService.findClassificationById(outpatientChargesRecord.getExpense_classification_id());
            record.put("expense_classification",expense_classification);

            data.add(record);
        }

        return Response.ok(data);
    }

    @RequestMapping("/getHistoryChargeItems")
    @ResponseBody
    public Map historyInfo(@RequestBody Map req){
        int medical_record_id = (int)req.get("medical_record_id");
        String start_time = req.get("start_time") == null ? "1970-1-1 00:00:00" : (String)req.get("start_time");
        String end_time = req.get("end_time") == null ? "9999-12-30 00:00:00" : (String)req.get("end_time");;
        String create_time = Utils.getSystemTime();
        if(start_time.compareTo(end_time)>=0)
            return Response.error("错误，开始时间不小于结束时间!");
        List<OutpatientChargesRecord> list = chargeAndRefundService.findByMedicalRecordIdAndTime(medical_record_id,start_time,end_time);
        List res = new ArrayList();
        for (OutpatientChargesRecord o : list) {
            Map m = chargeAndRefundService.outpatientChargesRecordToMap(o);
            res.add(m);
        }
        return Response.ok(res);
    }

    @RequestMapping("/historyChargeItems")
    @ResponseBody
    public Map getHistoryChargeItems5(@RequestBody Map req){
        if(req.get("medical_record_id")!=null && (int)req.get("medical_record_id")!=0){
            int medical_record_id = (int)req.get("medical_record_id");
            List<OutpatientChargesRecord> list = chargeAndRefundService.findByMedicalRecordIdAndStatus(medical_record_id, OutpatientChargesRecordStatus.Charged);
            List res = new ArrayList();
            for (OutpatientChargesRecord o : list) {
                //System.out.println("obj "+o);
                Map m = chargeAndRefundService.outpatientChargesRecordToMap(o);
                res.add(m);
            }
            return Response.ok(res);
        }else{
            String name = (String)req.get("name");
            List<Integer> medical_record_ids = outpatientRegistrationService.findMedicalRecordIdByName(name);
            List res = new ArrayList();
            for (Integer medical_record_id : medical_record_ids) {
                List list = chargeAndRefundService.findByMedicalRecordIdAndStatus(medical_record_id, OutpatientChargesRecordStatus.Charged);
                res.addAll(list);
            }
            return Response.ok(res);
        }
    }

    @PostMapping("/charge")
    @ResponseBody
    public Map collect(@RequestBody Map req) {
        OperateStatus.initOperateMap();
        int uid = Auth.uid(req);

        Map data = new HashMap();
        List<Integer> IDsNotHave = new ArrayList<>();
        List<Integer> IDsHaveCharged = new ArrayList<>();
        List<OutpatientChargesRecord> recordsToCharge = new ArrayList<>();
        float cost = 0;
        String type = BillRecordStatus.Charge;
        int medical_record_id = (int)req.get("medical_record_id");

        List id_list = (List)req.get("charges_id_list");

        for(int i=0; i<id_list.size(); i++){
            Integer id = (Integer)id_list.get(i);
            OutpatientChargesRecord outpatientChargesRecord = chargeAndRefundService.findByMedicalRecordIdAndId(medical_record_id,id);
            if(outpatientChargesRecord==null){
                IDsNotHave.add(id);
            }else if(!outpatientChargesRecord.getStatus().equals(OutpatientChargesRecordStatus.ToCharge)){
                IDsHaveCharged.add(id);
            }else {
                recordsToCharge.add(outpatientChargesRecord);
                cost += outpatientChargesRecord.getCost();
            }
        }

        if(!IDsHaveCharged.isEmpty() || !IDsNotHave.isEmpty()){
            data.put("ids_not_have",IDsNotHave);
            data.put("ids_have_charged_or_refunded",IDsHaveCharged);
            return Response.error(data);
        }else {
            //生成票据
            BillRecord billRecord = Utils.fromMap(req,BillRecord.class);
            billRecord.setType(type);
            billRecord.setCost(cost);
            billRecord.setMedical_record_id(medical_record_id);
            billRecord.setUser_id(uid);
            billRecord.setCreate_time(Utils.getSystemTime());

            //@XUranus
            billRecord.setPrint_status(0);

            int bill_record_id = billRecordService.insertBillRecord(billRecord);

            //修改收费记录
            recordsToCharge.forEach(outpatientChargesRecord -> {
                outpatientChargesRecord.setStatus(OutpatientChargesRecordStatus.Charged);
                outpatientChargesRecord.setBill_record_id(bill_record_id);
                outpatientChargesRecord.setCollect_time(Utils.getSystemTime());
                outpatientChargesRecord.setCollect_user_id(uid);
                chargeAndRefundService.update(outpatientChargesRecord);

                //对每条收费记录生成对应的操作记录
                String operateType;
                if(outpatientChargesRecord.getType()==0)
                    operateType = OperateStatus.PrescribeMedicine;
                else {
                    int expenseClassificationId = outpatientChargesRecord.getExpense_classification_id();
                    operateType = OperateStatus.operateMap.get(expenseClassificationId);
                }
                String create_time = Utils.getSystemTime();
                OperateLog operateLog = new OperateLog(uid,medical_record_id,operateType,bill_record_id,outpatientChargesRecord.getCost(),create_time);
                operateLogService.insertOperateLog(operateLog);

                //@XUranus收费 创建工作量记录
                createWorkRecord(outpatientChargesRecord,Utils.getSystemUser(req));

            });

            return Response.ok();
        }
    }

    @PostMapping("/withdraw")
    @ResponseBody
    public Map refund(@RequestBody Map req){
        System.out.println("withdraw "+req);
        OperateStatus.initOperateMap();
        int uid = Auth.uid(req);

        Map data = new HashMap();
        List<Integer> IDsNotHave = new ArrayList<>();
        List<Integer> IDsNotCharged = new ArrayList<>();
        List<Integer> IDsNotReturn = new ArrayList<>();
        List<OutpatientChargesRecord> recordsToRefund = new ArrayList<>();
        float cost = 0;
        String type = BillRecordStatus.Refund;
        int medical_record_id = (int)req.get("medical_record_id");

        List id_list = (List)req.get("charges_id_list");

        for(int i=0; i<id_list.size(); i++){
            Integer id = (Integer)id_list.get(i);
            OutpatientChargesRecord outpatientChargesRecord = chargeAndRefundService.findByMedicalRecordIdAndId(medical_record_id,id);
            if(outpatientChargesRecord==null){
                IDsNotHave.add(id);
            }else if(!outpatientChargesRecord.getStatus().equals(OutpatientChargesRecordStatus.Charged)){
                IDsNotCharged.add(id);
            }else if(!chargeAndRefundService.itemHasReturn(outpatientChargesRecord)){
                IDsNotReturn.add(id);
            }
            else {
                recordsToRefund.add(outpatientChargesRecord);
                cost -= outpatientChargesRecord.getCost();
            }
        }

        if(!IDsNotCharged.isEmpty() || !IDsNotHave.isEmpty() || !IDsNotReturn.isEmpty()){
            data.put("ids_not_have",IDsNotHave);
            data.put("ids_not_charged_or_have_refunded",IDsNotCharged);
            data.put("ids_not_cancelled",IDsNotReturn);
            return Response.error(data);
        }else {
            //生成票据
            BillRecord billRecord = new BillRecord();
            billRecord.setCost(cost);
            billRecord.setType(type);
            billRecord.setMedical_record_id(medical_record_id);
            billRecord.setUser_id(uid);
            billRecord.setCreate_time(Utils.getSystemTime());
            //@XUranus
            billRecord.setPrint_status(0);

            int bill_record_id = billRecordService.insertBillRecord(billRecord);

            //修改收费记录
            recordsToRefund.forEach(outpatientChargesRecord -> {
                outpatientChargesRecord.setStatus(OutpatientChargesRecordStatus.Refunded);
                outpatientChargesRecord.setBill_record_id(bill_record_id);
                outpatientChargesRecord.setReturn_time(Utils.getSystemTime());
                outpatientChargesRecord.setReturn_user_id(uid);
                chargeAndRefundService.update(outpatientChargesRecord);

                //对每条收费记录生成对应的操作记录
                String operateType;
                if(outpatientChargesRecord.getType()==0)
                    operateType = OperateStatus.PrescribeMedicine;
                else {
                    int expenseClassificationId = outpatientChargesRecord.getExpense_classification_id();
                    operateType = OperateStatus.operateMap.get(expenseClassificationId);
                }
                String create_time = Utils.getSystemTime();
                OperateLog operateLog = new OperateLog(uid,medical_record_id,operateType,bill_record_id,0-outpatientChargesRecord.getCost(),create_time);
                operateLogService.insertOperateLog(operateLog);
            });

            return Response.ok();
        }
    }

    @RequestMapping("/registrationByRecordId")
    @ResponseBody
    public Map registrationByRecordId(@RequestBody Map req){
        int medical_record_id = (int) req.get("medical_record_id");
        Registration registration = outpatientRegistrationService.findRegistrationById(medical_record_id);
        if(registration==null)
            return Response.error("错误，该挂号信息不存在");
        else {
            Map data = Utils.objectToMap(registration);
            String outpatient_doctor_name = userService.findByUid(registration.getOutpatient_doctor_id()).getReal_name();
            String department_name = departmentMapper.selectById(registration.getRegistration_department_id()).getName();
            data.put("outpatient_doctor_name",outpatient_doctor_name);
            data.put("department_name",department_name);
            return Response.ok(data);
        }
    }

    @RequestMapping("/statistics")
    @ResponseBody
    public Map statistics(@RequestBody Map req){
        int uid = Auth.uid(req);
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        Map data = chargeAndRefundService.statistic(uid,start_time,end_time);
        return Response.ok(data);
    }

    //创建工作量收费记录 从收费操作中提取：检查/检验/ 成药/草药/医技补录
    private void createWorkRecord(OutpatientChargesRecord outpatientChargesRecord,User me) {
        try {
            if(outpatientChargesRecord.getType()==OutpatientChargesRecordStatus.Exam) {
                //检查
                int exam_item_id = outpatientChargesRecord.getItem_id();

                //@DEBUG 修改完毕
                int exam_id = examItemService.selectByPrimaryKey(exam_item_id).getExam_id();

                Exam exam = examService.selectById(exam_id);
                int medical_record_id = exam.getMedical_record_id();
                Registration registration = outpatientRegistrationService.findRegistrationById(medical_record_id);
                String type = "";
                if(exam.getType()==0) type = WorkloadRecordType.JIANCHAFEI;
                else if(exam.getType()==1) type = WorkloadRecordType.JIANYANFEI;
                else if(exam.getType()==2) type = WorkloadRecordType.CHUZHIFEI;

                workloadRecordService.insert(new WorkloadRecord(
                        type,
                        registration.getOutpatient_doctor_id(),
                        outpatientChargesRecord.getCost(),
                        registration.getRegistration_department_id()
                ));
                //创建工作量统计记录
                workloadRecordService.insert(new WorkloadRecord(
                        type,
                        me.getUid(),
                        outpatientChargesRecord.getCost(),
                        me.getDepartment_id()
                ));
            } else {
                //处方
                int prescription_item_id = outpatientChargesRecord.getItem_id();
                int prescription_id = prescriptionItemMapper.selectById(prescription_item_id).getPrescription_id();
                Prescription prescription = prescriptionMapper.selectByPrimaryKey(prescription_id);
                int medical_record_id = prescription.getMedical_record_id();
                Registration registration = outpatientRegistrationService.findRegistrationById(medical_record_id);

                String type = "";
                if(prescription.getType()==0) type = WorkloadRecordType.CHENYAOFEI;
                else if(prescription.getType()==1) type = WorkloadRecordType.CAOYAOFEI;
                if(prescription.getType()==2) type = WorkloadRecordType.JIANCHACAILIAOFEI;

                workloadRecordService.insert(new WorkloadRecord(
                        type,
                        registration.getOutpatient_doctor_id(),
                        outpatientChargesRecord.getCost(),
                        registration.getRegistration_department_id()
                ));
                //创建工作量统计记录
                workloadRecordService.insert(new WorkloadRecord(
                        type,
                        me.getUid(),
                        outpatientChargesRecord.getCost(),
                        me.getDepartment_id()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
