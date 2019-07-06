package edu.neu.his.bean.schedule;

import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/doctorWorkforceManagement")

public class DoctorWorkforceController {
    @Autowired
    DoctorWorkforceService doctorWorkforceService;

    @RequestMapping("/all")
    @ResponseBody
    public Map getAllDoctorWorkforce() {
        List<Schedule> getSchedules = doctorWorkforceService.getDoctorWorkforces();
        List<Schedule> inject = new ArrayList<Schedule>();
        getSchedules.forEach(schedule ->
        {
            inject.add(doctorWorkforceService.injectDoctoeWorkforce(schedule));
            schedule.setName(inject.get(inject.size() - 1).getName());
            schedule.setReg_limit(inject.get(inject.size() - 1).getReg_limit());
            schedule.setResidue(inject.get(inject.size() - 1).getResidue());
        });
        return Response.ok(doctorWorkforceService.getDoctorWorkforces());
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public Map getAllSchedule() {
        //System.out.println("doctorWorkforceService.findAllSchedule()"+doctorWorkforceService.findAllSchedule());
        /*for(AllSchedule allSchedule:doctorWorkforceService.findAllSchedule()){
        }*/
        return Response.ok(doctorWorkforceService.findAllSchedule());
    }


    @PostMapping("/choose")
    @ResponseBody
    public Map chooseDoctorWorkforce(@RequestBody Map req) throws ParseException {
        System.out.println("chooseDoctorWorkforce:"+req);
        Integer num = (Integer) req.get("num");

        doctorWorkforceService.setNumberPerDay(num);

        List<Map> doctorWorkforces = (List<Map>) req.get("data");
        List<String> dateRange = (List<String>) req.get("dateRange");
        System.out.println("dateRange:"+dateRange);
        System.out.println(doctorWorkforces);
        doctorWorkforces.forEach(doctorWorkforce -> doctorWorkforceService.chooseDoctorWorkforceByName((int)doctorWorkforce.get("id"), (String) doctorWorkforce.get("name"),(String) doctorWorkforce.get("shift"),(String) doctorWorkforce.get("expiry_date"),(Integer) doctorWorkforce.get("scheduling_limit"),(String) doctorWorkforce.get("registration_Level")));
        //doctorWorkforceService.schedule(dateRange);
        //改为分挂号级别
        //doctorWorkforceService.scheduleByRegistrationLevel(dateRange);
        List<String> registrationLevels = doctorWorkforceService.getRegistrationLevels();
        doctorWorkforceService.scheduleByRegistrationLevel(dateRange,registrationLevels);

        return Response.ok();
    }

 /*   @GetMapping("/update")
    @ResponseBody
    public Map updateDoctorWorkforce() {
        System.out.println("doctorWorkforceService.getDoctorWorkforces()"+doctorWorkforceService.getDoctorWorkforces());
        return Response.ok();
    }*/


    @PostMapping("/add")
    @ResponseBody
    public Map addAllDoctorWorkforce(@RequestBody Map req) {
        System.out.println("addAllDoctorWorkforce:"+req);
        ArrayList<Schedule> schedules = map2DoctorWorkforce(req);
        for(Schedule schedule:schedules) {
            if (schedule == null)
                return Response.error("该用户不存在");
            //System.out.println("doctorWorkforceService.addDoctorWorkforce");
            doctorWorkforceService.addDoctorWorkforce(schedule);
        }
        return Response.ok();
    }

    @PostMapping("/addRow")
    @ResponseBody
    public Map addOneRow(@RequestBody Map req) {
        System.out.println("addRow:"+req);
        Schedule schedule = map2DoctorWorkforceInfo(req);
        if (schedule==null) {
            System.out.println("addRow false");
            return Response.error("该用户不存在");
        }

        //查找id是否存在
        List<DoctorSchedulingInfo> ids = doctorWorkforceService.getName(schedule.getName());
        System.out.println("size:"+ids.size());
        if(ids.isEmpty()){
            return Response.error("");
        }
        doctorWorkforceService.addOneDoctorWorkforce(schedule);
        return Response.ok();
    }

    //查找add row冲突
    @PostMapping("/addRowConflict")
    @ResponseBody
    public Map findAddRowConflict(@RequestBody Map req){
        System.out.println("add row conflict req:"+req);

        Schedule schedule = map2DoctorWorkforceInfo(req);

        //查找id是否存在
        List<DoctorSchedulingInfo> ids = doctorWorkforceService.getName(schedule.getName());
        System.out.println("size:"+ids.size());
        if(ids.isEmpty()){
            return Response.error("");
        }

        int uid = 0;
        Object id = req.get("id");
        if(id instanceof Integer) {
            uid = (Integer)id;
        }else{
            uid = Integer.valueOf((String)id);
        }

        String schedule_date = ((String) req.get("schedule_date")).substring(0,10);
        System.out.println("schedule_date="+schedule_date);
        String shift = (String) req.get("shift");

        if (uid==0)
            return Response.error("该用户不存在");
        //System.out.println("addrowC:"+doctorWorkforceService.findAddRowConflict(name,schedule_date,shift));
        return Response.ok(doctorWorkforceService.findAddRowConflict(uid,schedule_date,shift));
    }


    @PostMapping("/delete")
    @ResponseBody
    public Map deleteDoctorWorkforce(@RequestBody Map req) {
        System.out.println("Controller  Delete:"+req);
        List<Map> doctorWorkforces = (List<Map>) req.get("data");
        doctorWorkforces.forEach(
                doctorWorkforce -> doctorWorkforceService.deleteDoctorWorkforceById((Integer)doctorWorkforce.get("id"),(String)doctorWorkforce.get("schedule_date"))
        );
        return Response.ok();
    }

    @PostMapping("/time")
    @ResponseBody
    public Map findTimeConflict(@RequestBody Map req){
//        System.out.println("time req:"+req);
        List<String> times = (List<String>) req.get("data");
//        System.out.println("TIMES:"+times);
        return Response.ok(doctorWorkforceService.findTimeConflict(times));
    }

    @PostMapping("/addTable")
    @ResponseBody
    public Map findAddInfo(@RequestBody Map req){
        String name = (String) req.get("data");
        System.out.println("add table"+name);
        System.out.println("res:"+doctorWorkforceService.findAddInfo(name));
        return Response.ok(doctorWorkforceService.findAddInfo(name));
    }

    @PostMapping("/addTableByID")
    @ResponseBody
    public Map findAddInfoByID(@RequestBody Map req){
        int id = (Integer) req.get("data");
        System.out.println("res:"+doctorWorkforceService.findAddInfoByID(id));
        return Response.ok(doctorWorkforceService.findAddInfoByID(id));
    }

    //覆盖排班信息
    @PostMapping("/overwrite")
    @ResponseBody
    public Map overwriteInfo(@RequestBody Map req){
        System.out.println("overwrite:"+req);
        List<Map> times = (List<Map>) req.get("data");
        if(!times.isEmpty()) {
            times.forEach(doctorWorkforce ->
            {
                doctorWorkforceService.deleteDoctorWorkforceById((Integer) doctorWorkforce.get("id"), (String) doctorWorkforce.get("schedule_date"));
            });
        }
        return Response.ok();
    }

    private ArrayList<Schedule> map2DoctorWorkforce(Map req) {
        ArrayList<LinkedHashMap> req1 = (ArrayList<LinkedHashMap>) req.get("data");
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();
        Schedule schedule;
        //Map req1 = req;
        System.out.println("req1="+req1);

        for(LinkedHashMap map:req1) {
            schedule = new Schedule();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String)entry.getKey();
                if(key.equals("id")){
                    schedule.setId((Integer) entry.getValue());
                }
                if(key.equals("name")){
                    schedule.setName((String)entry.getValue());
                }
                if(key.equals("scheduleDate")){
                    schedule.setScheduleDate((String)entry.getValue());
                }
                if(key.equals("shift")){
                    schedule.setShift((String)entry.getValue());
                }
                if(key.equals("week")){
                    schedule.setWeek((Integer) entry.getValue());
                }
                if(key.equals("residue")){
                    schedule.setResidue((Integer) entry.getValue());
                }
                if(key.equals("valid")){
                    schedule.setValid((String) entry.getValue());
                }
                if(key.equals("reg_limit")){
                    schedule.setReg_limit((Integer) entry.getValue());
                }
                if(key.equals("registration_Level")){
                    schedule.setRegistration_Level((String) entry.getValue());
                }
            }
            schedules.add(schedule);
        }
        return schedules;
    }

    private Schedule map2DoctorWorkforceInfo(Map req) {
        Map req1 = req;//(Map)req.get("data");
        Schedule schedule = new Schedule();

        Object id = req1.get("id");
        if(id instanceof Integer) {
            schedule.setId((Integer)id);
        }else{
            schedule.setId(Integer.valueOf((String)id));
        }

        schedule.setName((String) req1.get("name"));

        String sche = (String) req1.get("schedule_date");
        sche = sche.substring(0, 10);
        schedule.setScheduleDate(sche);
        System.out.println("scheduleDate:"+schedule.getScheduleDate());
        //schedule.setScheduleDate((String) req1.get("schedule_date"));

        schedule.setWeek(date2Week(sche));

        schedule.setShift((String) req1.get("shift"));

        Object residue = req1.get("residue");
        if(residue instanceof Integer) {
            schedule.setResidue((Integer)residue);
        }else{
            schedule.setResidue(Integer.valueOf((String)residue));
        }
        schedule.setValid((String) req1.get("valid"));
        schedule.setReg_limit((Integer)req1.get("reg_limit"));
        schedule.setRegistration_Level((String) req1.get("registration_Level"));
        //System.out.println("map2DoctorWorkforceInfo:    "+schedule.getName()+"  "+schedule.getReg_limit());
        return schedule;
    }

    //日期转星期
    public int date2Week(String date) {
        DateFormat df = DateFormat.getDateInstance();
        try {
            df.parse(date);
            Calendar c = df.getCalendar();
            int day = c.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            return day;
        } catch (ParseException e) {
            return -1;
        }
    }

}
