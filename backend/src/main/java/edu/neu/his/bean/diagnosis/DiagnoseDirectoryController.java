
package edu.neu.his.bean.diagnosis;

import edu.neu.his.bean.disease.Disease;
import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//3.5 诊断目录管理
@RestController
@RequestMapping("/diagnoseDirectoryManagement")
public class DiagnoseDirectoryController {
    @Autowired
    private DiagnoseDirectoryService diagnoseDirectoryService;

    @RequestMapping("/allClassification")
    @ResponseBody
    public Map allDiseaseClassification(){
        return Response.ok(diagnoseDirectoryService.findAllDiseaseClassification());
    }

    @PostMapping("/searchAllByClassificationId")
    @ResponseBody
    public Map listAllDisease(@RequestBody Map req){
        int classification_id = (int)req.get("classification_id");
        Map data = new HashMap();
        data.put("diseases",diagnoseDirectoryService.findAll(classification_id));
        return Response.ok(data);
    }

    @PostMapping("/findByName")
    @ResponseBody
    public Map DiseaseFindByName(@RequestBody Map req){
        String name = (String)req.get("name");
        return Response.ok(diagnoseDirectoryService.findDiseaseByName(name));
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateDisease(@RequestBody Map req){
        Disease disease = req2Disease(req);
        int rawId = (int)req.get("raw_id");
        int classification_id = (int)req.get("classification_id");
        if(!checkIdExist(rawId)){
            return Response.error("错误，原ID不存在。");
        }else if(disease.getId()!=rawId && checkIdExist(disease.getId())) {
            return Response.error("错误，ID重复。");
        }else if(!checkClassificationExist(classification_id)){
            return Response.error("错误，疾病类别不存在。");
        }
        else {
            diagnoseDirectoryService.updateDisease(rawId,disease);
            return Response.ok();
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Map insertDisease(@RequestBody Map req){
        Disease disease = req2Disease(req);
        if(!checkClassificationExist(disease.getClassification_id())){
            return Response.error("错误，疾病类别不存在。");
        }else if(checkIdExist(disease.getId())) {
            return Response.error("错误，ID重复。");
        }else {
            diagnoseDirectoryService.insertDisease(disease);
            return Response.ok();
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map  deleteDisease(@RequestBody Map req){
        List<Integer> disease_ids = (List<Integer>) req.get("data");
        disease_ids.forEach(id->diagnoseDirectoryService.deleteDisease(id));
        return Response.ok();
    }

    private boolean checkCodeExist(String code) {
        //检测Code存在
        return diagnoseDirectoryService.checkCodeExist(code);
    }

    private boolean checkClassificationExist(int classification_id) {
        //检测类别存在
        return diagnoseDirectoryService.checkClassificationExist(classification_id);
    }

    private boolean checkIdExist(int id) {
        //检测Code存在
        return diagnoseDirectoryService.checkIdExist(id);
    }

    private Disease req2Disease(Map req) {
        Disease disease = new Disease();
        String code = (String) req.get("code");
        disease.setCode(code);
        disease.setId((int) req.get("id"));
        String name = (String)req.get("name");
        disease.setName(name);
        int classification_id = (int)req.get("classification_id");
        disease.setClassification_id(classification_id);
        String pinyin = (String)req.get("pinyin");
        disease.setPinyin(pinyin);
        String custom_name = "";
        String custom_pinyin = "";
        if(req.containsKey("custom_name")) custom_name = (String)req.get("custom_name");
        if(req.containsKey("custom_pinyin")) custom_pinyin = (String)req.get("custom_pinyin");
        disease.setCustom_name(custom_name);
        disease.setCustom_pinyin(custom_pinyin);
        return disease;
    }
}