package edu.neu.his.bean.exam;

import edu.neu.his.auto.ExamItemResultMapper;
import edu.neu.his.bean.medicalRecord.MedicalRecordService;
import edu.neu.his.bean.nondrug.NonDrugChargeItem;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecord;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecordStatus;
import edu.neu.his.config.Response;
import edu.neu.his.auto.OutpatientChargesRecordMapper;
import edu.neu.his.bean.nondrug.NonDrugChargeService;
import edu.neu.his.util.Common;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.parameters.P;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@RestController
@RequestMapping("/exam")
public class ExamController {
    /*
    drop table if exists exam;
    create table exam
    (
        id                int not null auto_increment primary key,
        medical_record_id int not null references medical_record (id),
        type              int not null, # 0 检查 1 检验 2 处置
        create_time       varchar(50),
        user_id           int,
        status            varchar(50)   # 暂存, 已作废, 已提交
    );
     */

    private String filePath;

    @Autowired
    ExamService examService;

    @Autowired
    ExamItemService examItemService;

    @Autowired
    NonDrugChargeService nonDrugChargeService;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    OutpatientChargesRecordMapper outpatientChargesRecordMapper;

    @Autowired
    ExamItemResultService examItemResultMapper;

    @PostMapping("/allItems")
    public Map allItemsByType(@RequestBody Map req){
        List res = examService.allItemsByType((Integer) req.get("type"));
        return Response.ok(res);
    }

    @PostMapping("/cancel")
    public Map cancel(@RequestBody Map req){
        List<Integer> ids = (List<Integer>) req.get("id");
        for (Integer id : ids) {
            Exam exam = examService.selectById(id);
            if(exam == null) return Response.error("没有该检查!");
            exam.setStatus(Common.YIZUOFEI);
            //todo::此处没有更新缴费状态
            examService.updateByPrimaryKey(exam);
        }
        return Response.ok();
    }

    @PostMapping("/create")
    public Map create(@RequestBody Map req){
//         = examService.selectByMedicalRecordIdAndType((int)req.get("medical_record_id"), (int)req.get("type"));
//        if(exam != null){
//            return Response.ok(exam);
//        }
        Exam exam = Utils.fromMap(req, Exam.class);
        if(!medicalRecordService.hasSubmit(exam.getMedical_record_id())){
            return Response.error("病历状态错误!");
        }
        List<Integer> nonDrugIdList = (List<Integer>) req.get("non_drug_id_list");
        for (Integer id: nonDrugIdList) {
            if(!nonDrugChargeService.exist(nonDrugChargeService.selectById(id))){
                return Response.error("列表错误!");
            }
        }
        exam.setCreate_time(Utils.getSystemTime());
        exam.setStatus(Common.ZANCUN);
        examService.insert(exam);
        Integer examId = exam.getId();
        List<Integer> resultList = new ArrayList<>();
        nonDrugIdList.forEach(nonDrugId -> {
            ExamItem examItem = new ExamItem();
            examItem.setExam_id(examId);
            examItem.setNon_drug_item_id(nonDrugId);
            examItem.setStatus(Common.WEIDENGJI);
            examItemService.insert(examItem);
            if(examItem.getId() > 0)
                resultList.add(examItem.getId());
        });
        Map<String, Object> res = new HashMap();
        res.put("id", examId);
        res.put("non_drug_item_id", examService.getNonDrugItemIdListById(exam.getId()));
        return Response.ok(res);
    }

    @PostMapping("/update")
    public Map update(@RequestBody Map req){
        int examId = (int) req.get("id");
        if(examService.selectById(examId)==null){
            return Response.error("没有该检查单!");
        }
        List<Integer> nonDrugIdList = (List<Integer>) req.get("non_drug_id_list");
        for (Integer id: nonDrugIdList) {
            if(!nonDrugChargeService.exist(nonDrugChargeService.selectById(id))){
                return Response.error("列表错误!");
            }
        }
        examService.deleteAllItemById(examId);
        nonDrugIdList.forEach(nonDrugId -> {
            ExamItem examItem = new ExamItem();
            examItem.setExam_id(examId);
            examItem.setNon_drug_item_id(nonDrugId);
            examItem.setStatus(Common.WEIDENGJI);
            examItemService.insert(examItem);
        });
        return Response.ok();
    }

    public Map addOne(@RequestBody Map map) throws IOException {
        int examId = (int)map.get("exam_id");
        Exam exam = examService.selectById(examId);
        if(exam == null){
            return Response.error("找不到该检查/检验/处置单!");
        }
        if(!exam.getStatus().equals(Common.ZANCUN)){
            return Response.error("该检查/检验/处置单状态错误!");
        }
        List<Integer> nonDrugIds = (List<Integer>) map.get("non_drug_id");
        for (Integer id: nonDrugIds) {
            if(!nonDrugChargeService.exist(nonDrugChargeService.selectById(id))){
                return Response.error("列表错误!");
            }
        }
        nonDrugIds.forEach(id->{
            ExamItem examItem = new ExamItem();
            examItem.setStatus(Common.WEIDENGJI);
            examItem.setNon_drug_item_id(id);
            examItem.setExam_id(examId);
            examItemService.insert(examItem);
        });
        Map<String, Object> res = new HashMap();
        res.put("non_drug_item_id", examService.getNonDrugItemIdListById(exam.getId()));
        return Response.ok(res);
    }

    @PostMapping("/send")
    public Map submit(@RequestBody Map map){
        List<Integer> examIds = (List<Integer>) map.get("id");
        for (Integer examId : examIds) {
            Exam exam = examService.selectById(examId);
            if(exam == null){
                return Response.error("找不到该检查/检验/处置单!");
            }
            if(!exam.getStatus().equals(Common.ZANCUN)){
                return Response.error("该检查/检验/处置单状态错误!");
            }
            exam.setStatus(Common.YITIJIAO);
            examService.updateByPrimaryKey(exam);

            List<ExamItem> examItemList = examService.getExamItemListById(examId);
            examItemList.forEach(examItem->{
                NonDrugChargeItem nonDrugChargeItem = nonDrugChargeService.selectById(examItem.getNon_drug_item_id());
                OutpatientChargesRecord record = new OutpatientChargesRecord();
                record.setCreate_time(Utils.getSystemTime());
                record.setMedical_record_id(exam.getMedical_record_id());
                record.setBill_record_id(0);
                record.setItem_id(examItem.getId());//examItemId
                record.setType(OutpatientChargesRecordStatus.Exam);
                record.setExpense_classification_id(nonDrugChargeItem.getExpense_classification_id());
                record.setStatus(Common.WEIJIAOFEI);
                record.setQuantity(1);
                record.setCost(nonDrugChargeItem.getFee());
                record.setCollect_time("");
                record.setExecute_department_id(Utils.getSystemUser(map).getDepartment_id());
                record.setCreate_time(Utils.getSystemTime());
                record.setCollect_time("");
                record.setReturn_time("");
                record.setCreate_user_id(Utils.getSystemUser(map).getUid());
                record.setCollect_user_id(0);
                record.setReturn_user_id(0);
                outpatientChargesRecordMapper.insert(record);
            });
            /*List<Integer> nonDrugIdList = examService.getNonDrugItemIdListById(examId);
            nonDrugIdList.forEach(itemId->{
                NonDrugChargeItem nonDrugChargeItem = nonDrugChargeService.selectById(itemId);
                OutpatientChargesRecord record = new OutpatientChargesRecord();
                record.setCreate_time(Utils.getSystemTime());
                record.setMedical_record_id(exam.getMedical_record_id());
                record.setBill_record_id(0);
                record.setItem_id(ex);//examItemId
                record.setType(OutpatientChargesRecordStatus.Exam);
                record.setExpense_classification_id(nonDrugChargeItem.getExpense_classification_id());
                record.setStatus(Common.WEIJIAOFEI);
                record.setQuantity(1);
                record.setCost(nonDrugChargeItem.getFee());
                record.setCollect_time("");
                record.setExecute_department_id(Utils.getSystemUser(map).getDepartment_id());
                record.setCreate_time(Utils.getSystemTime());
                record.setCollect_time("");
                record.setReturn_time("");
                record.setCreate_user_id(Utils.getSystemUser(map).getUid());
                record.setCollect_user_id(0);
                record.setReturn_user_id(0);
                outpatientChargesRecordMapper.insert(record);
            });*/
        }
        return Response.ok();
    }

    @PostMapping("/delete")
    public Map delete(@RequestBody Map req){
        List<Integer> examIds = (List<Integer>) req.get("id");
        for (Integer examId : examIds) {

            //@XUranus：他们忘记删除item了 之后检查会报错
            examService.getExamItemListById(examId).stream().forEach(examItem -> {
                examItemService.deleteById(examItem.getId());
            });
            //TODO::同理处方也是

            if(examService.delete(examId) != 1){
                return Response.error("列表错误!");
            }
        }
        return Response.ok();
    }

    public Map delOne(@RequestBody Map map) {
        int examId = (int)map.get("exam_id");
        Exam exam = examService.selectById(examId);
        if(exam == null){
            return Response.error("找不到该检查/检验/处置单!");
        }
        if(!exam.getStatus().equals(Common.ZANCUN)){
            return Response.error("该检查/检验/处置单状态错误!");
        }

        List<Integer> nonDrugIds = (List<Integer>) map.get("non_drug_id");
        for (Integer id: nonDrugIds) {
            if(!nonDrugChargeService.exist(nonDrugChargeService.selectById(id))){
                return Response.error("列表错误!");
            }
        }
        nonDrugIds.forEach(nonDrugId->{
            ExamItem examItem = examItemService.selectByDetail(nonDrugId, examId);
            examItemService.deleteById(examItem.getId());
        });
        Map<String, Object> res = new HashMap();
        res.put("non_drug_item_id", examService.getNonDrugItemIdListById(exam.getId()));
        return Response.ok(res);
    }

    @PostMapping("/list")
    public Map list(@RequestBody Map req){
        int medicalRecordId = (int) req.get("medical_record_id");
        int type = (int)req.get("type");
        List res = examService.listByType(type, medicalRecordId, Utils.getSystemUser(req));
        return Response.ok(res);
    }

    @PostMapping("/allExam")
    public Map allExam(@RequestBody Map req){
        int medicalRecordId = (int) req.get("medical_record_id");
        List res = examService.list(medicalRecordId, Utils.getSystemUser(req));
        return Response.ok(res);
    }

    @PostMapping("register")
    public Map register(@RequestBody Map req){
        List<Integer> examItemIds = (List<Integer>) req.get("exam_item_id");
        if(examItemService.register(examItemIds)){
            return Response.ok();
        }else {
            return Response.error("列表错误!");
        }
    }

    @PostMapping("submitResult")
    public Map submitResult(@RequestParam Map req, @RequestParam("file")MultipartFile[] files){
        int examItemId = Integer.parseInt((String) req.get("exam_item_id"));
        ExamItem examItem = examItemService.selectByPrimaryKey(examItemId);
        if(examItem == null){
            return Response.error("没有该检查!");
        }
        if(!examItem.getStatus().equals(Common.YIDENGJI)){
            return Response.error("该检查尚未登记!");
        }
        ExamItemResult examItemResult = Utils.fromMap(req, ExamItemResult.class);
        String DBFile = "";
        for (int i = 0; i < files.length; i++) {
            long time = new Date().getTime();
            String suffix = Utils.getFileSuffix(files[i].getOriginalFilename());
            String filename = examItemId + time + "." + suffix;
            FileOutputStream fos;
            try {
                String path = filePath + filename;
                File file = new File(path);
                fos = new FileOutputStream(file);
                fos.write(files[i].getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            DBFile += files[i].getOriginalFilename() + "&" + filename + ";";
        }
        examItemResult.setFile(DBFile);
        examItemResultMapper.insertOrUpdate(examItemResult);
        examItem.setStatus("已完成");
        examItemService.update(examItem);
        return Response.ok();
    }

    @PostMapping("getResult")
    public Map getResult(@RequestBody Map req){
        int examItemId = (int)req.get("exam_item_id");
        ExamItemResult examItemResult = examItemResultMapper.selectByExamItemId(examItemId);
        Map res = Utils.objectToMap(examItemResult);
        String files = examItemResult.getFile();
        String[] filenames = files.split(";");
        List fileList = new ArrayList();
        for (String filename : filenames) {
            String[] nameArr = filename.split("&");
            HashMap fileMap = new HashMap();
            String originName = nameArr[0];
            String saveName = nameArr[1];
            fileMap.put("originName", originName);
            fileMap.put("saveName", saveName);
            fileList.add(fileMap);
        }
        res.put("file", fileList);
        return Response.ok(res);
    }

    @RequestMapping("getResultFile")
    public void getResultFile(@RequestParam Map req, HttpServletResponse response) throws IOException {
        String filename = (String) req.get("filename");
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + filename);
        OutputStream os = response.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath + filename));
        int i = bis.read(buff);
        while (i != -1) {
            os.write(buff, 0, buff.length);
            os.flush();
            i = bis.read(buff);
        }
        os.close();
    }
}
