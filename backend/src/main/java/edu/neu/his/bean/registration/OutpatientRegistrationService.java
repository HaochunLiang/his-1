package edu.neu.his.bean.registration;

import edu.neu.his.bean.billRecord.BillRecord;
import edu.neu.his.bean.billRecord.BillRecordService;
import edu.neu.his.bean.billRecord.BillRecordStatus;
import edu.neu.his.bean.operateLog.OperateLog;
import edu.neu.his.bean.operateLog.OperateLogService;
import edu.neu.his.bean.operateLog.OperateStatus;
import edu.neu.his.bean.user.User;
import edu.neu.his.bean.user.UserService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OutpatientRegistrationService {
    @Autowired
    private OutpatientRegistrationMapper outpatientRegistrationMapper;

    @Autowired
    private BillRecordService billRecordService;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private UserService userService;

    @Transactional
    public List<User> findByDepartmentAndRegistrationLevel(int department_id, int registration_level_id){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        //System.out.println(df.format(new Date()));
        String curr_date = df.format(new Date());
        return outpatientRegistrationMapper.findByDepartmentAndTitle(department_id,registration_level_id,curr_date);
    }

    @Transactional
    public int insertRegistration(Registration registration){
        outpatientRegistrationMapper.insert(registration);
        return  registration.getMedical_record_id();
    }

    @Transactional
    public Registration findRegistrationById(int medical_record_id){
        return outpatientRegistrationMapper.findRegistrationById(medical_record_id);
    }

    @Transactional
    public void updateStatus(Registration registration){
       outpatientRegistrationMapper.update(registration);
    }

    @Transactional
    public List<Registration> findByDoctor(int id){
        return outpatientRegistrationMapper.findRegistrationByDoctor(id);
    }

    @Transactional
    public List<Integer> findMedicalRecordIdByName(String name){
        return outpatientRegistrationMapper.findMedicalRecordIdByName(name);
    }

    @Transactional
    public void cancelRegistration(Registration registration, int uid){
        //退号
        registration.setStatus(RegistrationConfig.registrationCanceled);
        updateStatus(registration);

        //冲正票据记录
        String billType = BillRecordStatus.Refund;
        BillRecord billRecord = registrationToWithdrawBill(registration,billType,uid);
        int bill_record_id = billRecordService.insertBillRecord(billRecord);

        //添加操作记录
        String operateType = OperateStatus.Cancel;
        OperateLog operateLog = registrationToWithdrawOperateLog(registration,operateType,bill_record_id,uid);
        operateLogService.insertOperateLog(operateLog);
    }

    @Transactional
    public Map createRegistration(Registration registration, Map req, float fee, int uid){
        Map data = new HashMap();
        String create_time = Utils.getSystemTime();

        //创建票据记录
        BillRecord billRecord = Utils.fromMap(req,BillRecord.class);
        if(billRecord==null)
            return null;

        //挂号记录
        registration.setRegistration_department_id((int)req.get("department_id"));
        int medical_record_number = insertRegistration(registration);
        data.put("medical_record_number", medical_record_number);//病历号

        //设置票据记录的字段
        billRecord.setType(BillRecordStatus.Charge);
        billRecord.setCost(fee);
        billRecord.setMedical_record_id(medical_record_number);
        billRecord.setCreate_time(create_time);
        billRecord.setUser_id(uid);

        //@XUranus: bill_record print_status默认要是0
        billRecord.setPrint_status(0);

        int bill_record_id = billRecordService.insertBillRecord(billRecord);
        data.put("bill",billRecord);

        //操作记录
        String operateType = OperateStatus.Register;
        OperateLog operateLog = new OperateLog(uid,medical_record_number,operateType,bill_record_id,fee,create_time);
        operateLogService.insertOperateLog(operateLog);

        return data;
    }

    private OperateLog registrationToWithdrawOperateLog(Registration registration, String type,int bill_record_id, int uid){
        String create_time = Utils.getSystemTime();
        int medical_record_number = registration.getMedical_record_id();
        float fee = 0 - registration.getCost();

        OperateLog operateLog = new OperateLog(uid,medical_record_number,type,bill_record_id,fee,create_time);

        return operateLog;
    }

    private BillRecord registrationToWithdrawBill(Registration registration, String type,int uid){
        BillRecord billRecord = new BillRecord();
        billRecord.setCost(0-registration.getCost());
        billRecord.setMedical_record_id(registration.getMedical_record_id());
        billRecord.setUser_id(uid);
        billRecord.setType(type);
        billRecord.setCreate_time(Utils.getSystemTime());
        //@XUranus
        billRecord.setPrint_status(0);

        return billRecord;
    }
}
