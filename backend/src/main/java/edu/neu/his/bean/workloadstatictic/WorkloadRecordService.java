package edu.neu.his.bean.workloadstatictic;

import edu.neu.his.bean.department.DepartmentMapper;
import edu.neu.his.bean.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WorkloadRecordService {
    @Autowired
    WorkloadRecordMapper workloadRecordMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    public void insert(WorkloadRecord workloadRecord) {
        workloadRecordMapper.insert(workloadRecord);
    }

    public List<Map> departmentStatistic(String start_date,String end_date) {
        List<Map> results = workloadRecordMapper.departmentStatistic(start_date,end_date);
        results.forEach(result->{
            result.put("department_name",departmentMapper.selectById((int)result.get("department_id")).getName());
        });
        return results;
    }

    public List<Map> userStatistic(String start_date,String end_date) {
         List<Map> results = workloadRecordMapper.userStatistic(start_date,end_date);
         results.forEach(result->{
            result.put("user_name",userMapper.find((int)result.get("user_id")).getReal_name());
         });
         return results;
    }

    public List<Map> typeStatistic(String start_date,String end_date) {
        List<Map> results = workloadRecordMapper.typeStatistic(start_date,end_date);
        return results;
    }

}
