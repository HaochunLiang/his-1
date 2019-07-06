package edu.neu.his.bean.diagnosis;

import edu.neu.his.bean.drug.Drug;
import edu.neu.his.bean.drug.DrugService;
import edu.neu.his.bean.medicalRecord.MedicalRecordStatus;
import edu.neu.his.bean.user.User;

import edu.neu.his.config.Response;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diagnoseTemplate")
public class MedicalRecordDiagnoseTemplateController {
    @Autowired
    MedicalRecordDiagnoseTemplateService medicalRecordDiagnoseTemplateService;
    @Autowired
    DiagnoseDirectoryService diagnoseDirectoryService;

    @PostMapping("/list")
    @ResponseBody
    public Map list(@RequestBody Map req){
        User user = Utils.getSystemUser(req);
        Map data = new HashMap();
        List<MedicalRecordDiagnoseTemplate> userTemplates = medicalRecordDiagnoseTemplateService.selectByUser(user.getUid());
        List<Map> personalList = medicalRecordDiagnoseTemplateService.returnMapList(userTemplates);
        data.put("personal",personalList);

        List<MedicalRecordDiagnoseTemplate> departmentTemplates = medicalRecordDiagnoseTemplateService.selectByDepartment(user.getDepartment_id());
        List<Map> departmentList = medicalRecordDiagnoseTemplateService.returnMapList(departmentTemplates);
        data.put("department",departmentList);

        List<MedicalRecordDiagnoseTemplate> publicTemplates = medicalRecordDiagnoseTemplateService.selectByType(MedicalRecordStatus.Public);
        List<Map> publicList = medicalRecordDiagnoseTemplateService.returnMapList(publicTemplates);
        data.put("hospital",publicList);

        return Response.ok(data);
    }

    @PostMapping("/create")
    @ResponseBody
    public Map create(@RequestBody Map req){
        MedicalRecordDiagnoseTemplate medicalRecordDiagnoseTemplate = Utils.fromMap(req,MedicalRecordDiagnoseTemplate.class);
        medicalRecordDiagnoseTemplate = initDiagnoseTemplate(medicalRecordDiagnoseTemplate,req);
        int diagnose_template_id = medicalRecordDiagnoseTemplateService.insertDiagnoseTemplate(medicalRecordDiagnoseTemplate);

        Map map = (Map)req.get("diagnose");
        List westernList = (List)map.get("western_diagnose");
        insertItemList(westernList,diagnose_template_id,DiagnoseItemType.Western);

        List chineseList = (List)map.get("chinese_diagnose");
        insertItemList(chineseList,diagnose_template_id,DiagnoseItemType.Chinese);

        return Response.ok();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map update(@RequestBody Map req){
        MedicalRecordDiagnoseTemplate medicalRecordDiagnoseTemplate = Utils.fromMap(req,MedicalRecordDiagnoseTemplate.class);
        String title = updateCheckName(medicalRecordDiagnoseTemplate);
        User user = Utils.getSystemUser(req);
        medicalRecordDiagnoseTemplate.setUser_id(user.getUid());
        medicalRecordDiagnoseTemplate.setDepartment_id(user.getDepartment_id());
        medicalRecordDiagnoseTemplate.setTitle(title);

        medicalRecordDiagnoseTemplateService.updateDiagnoseTemplate(medicalRecordDiagnoseTemplate);
        int diagnose_template_id = medicalRecordDiagnoseTemplate.getId();

        medicalRecordDiagnoseTemplateService.deleteAllItem(diagnose_template_id);

        Map map = (Map)req.get("diagnose");
        List westernList = (List)map.get("western_diagnose");
        insertItemList(westernList,diagnose_template_id,DiagnoseItemType.Western);

        List chineseList = (List)map.get("chinese_diagnose");
        insertItemList(chineseList,diagnose_template_id,DiagnoseItemType.Chinese);

        return Response.ok();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map delete(@RequestBody Map req){
        List ids = (List)req.get("idArr");
        ids.forEach(id->{
            if(medicalRecordDiagnoseTemplateService.selectTemplateById((int)id)!=null) {
                medicalRecordDiagnoseTemplateService.deleteTemplateById((int) id);
                medicalRecordDiagnoseTemplateService.deleteAllItem((int)id);
            }
        });

        return Response.ok();
    }

    @PostMapping("/detail")
    @ResponseBody
    public Map detail(@RequestBody Map req){
        int id = (int)req.get("id");
        MedicalRecordDiagnoseTemplate medicalRecordDiagnoseTemplate = medicalRecordDiagnoseTemplateService.selectTemplateById(id);
        if(medicalRecordDiagnoseTemplate == null)
            return  Response.error("错误，该诊断模板不存在");
        Map data = Utils.objectToMap(medicalRecordDiagnoseTemplate);
        data.put("diagnose",medicalRecordDiagnoseTemplateService.returnDiagnoseTemplateMap(id));

        return Response.ok(data);
    }

    private String checkName(String title){
        while (medicalRecordDiagnoseTemplateService.selectByTitle(title).size()!=0){
            title = title + "(1)";
        }
        return title;
    }

    private String updateCheckName(MedicalRecordDiagnoseTemplate medicalRecordDiagnoseTemplate){
        String title = medicalRecordDiagnoseTemplate.getTitle();
        List<MedicalRecordDiagnoseTemplate> list = medicalRecordDiagnoseTemplateService.selectByTitle(title);
        if (list.size()==1 && list.get(0).getId()==medicalRecordDiagnoseTemplate.getId()){
            return title;
        }

        while (medicalRecordDiagnoseTemplateService.selectByTitle(title).size()!=0){
            title = title + "(1)";
        }
        return title;
    }

    private MedicalRecordDiagnoseTemplate initDiagnoseTemplate(MedicalRecordDiagnoseTemplate medicalRecordDiagnoseTemplate,Map req){
        String title = medicalRecordDiagnoseTemplate.getTitle();
        title = checkName(title);
        medicalRecordDiagnoseTemplate.setTitle(title);

        User user = Utils.getSystemUser(req);
        medicalRecordDiagnoseTemplate.setUser_id(user.getUid());
        medicalRecordDiagnoseTemplate.setDepartment_id(user.getDepartment_id());
        medicalRecordDiagnoseTemplate.setCreate_time(Utils.getSystemTime());

        return medicalRecordDiagnoseTemplate;
    }

    private void insertItemList(List list, int diagnose_template_id, String type){
        list.forEach(item->{
            Map itemMap = (Map)item;
            itemMap = Utils.initMap(itemMap);
            MedicalRecordDiagnoseTemplateItem medicalRecordDiagnoseTemplateItem = Utils.fromMap(itemMap, MedicalRecordDiagnoseTemplateItem.class);
            medicalRecordDiagnoseTemplateItem.setMedical_record_diagnose_template_id(diagnose_template_id);
            medicalRecordDiagnoseTemplateItem.setDiagnose_type(type);
            if(diagnoseDirectoryService.checkIdExist(medicalRecordDiagnoseTemplateItem.getDisease_id()))
                medicalRecordDiagnoseTemplateService.insertDiagnoseTemplateItem(medicalRecordDiagnoseTemplateItem);
        });
    }
}
