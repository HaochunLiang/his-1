package edu.neu.his.bean.schedule;

import edu.neu.his.bean.schedule.DoctorSchedulingShift;
import edu.neu.his.config.Response;
import edu.neu.his.bean.schedule.DoctorSchedulingShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctorSchedulingShiftManagement")

public class DoctorSchedulingShiftController {
    @Autowired
    DoctorSchedulingShiftService doctorSchedulingShiftService;

    @RequestMapping("/all")
    @ResponseBody
    public Map getAllDoctorSchedulingShift() {
        return Response.ok(doctorSchedulingShiftService.findAll());
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map deleteDoctorSchedulingShift(@RequestBody Map req) {
        List<Map> doctorSchedulingShifts = (List<Map>) req.get("data");
        doctorSchedulingShifts.forEach(doctorSchedulingShift -> doctorSchedulingShiftService.deleteDoctorSchedulingShiftByName((String) doctorSchedulingShift.get("shift")));
        return Response.ok();
    }

    @PostMapping("/add")
    @ResponseBody
    public Map addAllShift(@RequestBody Map req) {
        DoctorSchedulingShift doctorSchedulingShift = map2DoctorSchedulingShift(req);
        if (doctorSchedulingShift==null)
            return Response.error("该用户不存在");
        doctorSchedulingShiftService.addDoctorSchedulingShift(doctorSchedulingShift);
        return Response.ok();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateAllShift(@RequestBody Map req) {
        int id = (int) req.get("id");
        String shift = (String) req.get("shift");
        int sort = Integer.parseInt((String)req.get("sort"));
        DoctorSchedulingShift doctorSchedulingShift = new DoctorSchedulingShift();
        doctorSchedulingShift.setId(id);
        doctorSchedulingShift.setShift(shift);
        doctorSchedulingShift.setSort(sort);
        doctorSchedulingShiftService.update(doctorSchedulingShift);
        return Response.ok();
    }

    private DoctorSchedulingShift map2DoctorSchedulingShift(Map req) {
        System.out.println(req);
        System.out.println((Map)req.get("data"));
        Map req1 = (Map)req.get("data");
        DoctorSchedulingShift doctorSchedulingShift = new DoctorSchedulingShift();
        doctorSchedulingShift.setShift((String)req1.get("shift"));
        doctorSchedulingShift.setSort(Integer.parseInt((String)req1.get("sort")));
        return doctorSchedulingShift;
    }

}
