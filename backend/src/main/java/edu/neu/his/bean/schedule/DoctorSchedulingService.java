package edu.neu.his.bean.schedule;

import edu.neu.his.bean.schedule.DoctorSchedulingInfo;
import edu.neu.his.bean.schedule.DoctorSchedulingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorSchedulingService {
    @Autowired
    DoctorSchedulingMapper doctorSchedulingMapper;

    @Transactional
    public void addDoctorScheduling(DoctorSchedulingInfo doctorSchedulingInfo) {
        int shift_id = doctorSchedulingMapper.shiftName2ID(doctorSchedulingInfo.getShift());
        int level_id = doctorSchedulingMapper.levelName2ID(doctorSchedulingInfo.getRegistration_Level());
        doctorSchedulingMapper.addDoctorScheduling1(doctorSchedulingInfo,level_id);
        doctorSchedulingMapper.addDoctorScheduling(doctorSchedulingInfo,shift_id);
    }

    @Transactional
    public void deleteDoctorSchedulingShiftById(int id) {
        doctorSchedulingMapper.deleteDoctorSchedulingById(id);
    }

    @Transactional
    public void update(DoctorSchedulingInfo doctorSchedulingInfo) {
        int shift_id = doctorSchedulingMapper.shiftName2ID(doctorSchedulingInfo.getShift());
        doctorSchedulingMapper.updateDoctorScheduling(doctorSchedulingInfo,shift_id);
    }

    @Transactional
    public List<DoctorSchedulingInfo> findAll() {
        //System.out.println(doctorSchedulingMapper.findAll().get(0).toString());
        return doctorSchedulingMapper.findAll();
    }

    @Transactional
    public List<DoctorSchedulingInfo> findAddInfo(int uid) {
        return doctorSchedulingMapper.findAddInfo(uid);
    }

    @Transactional
    public List<DoctorSchedulingInfo> findAddNameInfo(String name) {
        return doctorSchedulingMapper.findAddNameInfo(name);
    }

    @Transactional
    public List<DoctorSchedulingInfo> getId(int uid){
        return doctorSchedulingMapper.getId(uid);
    }

    @Transactional
    public List<Integer> getAddedId(int uid){
        return doctorSchedulingMapper.getAddedId(uid);
    }
}
