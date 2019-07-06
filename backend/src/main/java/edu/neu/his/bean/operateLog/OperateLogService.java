package edu.neu.his.bean.operateLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Transactional
    public void insertOperateLog(OperateLog operateLog) {
        operateLogMapper.insert(operateLog);
    }
}
