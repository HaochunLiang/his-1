package edu.neu.his.bean.schedule;

import edu.neu.his.bean.schedule.DoctorSchedulingInfo;
import edu.neu.his.config.Response;
import edu.neu.his.bean.schedule.DoctorSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctorSchedulingManagement")

public class DoctorSchedulingController {
    @Autowired
    DoctorSchedulingService doctorSchedulingService;

    SimpleDateFormat formatter = new SimpleDateFormat(("yyyy-mm-dd"));
    ParsePosition pos = new ParsePosition(0);

    @RequestMapping("/all")
    @ResponseBody
    public Map getAllDoctorScheduling() {
        return Response.ok(doctorSchedulingService.findAll());
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map deleteDoctorScheduling(@RequestBody Map req) {
        List<Integer> doctorSchedulingIds = (List<Integer>) req.get("data");
        doctorSchedulingIds.forEach(id -> doctorSchedulingService.deleteDoctorSchedulingShiftById(id));
        //List<Map> doctorSchedulings = (List<Map>) req.get("data");
        //doctorSchedulings.forEach(doctorSchedulingShift -> doctorSchedulingService.deleteDoctorSchedulingShiftByName((String) doctorSchedulingShift.get("name")));
        return Response.ok();
    }

    @PostMapping("/add")
    @ResponseBody
    public Map addDoctorInfo(@RequestBody Map req) throws ParseException {
        System.out.println("Add doctor:"+req);
        DoctorSchedulingInfo doctorSchedulingInfo = map2DoctorSchedulingInfo(req);
        if (doctorSchedulingInfo==null)
            return Response.error("该用户不存在");

        //查找id是否存在
        List<DoctorSchedulingInfo> ids = doctorSchedulingService.getId(doctorSchedulingInfo.getId());
        //System.out.println("size:"+ids.size());
        if(ids.isEmpty()){
            return Response.error("用户列表中找不到该用户");
        }

        //查找ID是否以添加
        List<Integer> addedIds = doctorSchedulingService.getAddedId(doctorSchedulingInfo.getId());
        if(!addedIds.isEmpty()){
            return Response.error("该用户已添加至可排班人员列表");
        }

        doctorSchedulingService.addDoctorScheduling(doctorSchedulingInfo);
        return Response.ok();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateAllInfo(@RequestBody Map req) throws ParseException {
        System.out.println("updateAllInfo:"+req);
        int id = (Integer)req.get("id");
        //String name = (String) req.get("name");
        //String department_name = (String) req.get("department_name");
        //String title = (String) req.get("title");
        String registration_Level = (String) req.get("registration_Level");
        String shift = (String) req.get("shift");

        Date expiry_dateD = formatter.parse((String)req.get("expiry_date"));
        String expiry_date = formatter.format(expiry_dateD);

        int scheduling_limit = (Integer)req.get("scheduling_limit");
        DoctorSchedulingInfo doctorSchedulingInfo = new DoctorSchedulingInfo();
        doctorSchedulingInfo.setId(id);
        //doctorSchedulingInfo.setName(name);
        //doctorSchedulingInfo.setTitle(title);
        //doctorSchedulingInfo.setDepartment_name(department_name);
        doctorSchedulingInfo.setRegistration_Level(registration_Level);
        doctorSchedulingInfo.setShift(shift);
        doctorSchedulingInfo.setExpiry_date(expiry_date);
        doctorSchedulingInfo.setScheduling_limit(scheduling_limit);
        doctorSchedulingService.update(doctorSchedulingInfo);
        return Response.ok();
    }

    @PostMapping("/addPersonnelTable")
    @ResponseBody
    public Map findAddInfo(@RequestBody Map req){
        Integer uid = 0;
        if(!((Integer) req.get("data")).equals("")) {
            uid = ((Integer) req.get("data"));
        }

        Map data = new HashMap();
        List<DoctorSchedulingInfo> ds = doctorSchedulingService.findAddInfo(uid);
        if(!ds.isEmpty()) {
            data.put("name", ds.get(0).getName());
            data.put("department_name", ds.get(0).getDepartment_name());
        }
        return Response.ok(data);
    }

    @PostMapping("/addNamePersonnelTable")
    @ResponseBody
    public Map findAddNameInfo(@RequestBody Map req){
        String name = (String) req.get("data");

        Map data = new HashMap();
        List<DoctorSchedulingInfo> ds = doctorSchedulingService.findAddNameInfo(name);
        if(!ds.isEmpty()) {
            data.put("id", ds.get(0).getId());
            data.put("department_name", ds.get(0).getDepartment_name());
        }
        return Response.ok(data);
    }

    private DoctorSchedulingInfo map2DoctorSchedulingInfo(Map req) throws ParseException {
        Map req1 = req;//(Map)req.get("data");
        DoctorSchedulingInfo doctorSchedulingInfo = new DoctorSchedulingInfo();
        doctorSchedulingInfo.setId((Integer)req1.get("id"));
        doctorSchedulingInfo.setName((String) req1.get("name"));
        doctorSchedulingInfo.setDepartment_name((String) req1.get("department_name"));
        doctorSchedulingInfo.setTitle((String) req1.get("title"));
        doctorSchedulingInfo.setRegistration_Level((String) req1.get("registration_Level"));
        doctorSchedulingInfo.setShift((String) req1.get("shift"));

        String expiry_date = (String) req1.get("expiry_date");
        expiry_date = expiry_date.substring(0,10);
        doctorSchedulingInfo.setExpiry_date(expiry_date);
        System.out.println("scheduleDate:"+doctorSchedulingInfo.getExpiry_date());

        doctorSchedulingInfo.setScheduling_limit((Integer)req1.get("scheduling_limit"));
        return doctorSchedulingInfo;
    }

}
