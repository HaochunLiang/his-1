package edu.neu.his.bean.exam;

import edu.neu.his.bean.nondrug.NonDrugChargeItem;
import edu.neu.his.bean.nondrug.NonDrugChargeItemMapper;
import edu.neu.his.bean.registration.OutpatientRegistrationMapper;
import edu.neu.his.bean.registration.Registration;
import edu.neu.his.bean.user.User;
import edu.neu.his.bean.workloadstatictic.WorkloadRecord;
import edu.neu.his.bean.workloadstatictic.WorkloadRecordService;
import edu.neu.his.bean.workloadstatictic.WorkloadRecordType;
import edu.neu.his.config.Response;
import edu.neu.his.util.Common;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/examExcute")
public class ExamExecuteController {

    @Autowired
    ExamItemService examItemService;

    @Autowired
    ExamService examService;

    @Autowired
    WorkloadRecordService workloadRecordService;

    @Autowired
    OutpatientRegistrationMapper outpatientRegistrationMapper;

    @Autowired ExamItemResultService examItemResultMapper;

    @Autowired
    NonDrugChargeItemMapper nonDrugChargeItemMapper;

    @RequestMapping("/searchRegistration")
    public Map searchRegistration(@RequestBody Map req){
        int type = (int) req.get("type");
        String input = (String) req.get("input");
        List res = new ArrayList();
        Registration registration = null;
        switch (type){
            case 0:
                res = outpatientRegistrationMapper.findMedicalRecordLikeName(input);
                break;
            case 1:
                res = outpatientRegistrationMapper.findRegistrationByIdNumber(input);
                break;
            case 2:
                res = outpatientRegistrationMapper.findRegistrationByMedicalCertificateNumber(input);
                break;
            case 3:
                registration = outpatientRegistrationMapper.findRegistrationById(Integer.parseInt(input));
                break;
        }
        if(registration != null){
            res.add(registration);
        }
        return Response.ok(res);
    }

    @PostMapping("/register")
    public Map register(@RequestBody Map req){
        List<Integer> examItemIds = (List<Integer>) req.get("exam_item_id");
        if(examItemService.register(examItemIds)){
            return Response.ok();
        }else {
            return Response.error("列表错误!");
        }
    }

    @PostMapping("/allExam")
    public Map allExam(@RequestBody Map req){
        int medicalRecordId = (int) req.get("medical_record_id");
        List res = examService.list(medicalRecordId, Utils.getSystemUser(req));
        return Response.ok(res);
    }

    @RequestMapping("/submitResult")
    public Map submitResult(@RequestBody Map req){
        int examItemId = (int) req.get("exam_item_id");
        ExamItem examItem = examItemService.selectByPrimaryKey(examItemId);
        if(examItem == null){
            return Response.error("没有该检查!");
        }
        if(!examItem.getStatus().equals(Common.YIDENGJI)){
            return Response.error("该检查尚未登记!");
        }
        ExamItemResult examItemResult = Utils.fromMap(req, ExamItemResult.class);
        String DBFile = "";
        List<Map> images = (List<Map>) req.get("images");
        for (Map image : images) {
            String file = (String) image.get("name");
            file += "&";
            file += (String) image.get("url");
            file += ";";
            DBFile += file;
        }
        examItemResult.setFile(DBFile);
        examItemResultMapper.insertOrUpdate(examItemResult);
        examItem.setStatus(Common.YIWANCHENG);
        examItemService.update(examItem);
        createWorkloadRecord(examItem,Utils.getSystemUser(req));
        return Response.ok();
    }

    @PostMapping("/getResult")
    public Map getResult(@RequestBody Map req){
        int examItemId = (int)req.get("exam_item_id");
        ExamItemResult examItemResult = examItemResultMapper.selectByExamItemId(examItemId);
        if(examItemResult == null)return Response.error("该检查结果尚未录入!");
        Map res = Utils.objectToMap(examItemResult);
        String files = examItemResult.getFile();
        String[] filenames = files.split(";");
        List fileList = new ArrayList();

        //容错 没有图片的时侯 filenames长度1,filesnames[0]是空串
        if(filenames.length==1 && filenames[0].equals(""))
            filenames = new String[]{};

        for (String filename : filenames) {
            String[] nameArr = filename.split("&");
            HashMap fileMap = new HashMap();
            String name = nameArr[0];
            String url = nameArr[1];
            fileMap.put("name", name);
            fileMap.put("url", url);
            fileList.add(fileMap);
        }
        res.put("images", fileList);
        return Response.ok(res);
    }

    //提交检查 完成工作 创建检查/检验的记录
    private void createWorkloadRecord(ExamItem examItem, User me) {
        try {
            Exam exam = examService.selectById(examItem.getExam_id());
            String type = "";
            if(exam.getType()==0) type = WorkloadRecordType.JIANCHAFEI;
            else if(exam.getType()==1) type = WorkloadRecordType.JIANYANFEI;
            else if(exam.getType()==2) type = WorkloadRecordType.CHUZHIFEI;
            NonDrugChargeItem nonDrugChargeItem = nonDrugChargeItemMapper.selectByPrimaryKey(examItem.getNon_drug_item_id());

            //System.out.println("examItem    :"+examItem);
            //System.out.println("exam    :"+exam);
            //System.out.println("nonDrugItem:   "+nonDrugChargeItem);


            //创建工作量统计记录
            workloadRecordService.insert(new WorkloadRecord(
                    type,
                    me.getUid(),
                    nonDrugChargeItem.getFee(),
                    me.getDepartment_id()
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
