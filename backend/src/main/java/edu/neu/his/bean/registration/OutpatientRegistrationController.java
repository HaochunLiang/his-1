package edu.neu.his.bean.registration;

import edu.neu.his.bean.billRecord.BillRecordService;
import edu.neu.his.bean.department.DepartmentService;
import edu.neu.his.bean.operateLog.OperateLogService;
import edu.neu.his.bean.settlementCategory.SettlementCategoryService;
import edu.neu.his.bean.user.User;
import edu.neu.his.bean.workloadstatictic.WorkloadRecord;
import edu.neu.his.bean.workloadstatictic.WorkloadRecordService;
import edu.neu.his.bean.workloadstatictic.WorkloadRecordType;
import edu.neu.his.config.*;
import edu.neu.his.util.Utils;
import edu.neu.his.util.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//4.1 挂号
@RestController
@RequestMapping("/outpatientRegistration")
public class OutpatientRegistrationController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RegistrationLevelService registrationLevelService;

    @Autowired
    private SettlementCategoryService settlementCategoryService;

    @Autowired
    private OutpatientRegistrationService outpatientRegistrationService;

    @Autowired
    private WorkloadRecordService workloadRecordService;

    @RequestMapping("/init")
    @ResponseBody
    public Map init(){
        Map data = new HashMap();
        data.put("departments", departmentService.findAll());
        data.put("defaultRegistrationLevel",registrationLevelService.findDefault());
        data.put("registrationLevel",registrationLevelService.findAll());
        data.put("settlementCategory",settlementCategoryService.findAll());
        return Response.ok(data);
    }

    @PostMapping("/syncDoctorList")
    @ResponseBody
    public Map syncDoctorList(@RequestBody Map req){
        int department_id = (int)req.get("department_id");
        int registration_level_id = (int)req.get("registration_level_id");
        List<User> users = outpatientRegistrationService.findByDepartmentAndRegistrationLevel(department_id,registration_level_id);
        for (User user : users) {
            user.setPassword(null);
        }
        return Response.ok(users);
    }

    @PostMapping("/calculateFee")
    @ResponseBody
    public Map calulateFee(@RequestBody Map req){
        Map data = new HashMap();
        int registration_level_id = (int)req.get("registration_level_id");
        RegistrationLevel registration_level = registrationLevelService.findById(registration_level_id);
        if(registration_level!=null) {
            float fee = registration_level.getFee();
            int has_record_book = (int) req.get("has_record_book");
            if (has_record_book == 1)
                fee++;

            data.put("fee", fee);
            return Response.ok(data);
        }else
            return Response.error("错误，挂号类别不存在");
    }

    @PostMapping("/confirm")
    @ResponseBody
    public Map confirm(@RequestBody Map req) {
        int uid = Auth.uid(req);
        Registration registration = Utils.fromMap(req,Registration.class);
        String medical_certificate_number_type = (String)req.get("medical_certificate_number_type");

        if(medical_certificate_number_type.equals("id")){
            registration.setId_number((String)req.get("medical_certificate_number"));
            registration.setMedical_certificate_number("");
        }else{
            registration.setMedical_certificate_number((String)req.get("medical_certificate_number"));
            registration.setId_number("");
        }

        if(registration.getAddress()==null)
            registration.setAddress("");
        if(registration.getRegistration_source()==null)
            registration.setRegistration_source((String)req.get("registration_source"));

        int registration_level_id = (int)req.get("registration_level_id");
        RegistrationLevel registration_level = registrationLevelService.findById(registration_level_id);
        if(registration_level==null)
            return Response.error("错误，挂号类别不存在");

        String registration_category = registration_level.getName();
        float fee = registration_level.getFee();
        int has_record_book = (int)req.get("has_record_book");
        if(has_record_book==1)
            fee++;
        registration.setCost(fee);
        registration.setRegistration_category(registration_category);
        registration.setStatus(RegistrationConfig.registrationAvailable);

        //新增
        if(registration.getMedical_insurance_diagnosis()==null)
            registration.setMedical_insurance_diagnosis("无");

        Map data = outpatientRegistrationService.createRegistration(registration,req,fee,uid);
        if(data==null)
            return Response.error("错误，票据记录创建失败");
        else {
            try {
                WebSocketServer.sendInfo("registration","你有新的挂号："+registration.getPatient_name(),""+registration.getOutpatient_doctor_id());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //@创建工作量统计记录
            workloadRecordService.insert(new WorkloadRecord(
                    WorkloadRecordType.GUAHAOFEI,
                    registration.getOutpatient_doctor_id(),
                    registration.getCost(),
                    registration.getRegistration_department_id()
            ));
            //创建工作量统计记录
            User me = Utils.getSystemUser(req);
            workloadRecordService.insert(new WorkloadRecord(
                    WorkloadRecordType.GUAHAOFEI,
                    me.getUid(),
                    registration.getCost(),
                    me.getDepartment_id()
            ));

            return Response.ok(data);
        }
    }

    @PostMapping("/withdrawNumber")
    @ResponseBody
    public Map withdrawNumber(@RequestBody Map req){
        int medical_record_id = (int)req.get("medical_record_id");
        int uid = Auth.uid(req);

        Registration registration = outpatientRegistrationService.findRegistrationById(medical_record_id);
        if(registration==null){
            return Response.error("该病历号不存在");
        }else if(registration.getStatus().equals(RegistrationConfig.registrationAvailable)) {
            try {
                WebSocketServer.sendInfo("cancelRegistration","病人已退号："+registration.getPatient_name(),""+registration.getOutpatient_doctor_id());
            } catch (Exception e) {
                e.printStackTrace();
            }
            outpatientRegistrationService.cancelRegistration(registration,uid);
            return Response.ok();
        }else {
            return Response.error("不可退号");
        }
    }

}