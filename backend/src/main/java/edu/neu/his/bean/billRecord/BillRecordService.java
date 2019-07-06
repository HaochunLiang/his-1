package edu.neu.his.bean.billRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实现处理数据库中bill_record表的相关操作
 *
 * @author 王婧怡
 * @version 1.0
 */
@Service
public class BillRecordService {
    @Autowired
    private BillRecordMapper billRecordMapper;

    /**
     * 向bill_record表中插入一条数据
     * @param billRecord 需要插入表中的BillRecord对象
     * @return 返回该BillRecord对象插入数据库中后自动生成的id
     */
    @Transactional
    public int insertBillRecord(BillRecord billRecord) {
        billRecordMapper.insert(billRecord);
        return billRecord.getId();
    }

    /**
     * 根据主键id查找相对应的bill_record表中的记录
     * @param id bill_record表的主键，代表票据记录的流水号
     * @return 将查找到的记录生成BillRecord对象返回
     */
    @Transactional
    public BillRecord findById(int id) {
        return billRecordMapper.find(id);
    }

    /**
     * 根据用户id和时间段查找该用户在该时间段的所有票据记录
     * @param user_id 需要查找票据记录的用户的id
     * @param start_time 查找时间段的开始时间
     * @param end_time 查找时间段的结束时间
     * @return 返回符合条件的所有票据记录列表
     */
    @Transactional
    public List<BillRecord> findByUserIdAndTime(int user_id, String start_time, String end_time) {
        return billRecordMapper.findByUserIdAndTime(user_id,start_time,end_time);
    }
}
