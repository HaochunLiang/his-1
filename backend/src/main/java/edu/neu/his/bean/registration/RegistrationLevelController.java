package edu.neu.his.bean.registration;

import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/registrationLevelManagement")
public class RegistrationLevelController {
    @Autowired
    private RegistrationLevelService registrationLevelService;

    @RequestMapping("/all")
    @ResponseBody
    public Map listAllRegistration_level(){
        return Response.ok(registrationLevelService.findAll());
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateRegistration_level(@RequestBody RegistrationLevel registration_level){
        if(canUpdate(registration_level)) {
            registrationLevelService.updateDepartment(registration_level);
            return Response.ok();
        }else
            return Response.error("名称冲突");
    }

    @PostMapping("/add")
    @ResponseBody
    public Map  insertRegistration_level(@RequestBody RegistrationLevel registration_level){
        if(canInsert(registration_level)) {
            registrationLevelService.insertRegistration_level(registration_level);
            return Response.ok();
        }else
            return Response.error("id或名称冲突");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map deleteRegistration_level(@RequestBody Map req){
        List<Integer> failed = new ArrayList<>();
        List<Integer> registration_level_ids = (List<Integer>)req.get("data");
        registration_level_ids.forEach(id -> {
            if(canDelete(id))
                registrationLevelService.deleteRegistration_level(id);
            else
                failed.add(id);
        });
        if(failed.isEmpty())
            return Response.ok();
        else{
            Map data = new HashMap();
            data.put("success number",registration_level_ids.size()-failed.size());
            data.put("fail number",failed.size());
            data.put("fail id",failed);
            return Response.error(data);
        }
    }

    private boolean canInsert(RegistrationLevel registration_level){
        return registrationLevelService.canInsert(registration_level);
    }

    private boolean canUpdate(RegistrationLevel registration_level){
        return registrationLevelService.canUpdate(registration_level);
    }

    private boolean canDelete(int id){
        return registrationLevelService.canDelete(id)!=0;
    }
}

