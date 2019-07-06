package edu.neu.his.bean.workloadstatictic;

import edu.neu.his.bean.diagnosis.MedicalRecordDiagnoseService;
import edu.neu.his.bean.exam.ExamController;
import edu.neu.his.bean.registration.OutpatientRegistrationService;
import edu.neu.his.bean.medicalRecord.MedicalRecordService;
import edu.neu.his.bean.exam.ExamItemResult;
import edu.neu.his.bean.exam.ExamItemResultService;
import edu.neu.his.config.Auth;
import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Registration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//个人工作量统计

@RestController
@RequestMapping("/workloadStatistic")
public class WorkloadStatisticController {

    @Autowired
    ExamItemResultService examItemResultMapper;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    OutpatientRegistrationService outpatientRegistrationService;

    @Autowired
    WorkloadRecordService workloadRecordService;

    @RequestMapping("/doctorOfTechnology")
    @ResponseBody
    public Map doctorOfTechnology(@RequestBody Map req) {
        int uid = Auth.uid(req);
        String start_date = (String)req.get("start_date");
        String end_date = (String)req.get("end_date");
        List<ExamItemResult> examItemResults = examItemResultMapper.allExamItemResult().stream().filter(examItemResult -> {
            if(examItemResult.getUser_id()!=uid) return false;
            try {
                SimpleDateFormat parser =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = parser.parse(examItemResult.getCreate_time());
                return d.after(parser.parse(start_date)) && d.before(parser.parse(end_date));
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList());
        return Response.ok(examItemResults);
    }

    @RequestMapping("/outpatientDoctor")
    @ResponseBody
    public Map outpatientDoctor(@RequestBody Map req) {
        int uid = Auth.uid(req);
        String start_date = (String)req.get("start_date");
        String end_date = (String)req.get("end_date");
        List data = outpatientRegistrationService.findByDoctor(uid).stream().filter(registration -> {
            try {
                SimpleDateFormat parser =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = parser.parse(registration.getConsultation_date());
                return d.after(parser.parse(start_date)) && d.before(parser.parse(end_date));
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList());
        return Response.ok(data);
    }

    @RequestMapping("/userStatistic")
    public Map userStatistic(@RequestBody Map req) {
        int uid = Auth.uid(req);
        String start_date = (String)req.get("start_date");
        String end_date = (String)req.get("end_date");
        return Response.ok(workloadRecordService.userStatistic(start_date,end_date));
    }

    @RequestMapping("/departmentStatistic")
    public Map departmentStatistic(@RequestBody Map req) {
        int uid = Auth.uid(req);
        String start_date = (String)req.get("start_date");
        String end_date = (String)req.get("end_date");
        return Response.ok(workloadRecordService.departmentStatistic(start_date,end_date));
    }

    @RequestMapping("/typeStatistic")
    public Map typeStatistic(@RequestBody Map req) {
        int uid = Auth.uid(req);
        String start_date = (String)req.get("start_date");
        String end_date = (String)req.get("end_date");
        return Response.ok(workloadRecordService.typeStatistic(start_date,end_date));
    }
}
