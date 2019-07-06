package edu.neu.his.bean.medicalRecord;

import edu.neu.his.config.Auth;
import edu.neu.his.config.Response;
import edu.neu.his.bean.user.UserService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medicalRecordTemplate")
public class MedicalRecordTemplateController {
    @Autowired
    private MedicalRecordTemplateService medicalRecordTemplateService;

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    @ResponseBody
    public Map list(@RequestBody Map req){
        return Response.ok(returnList(req));
    }

    @PostMapping("/detail")
    @ResponseBody
    public Map detail(@RequestBody Map req){
        int id = (int)req.get("id");
        return Response.ok(medicalRecordTemplateService.selectById(id));
    }

    @PostMapping("/create")
    @ResponseBody
    public Map create(@RequestBody Map req){
        req = Utils.initMap(req);
        MedicalRecordTemplate medicalRecordTemplate = Utils.fromMap(req,MedicalRecordTemplate.class);
        String title = (String)req.get("title");
        title = updateCheckTitle(medicalRecordTemplate);
        int uid = Auth.uid(req);
        int department_id = userService.findByUid(uid).getDepartment_id();

        medicalRecordTemplate.setTitle(title);
        medicalRecordTemplate.setUser_id(uid);
        medicalRecordTemplate.setDepartment_id(department_id);
        medicalRecordTemplate.setCreate_time(Utils.getSystemTime());

        medicalRecordTemplateService.insert(medicalRecordTemplate);

        return Response.ok(returnList(req));
    }

    @PostMapping("/update")
    @ResponseBody
    public Map update(@RequestBody Map req) {
        int id = (int)req.get("id");
        MedicalRecordTemplate medicalRecordTemplate = medicalRecordTemplateService.selectById(id);
        if(medicalRecordTemplate==null)
            return Response.error("错误，该病历模板不存在");

        req = Utils.initMap(req);
        String time = medicalRecordTemplate.getCreate_time();
        MedicalRecordTemplate newMedicalRecordTemplate = Utils.fromMap(req,MedicalRecordTemplate.class);
        newMedicalRecordTemplate.setCreate_time(time);
        newMedicalRecordTemplate.setDepartment_id(medicalRecordTemplate.getDepartment_id());
        newMedicalRecordTemplate.setUser_id(medicalRecordTemplate.getUser_id());

        String title = updateCheckTitle(newMedicalRecordTemplate);
        medicalRecordTemplate.setTitle(title);

        medicalRecordTemplateService.update(newMedicalRecordTemplate);

        return Response.ok(returnList(req));
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map delete(@RequestBody Map req){
        List id_list = (List)req.get("idArr");
        id_list.forEach(id->{
            MedicalRecordTemplate medicalRecordTemplate = medicalRecordTemplateService.selectById((int)id);
            if(medicalRecordTemplate!=null)
                medicalRecordTemplateService.delete((int)id);
        });

        return Response.ok(returnList(req));
    }

    private Map returnList(Map req){
        Map data = new HashMap();
        int uid = Auth.uid(req);
        int department_id = userService.findByUid(uid).getDepartment_id();
        data.put("personal",medicalRecordTemplateService.selectByUser(uid));
        data.put("department",medicalRecordTemplateService.selectByDepartment(department_id));
        data.put("hospital",medicalRecordTemplateService.selectByType(MedicalRecordStatus.Public));
        return data;
    }

    private String updateCheckTitle(MedicalRecordTemplate medicalRecordTemplate) {
        String title = medicalRecordTemplate.getTitle();
        List<MedicalRecordTemplate> list = medicalRecordTemplateService.selectByTitle(title);
        if (list.size() == 1 && list.get(0).getId() == medicalRecordTemplate.getId()) {
            return title;
        }
        while (medicalRecordTemplateService.selectByTitle(title).size() != 0) {
            title = title + "(1)";
        }
        return title;
    }

}
