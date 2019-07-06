package edu.neu.his.bean.schedule;

import edu.neu.his.bean.schedule.DoctorSchedulingShift;
import edu.neu.his.bean.schedule.DoctorSchedulingShiftMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorSchedulingShiftService {
    @Autowired
    DoctorSchedulingShiftMapper doctorSchedulingShiftMapper;

    @Transactional
    public void addDoctorSchedulingShift(DoctorSchedulingShift doctorSchedulingShift) {
        doctorSchedulingShiftMapper.addDoctorSchedulingShift(doctorSchedulingShift);
    }

    @Transactional
    public void deleteDoctorSchedulingShiftByName(String name) {
        doctorSchedulingShiftMapper.deleteDoctorSchedulingShiftByName(name);
    }

    @Transactional
    public void update(DoctorSchedulingShift doctorSchedulingShift) {
        doctorSchedulingShiftMapper.updateDoctorSchedulingShift(doctorSchedulingShift);
    }

    @Transactional
    public List<DoctorSchedulingShift> findAll() {
        return doctorSchedulingShiftMapper.findAll();
    }
}
